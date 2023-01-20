package com.xin.staservice.schedule;

import com.xin.staservice.service.StatisticsDailyService;
import com.xin.staservice.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xin
 * @since 2023/1/20 17:14
 */

@Component
public class ScheduledTask {

    @Resource
    private StatisticsDailyService staService;

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1(){
//        System.out.println("***************task1执行了。。");
//    }

    // 在每天凌晨1点，把前一天数据进行数据查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
