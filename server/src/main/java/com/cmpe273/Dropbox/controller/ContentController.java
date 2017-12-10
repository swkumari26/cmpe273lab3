package com.cmpe273.Dropbox.controller;

import com.cmpe273.Dropbox.entity.Content;
import com.cmpe273.Dropbox.service.ContentService;
import org.springframework.core.io.FileSystemResource;
import com.sun.net.httpserver.Authenticator;
import com.sun.xml.internal.fastinfoset.stax.events.EmptyIterator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    }
    @PostMapping(path = "/createFolder", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFolder(@RequestBody Content content){
        String folderPath=content.getContentPath();
        String absolutePath = new File("dropbox").getPath()+'/'+folderPath;
        if(contentService.createDir(new File(absolutePath),content))
            return new ResponseEntity(null, HttpStatus.CREATED);
        else
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable String id){
        String contentPath = contentService.getById(Integer.parseInt(id)).getContentPath();
        String absolutePath = new File("dropbox").getPath()+'/'+contentPath;
        if(contentService.deleteContent(new File(absolutePath))) {
            contentService.deleteCon(Integer.parseInt(id));
            return new ResponseEntity(null, HttpStatus.OK);
        }
        else
            return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public ResponseEntity<?> fileUpload(@RequestParam("path") String path,
                                        @RequestParam("rootFolder") String rootFolder,
                                        @RequestParam("createdBy") String createdBy,
                                        @RequestParam("file") MultipartFile file) {
        if(contentService.uploadFile(path,rootFolder,createdBy,file))
            return new ResponseEntity(null, HttpStatus.CREATED);
        else
            return new ResponseEntity<Error>(HttpStatus.CONFLICT);
    }
    @GetMapping(path = "/download/{id}")
    public void download(@PathVariable String id,HttpServletResponse response)  {
        String contentPath = contentService.getById(Integer.parseInt(id)).getContentPath();
        String absolutePath = new File("dropbox").getPath()+contentPath;
        File path = new File(absolutePath);
        System.out.println("file path is:"+absolutePath);
        try {
            InputStream is = new FileInputStream(path);
            response.setHeader("Content-disposition", "attachment; filename="+ contentService.getById(Integer.parseInt(id)).getContentName());
            StreamUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }
}