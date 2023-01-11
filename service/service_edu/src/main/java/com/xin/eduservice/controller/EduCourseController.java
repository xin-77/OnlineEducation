package com.xin.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xin.commonutils.R;
import com.xin.eduservice.entity.EduCourse;
import com.xin.eduservice.entity.EduTeacher;
import com.xin.eduservice.entity.vo.CourseInfoVo;
import com.xin.eduservice.entity.vo.CoursePublishVo;
import com.xin.eduservice.entity.vo.CourseQuery;
import com.xin.eduservice.entity.vo.TeacherQuery;
import com.xin.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xin
 * @since 2023-01-06
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Resource
    private EduCourseService courseService;

    //课程列表 基本实现
    @GetMapping("pageCourse/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit) {
        // 创建page对象
        Page<EduCourse> page = new Page<>(current, limit);

        courseService.page(page, null);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    // 条件查询带分页的方法
    @ApiOperation(value = "分页多条件查询课程信息")
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery) {
        // 创建page对象
        Page<EduCourse> page = new Page<>(current, limit);
        // 构造条件
        LambdaQueryWrapper<EduCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(courseQuery.getTitle()), EduCourse::getTitle, courseQuery.getTitle());
        wrapper.eq(!StringUtils.isEmpty(courseQuery.getStatus()), EduCourse::getStatus, courseQuery.getStatus());


        // 调用方法实现条件分页查询
        courseService.page(page, wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("total", total).data("rows", records);


    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String id = courseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId", id);
    }


    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublicCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publicCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

