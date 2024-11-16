package sk.tuke.kpi.kp.ninemensmorris.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentServiceJPA;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingService;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingServiceJPA;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreService;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreServiceJPA;
import sk.tuke.kpi.kp.ninemensmorris.service.user.UserService;
import sk.tuke.kpi.kp.ninemensmorris.service.user.UserServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan(basePackages = "sk.tuke.kpi.kp.ninemensmorris.entity")
public class GameStudioServer
{
    public static void main(String[] args)
    {
        SpringApplication.run(GameStudioServer.class);
    }

    @Bean
    public ScoreService scoreService()
    {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService()
    {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService()
    {
        return new RatingServiceJPA();
    }
    @Bean
    public UserService userService()
    {
        return new UserServiceJPA();
    }
}