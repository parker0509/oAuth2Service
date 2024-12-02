package com.example.studymatching_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "Home Controller", description = "메인 페이지 관련 API")
public class HomeController {

    private final HttpSession httpSession;

    /**
     * 메인 화면을 반환합니다.
     *
     * @param oAuth2User OAuth2 인증된 사용자 정보
     * @param model      모델 객체에 사용자 정보를 추가합니다.
     * @return "home" HTML 페이지를 반환합니다.
     */
    @Operation(summary = "홈 페이지", description = "로그인된 사용자의 이름을 반환하고, 홈 페이지를 보여줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 홈 페이지를 반환"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자로, 로그인 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류 발생")
    })
    @GetMapping("/")
    public String login(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        if (oAuth2User != null) {
            // OAuth2User에서 사용자 이름 추출
            String userName = (String) oAuth2User.getAttributes().get("name");
            System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());  // 사용자 정보 출력

            httpSession.setAttribute("userName", userName);  // 세션에 사용자 이름 저장
            model.addAttribute("userName", userName);  // 모델에 userName 추가
        }
        return "home";  // "home.html" 템플릿을 반환
    }

    /**
     * 사용자 로그인 후 홈 페이지를 반환합니다.
     *
     * @param oAuth2User OAuth2 인증된 사용자 정보
     * @param model      모델 객체에 사용자 정보를 추가합니다.
     * @return "HomePage" HTML 페이지를 반환합니다.
     */
    @Operation(summary = "홈 페이지 반환", description = "사용자가 로그인 후 홈 페이지를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 홈 페이지를 반환"),
            @ApiResponse(responseCode = "401", description = "사용자 인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/HomePage")
    public String HomePage(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        // OAuth2 인증된 사용자 정보 처리
        if (oAuth2User != null) {
            // OAuth2User에서 사용자 이름 추출
            String userName = (String) oAuth2User.getAttributes().get("name");
            System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());  // 사용자 정보 출력

            httpSession.setAttribute("userName", userName);  // 세션에 사용자 이름 저장
            model.addAttribute("userName", userName);  // 모델에 userName 추가
        }

        return "HomePage";  // "HomePage.html" 템플릿 반환
    }
}
