package com.medium.githubactions.controller;

import com.medium.githubactions.adapter.MinioAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/stream")
public class StreamController {

    private final MinioAdapter minioAdapter;

    @Autowired
    public StreamController(MinioAdapter minioAdapter) {
        this.minioAdapter = minioAdapter;
    }

    @GetMapping("/{objectName}")
    public ResponseEntity<InputStreamResource> streamResourceFromDisk(@PathVariable String objectName) throws Exception {
        String FILE_DIRECTORY = "uploads/"+objectName;
        File file = new File(FILE_DIRECTORY);
        InputStream inputStream = new FileInputStream(FILE_DIRECTORY);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Ranges", "bytes");
        headers.set("Content-Type", "video/mp4");
        headers.set("Content-Range", "bytes 50-1025/17839845");
        headers.set("Content-Length", String.valueOf(file.length()));
        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }

    @GetMapping("/minio/{objectName}")
    public ResponseEntity<InputStreamResource> streamResourceFromMinio(@PathVariable String objectName) {
        var length = minioAdapter.getFileLength(objectName);
        InputStream inputStream = minioAdapter.getFileAsInputStream(objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Ranges", "bytes");
        headers.set("Content-Type", "video/mp4");
        headers.set("Content-Range", "bytes 50-1025/17839845");
        headers.set("Content-Length", String.valueOf(length));
        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
    }

}
