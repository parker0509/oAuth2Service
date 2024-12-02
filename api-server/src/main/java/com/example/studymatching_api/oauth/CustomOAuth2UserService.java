package com.example.studymatching_api.oauth;


import com.example.studymatching_api.domain.User;
import com.example.studymatching_api.domain.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2UserService의 기본 구현을 사용하여 사용자 정보 로드
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        // OAuth2 서비스 구분
        // registationId 를 통해 of를 통해 구글 , 네이버 , 카카오톡 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth 제공자에서 사용자의 기본 정보(이름)를 추출
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2User의 속성 정보를 처리하는 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // attributes가 null일 경우 예외를 발생시켜 처리
        if (attributes == null) {
            throw new OAuth2AuthenticationException("OAuthAttributes is null for registrationId: " + registrationId);
        }

        // 사용자 정보를 저장하거나 업데이트하는 메소드 호출
        User user = saveOrUpdate(attributes);

        // 세션에 사용자 정보를 저장 (SessionUser로 감싸서 저장)
        httpSession.setAttribute("user", new SessionUser(user));

        // 새로운 OAuth2User 객체를 반환 (빈 권한 집합 설정)
        return new DefaultOAuth2User(
                Collections.emptySet(), // 권한 정보가 없으므로 빈 집합 설정
                attributes.getAttributes(), // OAuth2User의 속성 정보 설정
                attributes.getNameAttributeKey() // 사용자 이름 속성 설정
        );
    }



    /*
     * 사용자 정보 중복 점검 및 저장
     *
     * 1. 주어진 이메일로 이미 존재하는 사용자가 있는지 확인합니다.
     *
     * 2. 존재하는 사용자:
     *    - 사용자 정보가 중복되었음을 로깅하고, 해당 사용자 정보를 업데이트합니다.
     *    - 업데이트된 사용자는 DB에 저장합니다.
     *
     * 3. 존재하지 않는 사용자:
     *    - 새로운 사용자 정보를 생성하여 DB에 저장합니다.
     */


    private User saveOrUpdate(OAuthAttributes attributes) {

        // 이메일을 기준으로 기존 사용자 검색
        Optional<User> existingOpt = userRepository.findByEmail(attributes.getEmail());

        // 기존 사용자가 존재하는 경우, 중복된 사용자 처리
        if (existingOpt.isPresent()) {
            System.out.println("Duplicate User = " + existingOpt.get());
            User existingUser = existingOpt.get();

            // 사용자 정보를 업데이트 (이름, 이메일)
            existingUser.update(attributes.getName(), attributes.getEmail());

            return userRepository.save(existingUser);
        }

        // 새로운 사용자 처리
        else {

            User newUser = attributes.toEntity();
            System.out.println("Create new User = " + newUser);

            return userRepository.save(newUser);
        }
    }

}



/*
            // 새 사용자 생성 및 저장
            User newUser = attributes.toEntity();
            System.out.println("Create new User = " + newUser);

            return userRepository.save(newUser);
        }
*/