package com.fiberhome.service;


import com.fiberhome.dao.IUserDao;
import com.fiberhome.job.TimeMassJob;
import com.fiberhome.pojo.User;
import com.fiberhome.utils.QuartzManager2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wanzhao zhang
 * @date 2018/12/18 13:49
 * Description:
 */
@Service("userService")
public class UserService {
    @Resource
    private IUserDao userDao;

    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    public User findById(int id){
        return userDao.findById(id);
    }


    /**
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param jobClass
     * @param cron
     * @param objects
     * @return
     * 主要用于在service层添加Job
     */
    public String addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron,Object...objects){
        try {
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 触发器
            if(objects!=null){
                for (int i = 0; i < objects.length; i++) {
                    //该数据可以通过Job中的JobDataMap dataMap = context.getJobDetail().getJobDataMap();来进行参数传递值
                    jobDetail.getJobDataMap().put("data"+(i+1), objects[i]);
                }
            }
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(triggerName,triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "add";
    }

    /**
     * 把业务层注入的Schedule bean传给QuartzManager2
     * @param job_name
     * @return
     */
    public String addJob2(String job_name){
        QuartzManager2.addJob(scheduler,job_name, job_name, job_name, job_name, TimeMassJob.class, "0/10 * * * * ?", new Object[]{});
         return "add2";
    }

}
