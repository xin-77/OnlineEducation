package com.xin.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xin.commonutils.R;
import com.xin.eduservice.entity.EduCourse;
import com.xin.eduservice.entity.EduTeacher;
import com.xin.eduservice.service.EduCourseService;
import com.xin.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xin
 * @since 2023/1/12 16:29
 */
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Resource
    private EduCourseService courseService;
    @Resource
    private EduTeacherService teacherService;

    // 查询前8条热门课程。查询前4条老师
    @GetMapping("index")
    public R index(){
        // 查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        // 查询前4条老师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("eduList", eduList).data("teacherList", teacherList);

    }

}
