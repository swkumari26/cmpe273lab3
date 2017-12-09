package com.cmpe273.Dropbox.controller;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.service.ContentService;
import com.sun.net.httpserver.Authenticator;
import com.sun.xml.internal.fastinfoset.stax.events.EmptyIterator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Collections;

@Controller
@CrossOrigin(origins="*")
@RequestMapping(path = "/content")
public class ContentController {
    @Autowired
    ContentService contentService;
    @GetMapping(path="/{rootFolder}")
    public @ResponseBody Iterable<String> getContens(@PathVariable String rootFolder){
            String absolutePath = new File("dropbox").getPath();
            File root = new File(absolutePath,rootFolder);
            System.out.println("result is:"+contentService.listDir(root));
            return contentService.listDir(root);
//        return contentService.getByRootFolder(Integer.parseInt(rootFolder));
    }
    @PostMapping(path = "/createFolder", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFolder(@RequestBody String path){
        JSONObject jsonObject = new JSONObject(path);
        String folderPath=jsonObject.getString("path");
        String absolutePath = new File("dropbox").getPath()+'/'+folderPath;
        if(contentService.createDir(new File(absolutePath)))
            return new ResponseEntity(null, HttpStatus.CREATED);
        else
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }
    @PostMapping(path = "/deleteContent", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteContent(@RequestBody String path){
        JSONObject jsonObject = new JSONObject(path);
        String contentPath=jsonObject.getString("path");
        String absolutePath = new File("dropbox").getPath()+'/'+contentPath;
        if(contentService.deleteContent(new File(absolutePath)))
            return new ResponseEntity(null, HttpStatus.OK);
        else
            return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public ResponseEntity<?> fileUpload(@RequestParam("path") String path,@RequestParam("file") MultipartFile file) {
        if(contentService.uploadFile(path,file))
            return new ResponseEntity(null, HttpStatus.CREATED);
        else
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }
}