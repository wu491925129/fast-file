package com.wulong.project.web;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wulong.project.core.ProjectConstant;
import com.wulong.project.core.Result;
import com.wulong.project.core.ResultCode;
import com.wulong.project.core.ResultGenerator;
import com.wulong.project.model.FileInfo;
import com.wulong.project.service.FileInfoService;
import com.wulong.project.tool.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
* Created by CodeGenerator on 2019/01/17.
*/
@RestController
@RequestMapping("/file")
public class FileInfoController {
    @Resource
    private FileInfoService fileInfoService;

    @Value("${file_path}")
    private String path;

    private static Logger logger = LoggerFactory.getLogger(FileInfoController.class);

    /**
     * 根据id获取文件信息
     *
     * @param fileId
     * @return
     */
    @GetMapping("/getFileByFileId/{fileId}")
    public Result getFileInfo(@PathVariable String fileId) {
        FileInfo fileInfo = fileInfoService.findById(fileId);
        if (fileInfo == null) {
            return ResultGenerator.genFailResult(ResultCode.NOT_FOUND, "文件不存在");
        }
        return ResultGenerator.genSuccessResult(fileInfo);
    }

    /**
     * 根据文件名获取文件列表信息
     *
     * @param fileName
     * @param pageNumber
     * @param limit
     * @return
     */
    @PostMapping("/getFileListByFileName")
    public Result getFileList(@RequestParam String fileName, @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0") Integer limit) {
        PageHelper.startPage(pageNumber, limit);
        Condition condition = new Condition(FileInfo.class);
        condition.createCriteria().andLike("fileName", "%" + fileName + "%");
        List<FileInfo> fileList = fileInfoService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(fileList);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据日期获取文件列表信息
     *
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param limit
     * @return
     */
    @PostMapping("/getFileListByDate")
    public Result getFileList(@RequestParam Date startTime, @RequestParam Date endTime, @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "0") Integer limit) {
        if (startTime == null || endTime == null) {
            return ResultGenerator.genFailResult("参数不能为空！");
        }
        PageHelper.startPage(pageNumber, limit);
        Condition condition = new Condition(FileInfo.class);
        condition.createCriteria().andBetween("opTime", startTime, endTime);
        List<FileInfo> fileList = fileInfoService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(fileList);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return
     */
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file == null || "".equals(file)) {
            return ResultGenerator.genFailResult("上传失败，文件为空！");
        }
        Result result;
        FileInfo fileInfo = new FileInfo();
        // 获取当前年份月份 保存目录按照年月保存
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        String filePath = path + ProjectConstant.FILE_SEPARATOR + year + ProjectConstant.FILE_SEPARATOR + month;
        String fileName = file.getOriginalFilename();
        fileInfo.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
        fileInfo.setFileName(fileName);
        fileInfo.setFileSuffix(fileName.split("\\.")[1]);
        fileInfo.setStatus("1");
        fileInfo.setFileDesc(request.getParameter("fileDesc"));
        fileInfo.setExt1(request.getParameter("ext1"));
        fileInfo.setExt2(request.getParameter("ext2"));
        fileInfo.setExt3(request.getParameter("ext3"));
        fileInfo.setFileType(request.getParameter("fileType"));
        fileInfo.setOpTime(new Date());
        try {
            String[] pathList = filePath.split("\\\\");
            String ph = pathList[0];
            for (int i = 1; i < pathList.length; i++) {
                ph = ph + ProjectConstant.FILE_SEPARATOR + pathList[i];
            }
            fileInfo.setFilePath(ph + ProjectConstant.FILE_SEPARATOR);
            File fileDir = new File(ph);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String fp = ph + ProjectConstant.FILE_SEPARATOR + fileInfo.getFileId() + "_" + fileName;
            // 文件保存
            file.transferTo(new File(fp));
            // 图片生成缩略图
            ImageUtils.createThumbnail(file.getSize(),fp,fileInfo.getFileSuffix());
            fileInfoService.save(fileInfo);
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("fileId", fileInfo.getFileId());
            dataMap.put("fileName", fileName);
            result = ResultGenerator.genSuccessResult(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件" + file.getName() + "保存失败！");
            result = ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR, "文件保存失败！");
        }
        return result;
    }

    @JsonRawValue
    @GetMapping("/download/{fileId}")
    public Object downloadFile(@PathVariable String fileId,HttpServletResponse response) {
        FileInfo fileInfo = fileInfoService.findById(fileId);
        if (fileInfo != null) {
            File file = new File(fileInfo.getFilePath() + fileId + "_" + fileInfo.getFileName());
            return export(file, fileInfo.getFileName());
        }
        // 直接返回404错误页面
        response.setStatus(404);
        return ResultGenerator.genFailResult("文件不存在！");
    }

    /**
     * 文件下载
     *
     * @param fileId 文件id
     * @return
     */
    @JsonRawValue
    @GetMapping("/download/{fileId}/{thumb}")
    public Object downloadFile(@PathVariable String fileId,@PathVariable String thumb, HttpServletResponse response) {
        FileInfo fileInfo = fileInfoService.findById(fileId);
        if (fileInfo != null) {
            File  file = new File(fileInfo.getFilePath() + fileId + "_" + fileInfo.getFileName().replaceAll("\\."+ fileInfo.getFileSuffix(),"_thumb."+fileInfo.getFileSuffix()));
            return export(file, fileInfo.getFileName());
        }
        // 直接返回404错误页面
        response.setStatus(404);
        return ResultGenerator.genFailResult("文件不存在！");
    }

    @JsonRawValue
    @GetMapping("/download")
    public Object downloadFile(@RequestParam("f") String fileId,@RequestParam("s") int size,@RequestParam("q") double quality, HttpServletResponse response) {
        FileInfo fileInfo = fileInfoService.findById(fileId);
        if (fileInfo != null) {
            String filePath = fileInfo.getFilePath() + fileId + "_" + fileInfo.getFileName();
            File tmpFile = ImageUtils.getImgBy(filePath,size,quality,fileInfo.getFileSuffix());
            return export(tmpFile, fileInfo.getFileName());
        }
        // 直接返回404错误页面
        response.setStatus(404);
        return ResultGenerator.genFailResult("文件不存在！");
    }

    /**
     * 下载
     * @param file
     * @param fileName
     * @return
     */
    public ResponseEntity<FileSystemResource> export(File file, String fileName) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        String finalFileName = null;
        try {
            // 解决中文乱码问题
            finalFileName = URLEncoder.encode(fileName,"UTF-8");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", "attachment; filename=" + finalFileName);
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity .ok() .headers(headers) .contentLength(file.length()) .contentType(MediaType.parseMediaType("application/octet-stream")) .body(new FileSystemResource(file));
        }catch (UnsupportedEncodingException e){
            logger.info("文件名字转换异常");
            e.printStackTrace();
        }
        return null;
    }
}
