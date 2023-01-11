package com.xin.eduservice.controller;


import com.xin.commonutils.R;
import com.xin.eduservice.client.VodClient;
import com.xin.eduservice.entity.EduVideo;
import com.xin.eduservice.service.EduVideoService;
import com.xin.servicebase.exceptionHandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xin
 * @since 2023-01-06
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
@Slf4j
public class EduVideoController {
    @Resource
    private EduVideoService videoService;

    @Resource
    private VodClient vodClient;


    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        log.info(eduVideo.toString());
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    // 删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        log.info(videoSourceId+"haha");
        if(!StringUtils.isEmpty(videoSourceId)){
            log.info("删除视频");
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }

        }

        videoService.removeById(id);
        return R.ok();
    }

    // 根据小节Id查询小节
    @GetMapping("getVideoInfo/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return R.ok().data("video", video);
    }
    //修改小节 TODO
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        videoService.updateById(video);
        return R.ok();
    }
}

