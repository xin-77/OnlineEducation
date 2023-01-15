package com.xin.educms.controller;

import com.xin.commonutils.R;
import com.xin.educms.entity.CrmBanner;
import com.xin.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xin
 * @since 2023/1/12 15:43
 */
@RestController
@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}
