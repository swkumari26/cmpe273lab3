package com.cmpe273.Dropbox.service;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.entity.SharedContent;
import com.cmpe273.Dropbox.repository.ContentRepository;
import com.cmpe273.Dropbox.repository.SharedContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SharedContentService {
    @Autowired
    private SharedContentRepository sharedContentRepository;
    @Autowired
    private ContentService contentService;
    public boolean modifySharedContent(SharedContent sharedContent){
        sharedContentRepository.save(sharedContent);
        return true;
    }

    public List<Content> getSharedContent(Integer sharedToId){
        List<Integer> sharedContentList = new ArrayList<Integer>();
        sharedContentList = sharedContentRepository.findBysharedToId(sharedToId);
        System.out.println("shared content List is"+sharedContentList.toString());
        return contentService.getByIdList(sharedContentList);
    }
}
