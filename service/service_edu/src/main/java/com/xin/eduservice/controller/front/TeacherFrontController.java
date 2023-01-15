package com.xin.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xin.commonutils.R;
import com.xin.eduservice.entity.EduTeacher;
import com.xin.eduservice.service.EduCourseService;
import com.xin.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xin
 * @since 2023/1/15 9:56
 */

@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;

    //1 分页查询讲师的方法
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        teacherService.getTeacherFrontList(teacherPage);


        return R.ok();
    }

}
