package com.xin.eduservice.client;

import com.xin.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xin
 * @since 2023/1/11 13:30
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
