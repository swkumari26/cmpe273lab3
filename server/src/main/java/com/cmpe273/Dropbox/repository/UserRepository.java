package com.cmpe273.Dropbox.repository;

import com.cmpe273.Dropbox.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Long>{
    List<User> findByEmailAndPassword(String email,String password);
    User findByEmail(@Param("email") String email);
}
