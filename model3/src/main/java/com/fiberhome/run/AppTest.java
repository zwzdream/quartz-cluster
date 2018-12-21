package com.fiberhome.run;

import com.fiberhome.utils.QuartzManager2;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppTest implements ApplicationRunner {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
            try {
                System.out.println("项目启动后立即执行这个方法");
                QuartzManager2.withSchedule(scheduler);
                //String job_name = "job1";
                //System.out.println("【系统启动】开始(每1秒输出一次)...");
                //QuartzManager.startJobs();
                //QuartzManager.addJob(job_name, job_name, job_name, job_name, TimeMassJob.class, "0/10 * * * * ?", new Object[]{});
                /* Thread.sleep(2000);
                System.out.println("【修改时间】开始(每2秒输出一次)...");
                QuartzManager.modifyJobTime(job_name, job_name, job_name, job_name, "10/2 * * * * ?");
                Thread.sleep(6000);
                System.out.println("【移除定时】开始...");
                QuartzManager.removeJob(job_name, job_name, job_name, job_name);
                System.out.println("【移除定时】成功");*/

            }catch (Exception e){
                e.printStackTrace();
            }

    }


}
