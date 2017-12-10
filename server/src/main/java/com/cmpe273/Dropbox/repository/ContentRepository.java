package com.cmpe273.Dropbox.repository;

import com.cmpe273.Dropbox.entity.Content;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends CrudRepository<Content,Integer> {
    Iterable<Content> findByRootFolder(@Param("rootFolder") Integer rootFolder);
}
