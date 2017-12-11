package com.cmpe273.Dropbox.repository;

import com.cmpe273.Dropbox.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer>{
    List<User> findByEmailAndPassword(String email,String password);
    User findByEmail(@Param("email") String email);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.noContentCreated = u.noContentCreated+1 where u.id = ?1")
    void contentCreatedCount(Integer id);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.noContentDeleted = u.noContentDeleted+1 where u.id = ?1")
    void contentDeletedCount(Integer id);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.noContentShared = u.noContentShared+1 where u.id = ?1")
    void contentSharedCount(Integer id);
}
