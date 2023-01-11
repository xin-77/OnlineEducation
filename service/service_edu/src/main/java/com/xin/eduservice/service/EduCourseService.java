package com.xin.eduservice.service;

import com.xin.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xin.eduservice.entity.vo.CourseInfoVo;
import com.xin.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xin
 * @since 2023-01-06
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publicCourseInfo(String id);

    void removeCourse(String courseId);
}
