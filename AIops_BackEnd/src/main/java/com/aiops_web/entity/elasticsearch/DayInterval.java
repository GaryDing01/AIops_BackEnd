package com.aiops_web.entity.elasticsearch;

import com.aiops_web.entity.sql.DataIntroducing;
import com.aiops_web.service.DataIntroducingService;
import com.aiops_web.service.OriginalDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.List;

/**
 * @author DQL
 * @time 2023/4/28
 */
@Component
public class DayInterval implements ServletContextListener {
    @Autowired
    private OriginalDataService originalDataService;
    @Autowired
    private DataIntroducingService dataIntroducingService;

    // 每天晚上 23:30 触发
    @Scheduled(cron = "0 45 21 ? * *")
    public void scheduled() {
        // 获取保存在 OriginalData 中的 batchId
        QueryWrapper<DataIntroducing> queryWrapper = new QueryWrapper<>();
        String firstPlace = "OriginalData";
        queryWrapper.eq("place", firstPlace);
        List<DataIntroducing> dataList = dataIntroducingService.list(queryWrapper);
        System.out.println(dataList.size());

        if (dataList.size() > 0) {
            // 获取 7天前的日期
            Date last_7 = new Date(new Date().getTime() - (1000 * 60 * 60 * 24 * 7));
            System.out.println(last_7);
            for (DataIntroducing data : dataList) {
                // date1.compareTo(date2) > 0 : date1 时间在 date2 之后
                int result = data.getTstamp().compareTo(last_7);
                System.out.println(result);
                if (result < 0) {
                    int batchId = data.getBatchId();
                    String secondPlace = "OriginalDataLake";
                    originalDataService.TransferDataToLake(batchId);
                    data.setPlace(secondPlace);
                    dataIntroducingService.updateById(data);
                }
            }
            System.out.println("检查DataIntroducing，转移七天前的源数据。");
            System.out.println("批次共计：" + dataList.size());
        } else {
            System.out.println("日行检查，没有需要转移的源数据。");
        }
    }

}
