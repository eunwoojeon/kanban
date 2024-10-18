package com.eunwoojeon.kanban.service;

import com.eunwoojeon.kanban.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eunwoojeon.kanban.database.Repositories.*;
import com.eunwoojeon.kanban.database.Entities.*;

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
