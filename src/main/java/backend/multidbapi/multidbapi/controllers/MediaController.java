package backend.multidbapi.multidbapi.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import backend.multidbapi.multidbapi.models.MediaRequest;
import backend.multidbapi.multidbapi.models.MediaResponse;
import backend.multidbapi.multidbapi.models.exceptions.Messages;
import backend.multidbapi.multidbapi.models.exceptions.MultiDbException;
import backend.multidbapi.multidbapi.repository.UserRepository;
import backend.multidbapi.multidbapi.services.MediaService;
import backend.multidbapi.multidbapi.services.minioconfig.MinioStorageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class MediaController {
    
    @Autowired
    public MediaService mediaService;

    @Autowired
    public MinioStorageService minioService;

    public MediaController(MediaService mediaservice) {
        this.mediaService = mediaservice;
    }

    @PostMapping("upload")
    public ResponseEntity<MediaResponse> UploadFile(@RequestBody MediaRequest request) throws MultiDbException 
    {
        MediaResponse response = new MediaResponse();
        try{
            response.setMetadata(mediaService.UploadFile(request));
            minioService.uploadFile(request.getUsername(), request.getName(), request.File.getInputStream(), request.getType().toString());
        }
        catch(IOException ex)
        {
            throw new MultiDbException(Messages.failtosaveonminio);
        }
        catch(Exception ex)
        {
            throw new MultiDbException(Messages.failtosave);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    

    @PostMapping("download")
    public ResponseEntity<MediaResponse> DownloadFile(@RequestBody MediaRequest request) throws MultiDbException {
        MediaResponse response = new MediaResponse();
        try {
            response.setMetadata(mediaService.DownloadFile(request));
            response.setData(minioService.downloadFile(request.getName(), request.getUserId()));
        } catch (Exception e) {
            throw new MultiDbException(Messages.downloadproblem);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
