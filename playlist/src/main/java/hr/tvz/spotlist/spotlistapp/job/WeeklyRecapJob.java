package hr.tvz.spotlist.spotlistapp.job;

import hr.tvz.spotlist.spotlistapp.service.ReviewService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeeklyRecapJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(WeeklyRecapJob.class);

    @Autowired
    private ReviewService reviewService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("=== Weekly Recap Job pokrenut ===");

        reviewService.getTopPlaylistsByRating(3)
                .forEach(playlist ->
                        log.info("Top playlist: {} | Prosječna ocjena: {}",
                                playlist.getPlaylistTitle(),
                                playlist.getRating())
                );

        log.info("=== Weekly Recap Job završen ===");
    }
}