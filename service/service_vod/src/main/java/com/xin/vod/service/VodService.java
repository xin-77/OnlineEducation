package com.xin.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xin
 * @since 2023/1/10 20:59
 */
public interface VodService {

    String uploadVideoAly(MultipartFile file);

    void removeMoreAlyVideo(List videoIdList);
}
