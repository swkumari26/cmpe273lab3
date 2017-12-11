package com.cmpe273.Dropbox.service;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.entity.UserGroup;
import com.cmpe273.Dropbox.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;


@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ContentService contentService;
    public List<Content> getGroups(Integer id){
        return contentService.getByRootFolderIn(groupRepository.findByMemberId(id));
    }
    public boolean createGroup(UserGroup group){
        String absolutePath = '/'+new File("dropbox").getPath()+'/'+group.getGroupName();
        Content content = new Content();
        content.setContentPath(absolutePath);
        content.setRootFolder(group.getGroupName());
        content.setContentName(group.getGroupName());
        content.setCreatedBy(group.getMemberId());
        content.setCreatedOn(new Date());
        content.setStar(false);
        if(contentService.createDir(new File(absolutePath),content)) {
            groupRepository.save(group);
            return true;
        }
        else
            return false;
    }
    public void removeGroup(Integer id){
        groupRepository.delete(id);
    }
}
