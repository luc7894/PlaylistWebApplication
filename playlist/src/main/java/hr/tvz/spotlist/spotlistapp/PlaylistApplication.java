package hr.tvz.spotlist.spotlistapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PlaylistApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaylistApplication.class, args);
    }

   /* @Bean
    public CommandLineRunner printPassword(PasswordEncoder encoder) {
        return args -> {
            System.out.println("admin123 hash: " + encoder.encode("admin123"));
            System.out.println("user123 hash: " + encoder.encode("user123"));
            System.out.println("guest hash: " + encoder.encode("guest"));
        };
    }*/

}
