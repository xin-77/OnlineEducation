package com.xin.eduservice.controller;


import com.xin.commonutils.R;
import com.xin.eduservice.entity.subject.OneSubject;
import com.xin.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xin
 * @since 2023-01-05
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Resource
    private EduSubjectService eduSubjectService;

    // 添加课程分类
    // 获取上传的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        // 上传过来excel文件

        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    // 课程分类列表（树型）
    @GetMapping("getAllSubject")
    public R getAllSubject(){

        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

