package com.fiberhome.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

/**
 * function:封装Quartz2.2 动态添加、修改和删除定时任务时间的方法
 * @author WH1707008
 * @date 2018/6/28 19:17
 */
public class QuartzManager2 {


    private static Scheduler scheduler ;

    public static void withSchedule(Scheduler scheduler){
        QuartzManager2.scheduler=scheduler;
    }




    /**
     * 功能： 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName  任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass  任务的类类型  eg:TimedMassJob.class
     * @param cron   时间设置 表达式，参考quartz说明文档
     * @param objects  可变参数需要进行传参的值
     */
    public static void addJob(Scheduler scheduler,String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron,Object...objects) {
        try {

            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            if(objects!=null){
                for (int i = 0; i < objects.length; i++) {
                    jobDetail.getJobDataMap().put("data"+(i+1), objects[i]);
                }
            }
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName,triggerGroupName);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            if(!scheduler.checkExists(JobKey.jobKey(jobName, jobGroupName))){
                scheduler.scheduleJob(jobDetail, trigger);
            }else{
                System.out.println("创建失败，该任务已创建！");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void addSchedulJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron,Object...objects) {
        try {

            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 触发器
            if(objects!=null){
                for (int i = 0; i < objects.length; i++) {
                    //该数据可以通过Job中的JobDataMap dataMap = context.getJobDetail().getJobDataMap();来进行参数传递值
                    jobDetail.getJobDataMap().put("data"+(i+1), objects[i]);
                }
            }
            CronTrigger trigger=TriggerBuilder.newTrigger().withIdentity(triggerName,triggerGroupName)
                          .startNow().withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();

            if(!scheduler.checkExists(JobKey.jobKey(jobName, jobGroupName))){
                // 调度容器设置JobDetail和Trigger
                scheduler.scheduleJob(jobDetail, trigger);
            }else{
                System.out.println("创建失败，该任务已创建！");
            }

            // 启动
      /*      if (!scheduler.isShutdown()) {
                scheduler.start();
            }*/
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
