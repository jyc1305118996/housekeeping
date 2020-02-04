package com.haige.service;

import com.haige.common.bean.ResultInfo;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

/**
 * @author Archie
 * @date 2020/2/4 11:24
 */

public interface ImageService {
    Mono<ResultInfo> uploadImage(String type, FilePart filePart);

    Mono<ResultInfo> findByType(String type, int index, int size);
}
