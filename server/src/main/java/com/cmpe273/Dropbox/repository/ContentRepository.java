package com.cmpe273.Dropbox.repository;

import com.cmpe273.Dropbox.entity.Content;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

public interface ContentRepository extends CrudRepository<Content,Integer> {
    Iterable<Content> findByRootFolder(@Param("rootFolder") Integer rootFolder);
    List<Content> findByContentPathIn(@Param("contentPath") List<String> contentPathList);
    List<Content> findByRootFolderIn(@Param("rootFolder") List<String> rootFolderList);
    List<Content> findByIdIsIn(@Param("id") List<Integer> idList);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Content con set con.star = case con.star when true then false when false then true else con.star end where con.id = ?1")
    int setStarFor(Integer id);

}
