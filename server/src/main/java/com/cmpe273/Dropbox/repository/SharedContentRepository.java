package com.cmpe273.Dropbox.repository;

import com.cmpe273.Dropbox.entity.SharedContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharedContentRepository extends CrudRepository<SharedContent,Integer>{
    @Query("select contentId from SharedContent sharedCon where sharedCon.sharedToId = ?1")
    List<Integer> findBysharedToId(Integer sharedToId);
}
