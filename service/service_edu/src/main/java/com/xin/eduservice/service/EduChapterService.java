package com.xin.eduservice.service;

import com.xin.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xin.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xin
 * @since 2023-01-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoBtCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
