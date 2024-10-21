package com.eunwoojeon.kanban.service;

import com.eunwoojeon.kanban.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eunwoojeon.kanban.database.Repositories.*;
import com.eunwoojeon.kanban.database.Entities.*;


/*
1. UsernamePasswordAuthenticationFilter 구현클래스에서 AuthenticationManager에 token 전달
2. AuthenticationManager(AuthenticationProvider)는 UserDetailsService 구현클래스를 찾아 인증 정보 전달
3. UserDetailsService는 사용자 정보(username,password)를 통해 유저 확인
4. UserDetailsservice는 UserDetail 객체를 AuthenticationManager(AuthenticationProvider)에 반환
5. AuthenticationManager는 PasswordEncoder를 사용해 UserDetails의 비밀번호를 검증
6. 모든 검증이 완료되면 최종적으로 UsernamePasswordAuthenticationToken은 filter에 의해 SecurityContextHolder에 설정된다.
*/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 실제 인증 처리 위치
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB에서 조회
        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity != null) {
            // user info를 UserDetails에 담아서 return하면 AuthenticationManage가 검증
            return new CustomUserDetails(userEntity);
        }
        return null;
    }
}
