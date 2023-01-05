package com.xin.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xin
 * @since 2023/1/4 13:50
 */

public interface OssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);
}
