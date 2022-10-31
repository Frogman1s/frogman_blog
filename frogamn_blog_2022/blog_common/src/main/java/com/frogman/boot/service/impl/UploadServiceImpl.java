package com.frogman.boot.service.impl;

import com.frogman.boot.domain.ResponseResult;
import com.frogman.boot.domain.enums.AppHttpCodeEnum;
import com.frogman.boot.exception.SystemException;
import com.frogman.boot.service.UploadService;
import com.frogman.boot.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String urlPrefix;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型 文件大小
        //获取原始文件名
        String filename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!filename.endsWith(".png")&&!filename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_NOT_ALLOWED);
        }
        //上传到oss
        String generateFilePath = PathUtils.generateFilePath(filename);
        String url=uploadOss(img,generateFilePath);
        return ResponseResult.okResult(url);
    }


    private String uploadOss(MultipartFile file, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;

        try {
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

        System.out.println(urlPrefix+key);
        return urlPrefix+key;
    }
}

