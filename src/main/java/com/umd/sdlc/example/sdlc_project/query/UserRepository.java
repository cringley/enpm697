package com.umd.sdlc.example.sdlc_project.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.umd.sdlc.example.sdlc_project.models.User;

@Component
public interface UserRepository extends JpaRepository<User, Long>{
    @Query(value = "INSERT INTO Users (name, email, password, isAdmin) VALUES(:name, :email, :password, false)", nativeQuery = true)
    @Modifying
    void insertNewUser(@Param("name") String name, @Param("email") String email, @Param("password") String password);

    @Query(value = "INSERT INTO Users (name, email, password, isAdmin) VALUES(:name, :email, :password, :admin)", nativeQuery = true)
    @Modifying
    void insertNewUser(@Param("name") String name, @Param("email") String email, @Param("password") String password, @Param("admin") Boolean admin);
    
    @Query("SELECT u FROM Users u WHERE u.email = :email AND u.password = :password")
    User findUserByEmailPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM Users u WHERE u.name = :name")
    User findUserByName(@Param("name") String name);

    @Query(value = "SELECT * FROM Users u WHERE u.email = ?1", nativeQuery = true)
    User findUserByEmail(String email);

    @Query(value = "DELETE FROM Users u WHERE u.id = ?1", nativeQuery = true)
    @Modifying
    void deleteUser(Long id);
}
