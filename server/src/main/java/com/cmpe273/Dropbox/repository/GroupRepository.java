package com.cmpe273.Dropbox.repository;

import com.cmpe273.Dropbox.entity.UserGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<UserGroup,Integer>{
    @Query("select groupName from UserGroup ug where ug.memberId = ?1")
    List<String> findByMemberId(Integer id);
}
