package com.cmpe273.Dropbox.service;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.entity.User;
import com.cmpe273.Dropbox.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;
    public Iterable<Content> getByRootFolder(Integer rootFolder){
        return contentRepository.findByRootFolder(rootFolder);
    }
    public Content getById(int id){
        return  contentRepository.findOne(id);
    }
    public void modifyContent(Content content){
        contentRepository.save(content);
    }
    public void deleteCon(int id) {contentRepository.delete(id);}
    public List<String> listDir(File rootFolder){
        List<String> result = new ArrayList<String>();
        result.add(rootFolder.getPath().replace("\\","/"));
        if(rootFolder.isDirectory()){
            for(String childname:rootFolder.list()){                File child = new File(rootFolder,childname);
                result.addAll(listDir(child));
            }
        }
        return result;
    }
    public Boolean createDir(File folderPath,Content content){
        if(folderPath.exists()){
            return false;
        }
        else{
            folderPath.mkdir();
            contentRepository.save(content);
            return true;
        }
    }
    public Boolean deleteContent(File contentPath){
        if(contentPath.exists()){
            File[] files = contentPath.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteContent(files[i]);
                    }
                    else {
                        files[i].delete();

                    }
                }
            }
        }
        return(contentPath.delete());
    }
    public Boolean uploadFile(String path,String rootFolder,String createdBy,MultipartFile file){
        String pathWithName = path+'/'+file.getOriginalFilename();
        String absolutePath = new File("dropbox").getPath()+'/'+pathWithName;
        Path contentPath = Paths.get(absolutePath);
        if(Files.exists(contentPath)) {
            return false;
        }
        else
            try {
                Files.write(contentPath, file.getBytes());
                Content content = new Content();
                content.setContentPath(pathWithName);
                content.setRootFolder(rootFolder);
                content.setContentName(file.getOriginalFilename());
                content.setCreatedBy(createdBy);
                content.setCreatedOn(new Date());
                contentRepository.save(content);
                return true;
            } catch (IOException e) {
                return false;
            }
    }
}
