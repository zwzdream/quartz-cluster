package com.fiberhome.contoller;


import com.fiberhome.job.TimeMassJob;
import com.fiberhome.utils.QuartzManager;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanzhao zhang
 * @date 2018/12/19 11:32
 * Description:
 */
@RestController
@RequestMapping("quartz")
public class QuartzController {

    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    @GetMapping("add")
    public String  addQuartz(String job_name){
        //String job_name = "job1";
        QuartzManager.withScheduler(scheduler);
        QuartzManager.addJob(job_name, job_name, job_name, job_name, TimeMassJob.class, "0/10 * * * * ?", new Object[]{});
        return "请观察控制台！";
    }

 /*   @GetMapping("start")
    public String startQuartz(){
        QuartzManager.startJobs();
        return "已重新启动所有定时任务，请观察控制台";
    }*/

  /*  @GetMapping("stop")
    public String stopQuartz(){
        QuartzManager.shutdownJobs();
        return "已停止所有定时任务，请观察控制台";
    }*/



}
