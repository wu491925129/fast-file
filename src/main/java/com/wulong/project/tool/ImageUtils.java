package com.wulong.project.tool;

import com.alibaba.druid.sql.visitor.functions.If;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wulong
 * @Date: 2019/1/25 9:35
 * @Email: 491925129@qq.com
 *
 * 图片处理工具类
 */
public class ImageUtils {

    /**
     * 生成缩略图
     * @param imagePath     图片文件路径
     * @param imageSuffix   图片后缀
     * @return
     */
    public static boolean createThumbnail(long imageSize,String imagePath,String imageSuffix) {
        // 判断是否是图片
        String[] imgSuffixs = {"BMP","DIB","EMF","GIF","ICB","ICO","JPG","JPEG","PBM","PCD","PCX","PGM","PNG","PPM","PSD","PSP","RLE","SGI","TGA","TIF"};
        List<String> list = Arrays.asList(imgSuffixs);
        String upperSuffix = imageSuffix.toUpperCase();
        if (!list.contains(upperSuffix)) {
            return false;
        }
        try {
            String thumbImagePath = imagePath.replaceAll(("\\."+imageSuffix),"_thumb"+"."+imageSuffix);
            Map<String,Double> map = getScaleAndQualit(imageSize);
            Thumbnails
                    .of(imagePath)
                    .scale(map.get("scale"))
                    .outputQuality(map.get("quality"))
                    .toFile(thumbImagePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Map<String,Double> getScaleAndQualit(long imageSize) {
        Map<String,Double> result = new HashMap<>();
        if (imageSize > 0 && imageSize < 512 * 1024) {
            // 原图在 0-512kb
            // 缩放5倍
            // 质量缩放2倍
            result.put("scale",0.2);
            result.put("quality",0.5);
        } else if (imageSize > 512 * 1024 && imageSize < 1024 * 1024) {
            // 原图在 512kb-1024kb之间
            result.put("scale",0.2);
            result.put("quality",0.4);
        }else if (imageSize > 1024 * 1024 && imageSize < 2048 * 1024) {
            // 原图在 512kb-1024kb之间
            result.put("scale",0.2);
            result.put("quality",0.3);
        }else if (imageSize > 2048 * 1024) {
            // 原图在 512kb-1024kb之间
            result.put("scale",0.15);
            result.put("quality",0.5);
        }
        return result;
    }
}
