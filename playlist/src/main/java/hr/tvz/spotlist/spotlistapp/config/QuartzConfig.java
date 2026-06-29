package hr.tvz.spotlist.spotlistapp.config;

import hr.tvz.spotlist.spotlistapp.job.SongRecapJob;
import hr.tvz.spotlist.spotlistapp.job.WeeklyRecapJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail weeklyRecapJobDetail() {
        return JobBuilder.newJob(WeeklyRecapJob.class) //klasa koja pita koju klasu trea pokrenuti
                .withIdentity("weeklyRecapJob") //ime posla
                .storeDurably() //ako nema trigger da zadrzi posao
                .build();
    }

    @Bean
    public Trigger weeklyRecapTrigger(JobDetail weeklyRecapJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(weeklyRecapJobDetail)
                .withIdentity("weeklyRecapTrigger")

                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build();
    }
    //na labosu
    @Bean
    public JobDetail songRecapJobDetail() {
        return JobBuilder.newJob(SongRecapJob.class)
                .withIdentity("songRecapJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger songRecapTrigger(JobDetail songRecapJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(songRecapJobDetail)
                .withIdentity("songRecapTrigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("0/10 * 15-21 ? * MON-FRI"))
                .build();
    }
}