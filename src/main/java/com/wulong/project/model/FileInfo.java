package com.wulong.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "file_info")
public class FileInfo {
    /**
     * 文件id
     */
    @Id
    @Column(name = "file_id")
    private String fileId;

    /**
     * 文件后缀
     */
    @Column(name = "file_suffix")
    private String fileSuffix;

    /**
     * 操作时间
     */
    @Column(name = "op_time")
    private Date opTime;

    /**
     * 文件预留类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件状态
     */
    private String status;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 缓存文件名
     */
    @Column(name = "temp_name")
    private String tempName;

    /**
     * 缓存文件路径
     */
    @Column(name = "temp_path")
    private String tempPath;

    /**
     * 文件描述信息
     */
    @Column(name = "file_desc")
    private String fileDesc;

    /**
     * 扩展字段1
     */
    @Column(name = "ext_1")
    private String ext1;

    /**
     * 扩展字段2
     */
    @Column(name = "ext_2")
    private String ext2;

    /**
     * 扩展字段3
     */
    @Column(name = "ext_3")
    private String ext3;

    /**
     * 获取文件id
     *
     * @return file_id - 文件id
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * 设置文件id
     *
     * @param fileId 文件id
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取文件后缀
     *
     * @return file_suffix - 文件后缀
     */
    public String getFileSuffix() {
        return fileSuffix;
    }

    /**
     * 设置文件后缀
     *
     * @param fileSuffix 文件后缀
     */
    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    /**
     * 获取操作时间
     *
     * @return op_time - 操作时间
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * 设置操作时间
     *
     * @param opTime 操作时间
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * 获取文件预留类型
     *
     * @return file_type - 文件预留类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件预留类型
     *
     * @param fileType 文件预留类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取文件状态
     *
     * @return status - 文件状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置文件状态
     *
     * @param status 文件状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取文件名
     *
     * @return file_name - 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名
     *
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件路径
     *
     * @return file_path - 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取缓存文件名
     *
     * @return temp_name - 缓存文件名
     */
    public String getTempName() {
        return tempName;
    }

    /**
     * 设置缓存文件名
     *
     * @param tempName 缓存文件名
     */
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    /**
     * 获取缓存文件路径
     *
     * @return temp_path - 缓存文件路径
     */
    public String getTempPath() {
        return tempPath;
    }

    /**
     * 设置缓存文件路径
     *
     * @param tempPath 缓存文件路径
     */
    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    /**
     * 获取文件描述信息
     *
     * @return file_desc - 文件描述信息
     */
    public String getFileDesc() {
        return fileDesc;
    }

    /**
     * 设置文件描述信息
     *
     * @param fileDesc 文件描述信息
     */
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * 获取扩展字段1
     *
     * @return ext_1 - 扩展字段1
     */
    public String getExt1() {
        return ext1;
    }

    /**
     * 设置扩展字段1
     *
     * @param ext1 扩展字段1
     */
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    /**
     * 获取扩展字段2
     *
     * @return ext_2 - 扩展字段2
     */
    public String getExt2() {
        return ext2;
    }

    /**
     * 设置扩展字段2
     *
     * @param ext2 扩展字段2
     */
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    /**
     * 获取扩展字段3
     *
     * @return ext_3 - 扩展字段3
     */
    public String getExt3() {
        return ext3;
    }

    /**
     * 设置扩展字段3
     *
     * @param ext3 扩展字段3
     */
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
}