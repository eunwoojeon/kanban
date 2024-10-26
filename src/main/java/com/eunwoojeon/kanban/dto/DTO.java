package com.eunwoojeon.kanban.dto;

import lombok.*;

public class DTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO {
        private String username;
        private String password;
    }
}
