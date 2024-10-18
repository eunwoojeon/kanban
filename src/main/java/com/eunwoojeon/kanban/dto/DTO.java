package com.eunwoojeon.kanban.dto;

import lombok.*;

public class DTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String email;
        private String password;
        private String username;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO {
        private String email;
        private String username;
    }
}
