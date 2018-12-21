package com.fiberhome.run;

import com.fiberhome.utils.QuartzManager;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author wanzhao zhang
 * @date 2018/12/19 11:32
 * Description:
 * 在项目启动后，马上调用QuartzManager.withSchedule，将Spring管理的Schedule Bean
 *  绑定到QuartzManager工具类.
 */

@Component
public class BindScheduleBean implements ApplicationRunner {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
            try {
                QuartzManager.withSchedule(scheduler);
            }catch (Exception e){
                e.printStackTrace();
            }

    }


}
