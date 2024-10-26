package com.eunwoojeon.kanban.service;

import com.eunwoojeon.kanban.database.Entities.UserEntity;
import com.eunwoojeon.kanban.database.Repositories.UserRepository;
import com.eunwoojeon.kanban.dto.DTO.JoinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean joinProcess(JoinDTO joinDTO) {
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        String username = joinDTO.getUsername();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) return false;

        UserEntity user = UserEntity.builder()
                .email(email)
                .username(username)
                .password(bCryptPasswordEncoder.encode(password)) // 해시 함수로 암호화
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);
        return true;
    }
}
