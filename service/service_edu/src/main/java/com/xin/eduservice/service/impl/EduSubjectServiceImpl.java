package com.xin.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xin.eduservice.entity.EduSubject;
import com.xin.eduservice.entity.excel.SubjectData;
import com.xin.eduservice.entity.subject.OneSubject;
import com.xin.eduservice.entity.subject.TwoSubject;
import com.xin.eduservice.listener.SubjectExcelListener;
import com.xin.eduservice.mapper.EduSubjectMapper;
import com.xin.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author xin
 * @since 2023-01-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 1 查询所有一级分类
        LambdaQueryWrapper<EduSubject> queryWrapperOne = new LambdaQueryWrapper<>();
        queryWrapperOne.eq(EduSubject::getParentId, "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapperOne);

        // 2查询所有二级分类
        LambdaQueryWrapper<EduSubject> queryWrapperTwo = new LambdaQueryWrapper<>();
        queryWrapperTwo.ne(EduSubject::getParentId, "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(queryWrapperTwo);

        // 创建list集合， 用户存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            // 得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);

            // 把eduSubject里面的值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject, oneSubject);
            // 多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<TwoSubject>();

            for (int j = 0; j < twoSubjectList.size(); j++) {
                // 获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(j);
                // 判断二级分类parentid和一级分类id是否一样
                if(tSubject.getParentId().equals(oneSubject.getId())){
                    // 把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }

            }

            // 把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }




        return finalSubjectList;
    }
}
