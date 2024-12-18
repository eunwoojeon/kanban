package com.eunwoojeon.kanban.jwt;

import com.eunwoojeon.kanban.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

/*
1. (@EnableWebSecurity)security configuration에서 HttpSecurity객체 LoginFilter(=UsernamePasswordAuthenticationFilter)
2. filter에서 credentials를 수신하면 username, password를 담은 UsernamePasswordAuthenticationToken을 생성하여 authenticationManager에 전달
3. authenticationManager에서 인증 여부에 따라 AuthenticationSuccessHandler/AuthenticationFailureHandler가 호출
4. unsuccessfulAuthentication/unsuccessfulAuthentication이 실행된다.
*/
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // username + password 담은 인증 전 토큰 객체 생성
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // token을 검증할 AuthenticationManager에 token 전달(manager는 적절한 authenticationProvider에 인증 위임)
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공시 실행할 메소드(jwt 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername(); // username 추출

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority(); // role 추출

        String token = jwtUtil.createJwt(username, role, 1000 * 60 * 60); // 60min
        System.out.println("success");

        // HTTP 방식 인증 헤더 양식(header에 실어 token 전송)
        response.addHeader("Authorization", "Bearer " + token);
    };

    // 로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException faided) {
        System.out.println("fail");
        response.setStatus(401); // 401 : unauthorized
    }
}
