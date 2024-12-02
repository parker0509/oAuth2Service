package com.example.studymatching_api.oauth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuth", description = "OAuth 로그인")
@RestController
public class OAuthController {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public OAuthController(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Operation(summary = "OAuth2 로그인", description = "OAuth2 로그인 후 사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 로그인됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/oauth2/login")
    public OAuth2User oauth2Login(@RequestParam("registrationId") String registrationId,
                                  @RequestParam("userNameAttributeName") String userNameAttributeName,
                                  OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 실제로 loadUser 메소드 호출
        return customOAuth2UserService.loadUser(userRequest);
    }
}
