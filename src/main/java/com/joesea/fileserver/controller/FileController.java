package com.joesea.fileserver.controller;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    Logger logger = Logger.getLogger(FileController.class);

    @RequestMapping(value = "test")
    @ResponseBody
    public String test() {
        logger.info("hello world!");
        return "success";
    }

    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public String fileUpload(MultipartFile multipartFile) {
        if (null != multipartFile) {
            logger.info(multipartFile.getOriginalFilename());
        }

        return "success";
    }

    @RequestMapping(value = "fileDownload")
    @ResponseBody
    public ResponseEntity<byte[]> fileDownload(String filePath) {
        ResponseEntity<byte[]> responseEntity = null;
        File file=new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        String fileName= file.getName();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }
}
