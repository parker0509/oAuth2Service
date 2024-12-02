package com.example.studymatching_api.config;


import com.example.studymatching_api.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // 로그아웃 후 메인 페이지로 리디렉션
                        .invalidateHttpSession(true)  // 세션 무효화
                        .clearAuthentication(true)   // 인증 정보 삭제
                        .deleteCookies("JSESSIONID") // 세션 쿠키 삭제
                )

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/HomePage"); // 로그인 후 홈 페이지로 리디렉션
                        })
                )

                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (REST API나 OAuth 인증이 있을 때)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))  // Swagger UI를 iframe에서 사용
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/home.html").permitAll() // Swagger UI 허용
                        .requestMatchers("/api/study", "/api/**").permitAll() // API 엔드포인트 허용
                        .requestMatchers("/home", "/HomePage", "/", "/css/**", "/images/**", "/js/**", "/login/**", "/logout/**", "/posts/**", "/comments/**").permitAll() // 기타 리소스 허용
                        .anyRequest().authenticated()  // 인증된 사용자만 접근 허용
                );
        return http.build();
    }
}
