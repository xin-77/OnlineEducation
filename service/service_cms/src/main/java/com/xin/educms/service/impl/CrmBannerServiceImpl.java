package com.xin.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xin.educms.entity.CrmBanner;
import com.xin.educms.mapper.CrmBannerMapper;
import com.xin.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2023-01-12
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public List<CrmBanner> selectAllBanner() {
        //根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接sql语句
        wrapper.last("limit 2");

        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
