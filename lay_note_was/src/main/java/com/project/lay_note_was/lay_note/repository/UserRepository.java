package com.project.lay_note_was.lay_note.repository;

import com.project.lay_note_was.lay_note.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    boolean existsByNickName(String nickName);

    Optional<User> findByUserNameAndUserEmail(String userName, String userEmail);
}
