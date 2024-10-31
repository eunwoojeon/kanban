package com.eunwoojeon.kanban.service;

import com.eunwoojeon.kanban.database.Entities.UserEntity;
import com.eunwoojeon.kanban.database.Repositories.UserRepository;
import com.eunwoojeon.kanban.dto.DTO.JoinDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isExistEmail(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        try {
            Boolean isExist = userRepository.existsByUsername(username);
            if (isExist) return false;

            UserEntity user = UserEntity.builder()
                    .username(username)
                    .password(bCryptPasswordEncoder.encode(password)) // 해시 함수로 암호화
                    .role("ROLE_USER")
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
