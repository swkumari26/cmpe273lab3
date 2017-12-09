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
import java.util.List;
import java.io.File;

@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;
    public Iterable<Content> getByRootFolder(Integer rootFolder){
        return contentRepository.findByRootFolder(rootFolder);
    }
    public Content getById(Long id){
        return  contentRepository.findOne(id);
    }
    public void modifyContent(Content content){
        contentRepository.save(content);
    }

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
    public Boolean createDir(File folderPath){
        if(folderPath.exists()){
            return false;
        }
        else{
            folderPath.mkdir();
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
    public Boolean uploadFile(String path,MultipartFile file){
        String absolutePath = new File("dropbox").getPath()+'/'+path+'/'+file.getOriginalFilename();
        Path contentPath = Paths.get(absolutePath);
        if(Files.exists(contentPath)) {
            return false;
        }
        else
            try {
                Files.write(contentPath, file.getBytes());
                return true;
            } catch (IOException e) {
                return false;
            }
    }
}
