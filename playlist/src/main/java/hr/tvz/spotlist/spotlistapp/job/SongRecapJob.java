package hr.tvz.spotlist.spotlistapp.job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import hr.tvz.spotlist.spotlistapp.dto.SongDTO;
import hr.tvz.spotlist.spotlistapp.service.SongService;


import java.time.LocalDate;
import java.util.List;


@Component
public class SongRecapJob implements Job {

    @Autowired
    private SongService songService;

    private static final Logger log = LoggerFactory.getLogger(SongRecapJob.class);

    @Override
    public void execute(JobExecutionContext context) {
        if (LocalDate.now().getYear() != 2026) return;

        log.info(" Song Recap Job: zadnjih 5 pjesama");
        songService.findLast5()
                .forEach(s -> log.info("  [{}] {} - {} | Album: {} ({}s)",
                        s.getId(), s.getName(), s.getArtist(), s.getAlbum(), s.getDurationSeconds()));
        log.info("Song Recap Job završen ");
    }
}