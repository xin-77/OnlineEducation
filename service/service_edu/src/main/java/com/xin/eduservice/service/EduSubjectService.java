package com.xin.eduservice.service;

import com.xin.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xin.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xin
 * @since 2023-01-05
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
