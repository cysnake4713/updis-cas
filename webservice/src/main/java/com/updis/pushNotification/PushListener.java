package com.updis.pushNotification;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 4/30/13
 * Time: 7:04 下午
 */
public class PushListener implements ServletContextListener{

    private Logger logger = LoggerFactory.getLogger(PushListener.class);
    public static final int PULL_INTERVAL_MINUTE = 1;
    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        JobDataMap map = new JobDataMap();
        map.put("servletContext", servletContext);
        JobDetail pushJob = newJob(PushJob.class).withIdentity("job").usingJobData(map).build();
        Trigger trigger = newTrigger().withIdentity("trigger").startNow()
                            .withSchedule(simpleSchedule().withIntervalInMinutes(PushListener.PULL_INTERVAL_MINUTE).repeatForever())
                            .build();
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.scheduleJob(pushJob, trigger);
            scheduler.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (scheduler != null) {
            try {
                scheduler.shutdown();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}