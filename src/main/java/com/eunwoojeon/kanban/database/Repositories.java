package com.eunwoojeon.kanban.database;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eunwoojeon.kanban.database.Entities.*;

@Configurable
public interface Repositories {

    @Repository
    @Configurable
    public interface UserRepository extends JpaRepository<UserEntity, Long> {
        Boolean existsByUsername(String username);
        UserEntity findByUsername(String username);
    }
}
