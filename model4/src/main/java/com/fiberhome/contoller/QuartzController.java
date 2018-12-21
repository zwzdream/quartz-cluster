package com.fiberhome.contoller;



import com.fiberhome.job.TimeMassJob;
import com.fiberhome.utils.QuartzManager;
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
     * Function:添加任务
     * @param job_name
     * @return
     */
    @GetMapping("addJob")
    public String  addJob(String job_name){
        //String job_name = "job1";
        QuartzManager.addJob(job_name, job_name, job_name, job_name, TimeMassJob.class, "0/10 * * * * ?", new Object[]{});
        return "添加任务成功，请观察控制台！";
    }

    /**
     * Function:修改任务的触发事件
     * @param job_name
     * @return
     */
    @GetMapping("modifyJob")
    public String  modifyJobTime(String job_name){
        //String job_name = "job1";
        QuartzManager.modifyJobTime(job_name, job_name, job_name, job_name , "0/1 * * * * ?");
        return "修改任务的触发事件成功，请观察控制台！";
    }

    /**
     * Function:暂停正在执行的任务
     * @param job_name
     * @return
     */
    @GetMapping("pauseJob")
    public String  pauseJob(String job_name){
        QuartzManager.pauseJob(job_name, job_name);
        return "暂停任务成功，请观察控制台！";
    }

    /**
     * Function:恢复执行被暂停的任务
     * @param job_name
     * @return
     */
    @GetMapping("resumeJob")
    public String  resumeJob(String job_name){
        QuartzManager.resumeJob(job_name, job_name);
        return "恢复任务成功，请观察控制台！";
    }

    /**
     * Function:启动所有定时任务
     * @return
     */
    @GetMapping("startAll")
    public String startQuartz(){
        QuartzManager.startJobs();
        return "已重新启动所有定时任务，请观察控制台";
    }

    /**
     * Function:关闭所有定时任务
     * @return
     */
    @GetMapping("stopAll")
    public String stopQuartz(){
        QuartzManager.shutdownJobs();
        return "已停止所有定时任务，请观察控制台";
    }




}
