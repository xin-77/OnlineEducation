package com.xin.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xin.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xin
 * @since 2022-12-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
