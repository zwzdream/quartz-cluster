package com.fiberhome.utils;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * function:封装Quartz2.2 动态添加、修改和删除定时任务时间的方法
 * @author WH1707008
 * @date 2018/6/28 19:17
 */
@Component
public class QuartzManager {


    private static Scheduler scheduler ;

    /**
     * 在这个类中使用@Autowired无法注入Scheduler，
     * 此方法用于绑定Schedule
     * @param scheduler
     */
    public static void withSchedule(Scheduler scheduler){
        QuartzManager.scheduler=scheduler;
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
    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron,Object...objects) {
        try {

            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            if(objects!=null){
                for (int i = 0; i < objects.length; i++) {
                    //该数据可以通过Job中的JobDataMap dataMap = context.getJobDetail().getJobDataMap();来进行参数传递值
                    jobDetail.getJobDataMap().put("data"+(i+1), objects[i]);
                }
            }
            // 触发器名,触发器组  触发器时间设定
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName,triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
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



    /**
     * 功能：修改一个任务的触发时间
     * @param jobName  任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron   时间设置，参考quartz说明文档
     */
    public static void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {

                TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                if (trigger == null) {
                    return;
                }
                String oldTime = trigger.getCronExpression();
                if (!oldTime.equalsIgnoreCase(cron)) {

                    // 触发器名,触发器组  触发器时间设定
                    trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
                            .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                            .build();

                    scheduler.rescheduleJob(triggerKey, trigger);

                }
            }else{
                System.out.println("任务不存在！");
            }
            } catch(Exception e){
                throw new RuntimeException(e);
            }

    }

    /**
     * 功能: 移除一个任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void removeJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName) {
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,triggerGroupName);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName,jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能: 暂停一个任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     */
    public static void pauseJob(String jobName, String jobGroupName){
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 功能: 恢复一个任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     */
    public static void resumeJob(String jobName, String jobGroupName){
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }




    /**
     *
     * 功能：启动所有定时任务
     */
    public static void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 功能：关闭所有定时任务，等所有任务完成后
     *调用该方法后，schedule被干掉了，一切方法调用都会被禁止
     * 需要重启服务恢复
     */
    public static void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
