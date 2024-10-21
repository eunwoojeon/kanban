package com.eunwoojeon.kanban.config;

import com.eunwoojeon.kanban.jwt.JWTFilter;
import com.eunwoojeon.kanban.jwt.JWTUtil;
import com.eunwoojeon.kanban.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
1. client가 application에 request시(자격 증명_credentials을 제출시) container는 HttpServletRequest를 처리하는 FilterChain을 생성
2. HttpServletRequest에서 제출된 username과 password를 사용해서 UsernamePasswordAuthenticationToken을 생성
3. AuthenticationManager에 token을 전달하여 인증 진행
4. 인증 성공시 SecurityContext를 HttpSession에 저장 후 AuthenticationSuccessHandler가 호출된다.
5. 인증 실패시 SecurityContextHolder를 clear하고 AuthenticationFailureHandler를 호출한다.
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // AuthenticationManager가 인자로 받을 AuthenticationConfiguration 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(auth -> auth.disable());// csrf disable(세션이 아닌 jwt 방식이므로 불필요)
        http.formLogin(auth -> auth.disable());// form 로그인 방식 disable
        http.httpBasic(auth -> auth.disable());// http basic 인증 방식 disable
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// 세션 설정 : stateless(Spring Security가 세션을 생성하지도 사용하지도 않음)

        // 경로 인가 작업
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/join","/login").permitAll() // 해당 경로는 모든 사용자 접근 허용
                .requestMatchers("/admin").hasRole("ADMIN") // ADMIN 권한을 가진 사용자만 접근 허용
                .anyRequest().authenticated() // 나머지 요청에 대해서는 로그인한 사용자만 접근 허용
        );

        // 수신한 JWT 토큰 검증 필터
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        // 커스텀 로그인 검증 필터(UsernamePasswordAuthenticationFilter를 대체)
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
