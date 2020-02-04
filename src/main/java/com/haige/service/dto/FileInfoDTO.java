package com.haige.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDTO {
    private Integer fileId;

    private String fileName;

    private String fileRealPath;

    private String filePath;

    private String fileWork;
}