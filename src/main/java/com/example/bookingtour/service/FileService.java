package com.example.bookingtour.service;

import com.example.bookingtour.exception.SaveFileException;
import com.example.bookingtour.exception.FileNotFoundException;
import com.example.bookingtour.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService implements FileServiceImp {
    @Value("/Users/lequocvinh/Desktop/upload")
    private String pathSaveFile;


    @Override
    public boolean saveFile(MultipartFile file) {
        boolean isSuccess = false;
        try{
            Path root = Paths.get(pathSaveFile);
            if(!Files.exists(root)){
                Files.createDirectories(root);
            }
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);// resolve la ham dau / them ten file
            return true;
        }catch(Exception e){
            throw new SaveFileException(e.getMessage());
        }
    }

    @Override
    public Resource load(String fileName) {
        try{
            Path root = Paths.get(pathSaveFile).resolve(fileName);
            Resource resource = new UrlResource(root.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        }catch(Exception e){
            throw new FileNotFoundException();
        }
    }
}
