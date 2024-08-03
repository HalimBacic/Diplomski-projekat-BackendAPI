package backend.multidbapi.multidbapi.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.models.MediaDownloadRequest;
import backend.multidbapi.multidbapi.models.MediaRequest;
import backend.multidbapi.multidbapi.models.MediaResponse;
import backend.multidbapi.multidbapi.models.MediaTypeEnum;
import backend.multidbapi.multidbapi.models.dto.MediaDto;
import backend.multidbapi.multidbapi.models.exceptions.Messages;
import backend.multidbapi.multidbapi.models.exceptions.MultiDbException;
import backend.multidbapi.multidbapi.services.MediaService;
import backend.multidbapi.multidbapi.services.minioconfig.MinioStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MediaController {

    @Autowired
    public MediaService mediaService;

    @Autowired
    public MinioStorageService minioService;

    @Autowired
    private ModelMapper modelMapper;

    public MediaController(MediaService mediaservice) {
        this.mediaService = mediaservice;
    }

    @PostMapping(value = "upload", consumes = "multipart/form-data")
    public ResponseEntity<MediaResponse> UploadFile(@RequestParam("username") String username,
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file) throws MultiDbException {
        MediaRequest request = new MediaRequest();
        request.setFile(file);
        request.setName(name);
        request.setType(Enum.valueOf(MediaTypeEnum.class, type));
        request.setUsername(username);
        MediaResponse response = new MediaResponse();
        try {
            minioService.uploadFile("user-" + request.getUsername(), request.getName(),
                    request.getFile().getInputStream(), request.getType().toString());
            Media media = mediaService.UploadFile(request);
            MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
            response.setMetadata(mediaDto);
        } catch (IOException ex) {
            throw new MultiDbException(Messages.failtosaveonminio);
        } catch (Exception ex) {
            throw new MultiDbException(Messages.failtosave);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("preview")
    public ResponseEntity<MediaResponse> PreviewFile(@RequestBody MediaDownloadRequest request)
            throws MultiDbException {
        try {
            Media media = mediaService.PreviewFile(request.getMediaId());
            MediaDto mediaDto = modelMapper.map(media, MediaDto.class);
            InputStream multimedia = minioService.downloadFile(request.getName(), request.getUsername());
            MediaResponse response = new MediaResponse(mediaDto,multimedia);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + request.getName());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + request.getName())
                    .body(response);
        } catch (Exception e) {
            throw new MultiDbException(Messages.downloadproblem);
        }
    }

    @GetMapping("download")
    public ResponseEntity<Resource> DownloadFile(@RequestParam Long mediaId, @RequestParam String username) {
        try {
            String fileName = mediaService.DownloadFile(mediaId);
            InputStream multimedia = minioService.downloadFile(fileName, username);
            InputStreamResource resource = new InputStreamResource(multimedia);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
