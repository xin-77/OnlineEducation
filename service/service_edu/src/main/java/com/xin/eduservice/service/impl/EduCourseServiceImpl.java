package com.xin.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xin.eduservice.entity.EduCourse;
import com.xin.eduservice.entity.EduCourseDescription;
import com.xin.eduservice.entity.EduTeacher;
import com.xin.eduservice.entity.frontVo.CourseFrontVo;
import com.xin.eduservice.entity.frontVo.CourseWebVo;
import com.xin.eduservice.entity.vo.CourseInfoVo;
import com.xin.eduservice.entity.vo.CoursePublishVo;
import com.xin.eduservice.mapper.EduCourseMapper;
import com.xin.eduservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xin.servicebase.exceptionHandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xin
 * @since 2023-01-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    EduCourseDescriptionService descriptionService;

    //注入小节和章节service
    @Resource
    private EduVideoService eduVideoService;

    @Resource
    private EduChapterService chapterService;

    @Resource
    private EduTeacherService teacherService;

    // 添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1 向课程标添加课程基本信息
        // CourseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        boolean save = this.save(eduCourse);
        if(!save){
            // 添加失败
            throw  new GuliException(20001, "添加课程信息失败");
        }
        // 获取添加课程后的课程Id
        String cid = eduCourse.getId();

        // 2 向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        descriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = descriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new GuliException(20001,"修改课程信息失败！");
        }
        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        descriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo publicCourseInfo(String id) {
        // 调用Mapper
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        descriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) { //失败返回
            throw new GuliException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        LambdaQueryWrapper<EduCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId()), EduCourse::getSubjectParentId, courseFrontVo.getSubjectParentId());
        wrapper.eq(!StringUtils.isEmpty(courseFrontVo.getSubjectId()), EduCourse::getSubjectId, courseFrontVo.getSubjectId());
        wrapper.orderByDesc(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort()),EduCourse::getBuyCount);
        wrapper.orderByDesc(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort()),EduCourse::getGmtCreate);
        wrapper.orderByDesc(!StringUtils.isEmpty(courseFrontVo.getPriceSort()),EduCourse::getPrice);

        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long total = pageCourse.getTotal();
        long size = pageCourse.getSize();
        boolean hasNext  = pageCourse.hasNext();
        boolean hasPrevious  = pageCourse.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;


    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        this.updatePageViewCount(courseId);
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);

        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
