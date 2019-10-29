package com.haige.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDO {
    private Integer fileId;

    private String fileRealName;

    private String fileName;

    private String fileRealPath;

    private String filePath;

    private String fileWork;

    private Integer fileWorkId;

    private Integer fileBatch;

    private String fileIsUse;

}