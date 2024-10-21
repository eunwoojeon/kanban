package com.eunwoojeon.kanban.database;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eunwoojeon.kanban.database.Entities.*;

@Configurable(autowire = Autowire.BY_TYPE)
public interface Repositories {

    @Repository
    @Configurable(autowire = Autowire.BY_TYPE)
    public interface UserRepository extends JpaRepository<UserEntity, Long> {
        Boolean existsByEmail(String email);
        UserEntity findByUsername(String username);
    }
}
