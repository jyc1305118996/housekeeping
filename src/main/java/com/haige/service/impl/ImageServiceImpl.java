package com.haige.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haige.common.bean.ResultInfo;
import com.haige.convert.ConvertUtils;
import com.haige.db.entity.FileInfoDO;
import com.haige.db.mapper.FileInfoDOMapper;
import com.haige.db.mapperExtend.FileInfoDOExtendMapper;
import com.haige.service.ImageService;
import com.haige.service.dto.FileInfoDTO;
import com.haige.util.DateUtils;
import com.haige.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Archie
 * @date 2020/2/4 11:24
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Value("${haige.images.basepath}")
    private String basePath;

    @Value("${haige.images.httppath}")
    private String httppath;

    @Autowired
    private FileInfoDOMapper fileInfoDOMapper;

    @Autowired
    private FileInfoDOExtendMapper fileInfoDOExtendMapper;

    @Override
    public Mono<ResultInfo> uploadImage(String type, FilePart filePart) {

        return Mono.just(filePart)
                .map(filePart1 -> {
                    String convertDate = DateUtils.convertToyyyyMMdd(LocalDate.now());
                    String fileName = filePart.filename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
                    // 校验文件是否合法
                    FileUtils.checkImageType(prefix);
                    File dir = new File(basePath + convertDate + "/");
                    String childPath = System.currentTimeMillis() + prefix;
                    File file = new File(dir, childPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    try {
                        file.createNewFile();
                        filePart.transferTo(file);
                    } catch (IOException e) {
                        throw new RuntimeException("图片上传失败");
                    }
                    FileInfoDO fileInfoDO = new FileInfoDO();
                    fileInfoDO.setFileRealPath(basePath + convertDate + "/" + childPath);
                    fileInfoDO.setFileName(fileName.substring(0, fileName.lastIndexOf("."))+ DateUtils.convertToString(LocalDateTime.now()));
                    fileInfoDO.setFilePath(httppath + convertDate + "/" + childPath);
                    fileInfoDO.setFileWork(type);
                    fileInfoDO.setFileIsUse("1");
                    fileInfoDOMapper.insertSelective(fileInfoDO);
                    FileInfoDTO fileInfoDTO = new FileInfoDTO();
                    BeanUtils.copyProperties(fileInfoDO, fileInfoDTO);
                    return fileInfoDTO;
                })
                .map(fileInfoDTO -> ResultInfo.buildSuccess(fileInfoDTO));
    }

    @Override
    public Mono<ResultInfo> findByType(String type, int index, int size) {
        return Mono.just(PageHelper.startPage(index, size))
                .map(page -> {
                    List<FileInfoDO> byType = fileInfoDOExtendMapper.findByType(type);
                    return new PageInfo<>(byType);
                })
                .map(pageInfo -> {
                    List<FileInfoDTO> fileInfoDTOS = ConvertUtils.convert(pageInfo.getList(), fileInfoDO -> {
                        FileInfoDTO fileInfoDTO = new FileInfoDTO();
                        BeanUtils.copyProperties(fileInfoDO, fileInfoDTO);
                        return fileInfoDTO;
                    });
                    ResultInfo<List<FileInfoDTO>> listResultInfo = ResultInfo.buildSuccess(fileInfoDTOS);
                    listResultInfo.setCount(pageInfo.getTotal());
                    return listResultInfo;
                });
    }

    @Override
    public Mono<ResultInfo> deleteById(int fileId) {
        return Mono.just(fileId)
                .map(id -> fileInfoDOMapper.selectByPrimaryKey(id))
                .map(fileInfoDO -> {
                    // 查找磁盘删除
                    File file = new File(fileInfoDO.getFileRealPath());
                    if (file.exists()) {
                        boolean isDelete = file.delete();
                        if (!isDelete) {
                            throw new RuntimeException("图片删除失败");
                        }
                    }
                    return fileInfoDO;
                })
                .map(fileInfoDO -> fileInfoDOMapper.deleteByPrimaryKey(fileInfoDO.getFileId()))
                .map(data -> ResultInfo.buildSuccess("success"));
    }
}
