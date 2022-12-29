package com.xin.eduservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xin.commonutils.R;
import com.xin.eduservice.entity.EduTeacher;
import com.xin.eduservice.entity.vo.TeacherQuery;
import com.xin.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-12-29
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    //1 查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);

        // 模拟异常
        int i = 10 / 0;

        return R.ok().data("items",list);

    }

    //2 逻辑删除讲师的方法
    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable String id){

        return eduTeacherService.removeById(id) ? R.ok() : R.error();
    }

    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> page = new Page<EduTeacher>(current, limit);


        eduTeacherService.page(page, null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        return R.ok().data("total", total).data("records", records);
    }

    //4 条件查询带分页的方法
    @ApiOperation(value = "分页多条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        // 创建page对象
        Page<EduTeacher> page = new Page<>(current, limit);
        // 构造条件
        LambdaQueryWrapper<EduTeacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(teacherQuery.getName()), EduTeacher::getName, teacherQuery.getName());
        wrapper.eq(!StringUtils.isEmpty(teacherQuery.getLevel()), EduTeacher::getLevel, teacherQuery.getLevel());
        wrapper.ge(!StringUtils.isEmpty(teacherQuery.getEnd()), EduTeacher::getGmtCreate, teacherQuery.getEnd());


        // 调用方法实现条件分页查询
        eduTeacherService.page(page, wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total", total).data("records", records);


    }

    //添加讲师接口的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);

        return save ? R.ok() : R.error();
    }

    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);

        return b ? R.ok() : R.error();
    }
}

