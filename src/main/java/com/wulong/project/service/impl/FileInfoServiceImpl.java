package com.wulong.project.service.impl;

import com.wulong.project.dao.FileInfoMapper;
import com.wulong.project.model.FileInfo;
import com.wulong.project.service.FileInfoService;
import com.wulong.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class FileInfoServiceImpl extends AbstractService<FileInfo> implements FileInfoService {
    @Resource
    private FileInfoMapper fileInfoMapper;

}
