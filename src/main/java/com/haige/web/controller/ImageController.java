package com.haige.web.controller;

import com.haige.common.bean.ResultInfo;
import com.haige.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;

/**
 * @author Archie
 * @date 2020/2/4 11:05
 */
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> uploadImage(@RequestParam("type") String type, @RequestPart("file") FilePart filePart) {
        return imageService.uploadImage(type, filePart);
    }

    @GetMapping(value = "/findByType", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> findByType(@RequestParam(value = "type", required = false) String type, @RequestParam("index") int index, @RequestParam("size") int size) {
        return imageService.findByType(type, index, size);
    }

    @DeleteMapping(value = "/{fileId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mono<ResultInfo> deleteById(@PathVariable(value = "fileId") int fileId) {
        return imageService.deleteById(fileId);
    }
}
