package sk.tuke.kpi.kp.ninemensmorris;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.kpi.kp.ninemensmorris.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.ninemensmorris.core.Board;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentServiceJPA;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingService;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingServiceJPA;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreService;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreServiceRestClient;
import sk.tuke.kpi.kp.ninemensmorris.service.user.UserService;
import sk.tuke.kpi.kp.ninemensmorris.service.user.UserServiceJPA;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
    pattern = "sk.tuke.kpi.kp.ninemensmorris.server.*"))
public class SpringClient
{
    public static void main(String[] args)
    {
//        SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI)
    {
        return s -> consoleUI.menu();
    }

    @Bean
    public ConsoleUI consoleUI(Board board)
    {
        return new ConsoleUI(board);
    }

    @Bean
    public Board board()
    {
        return new Board();
    }
    @Bean
    public ScoreService scoreService()
    {
        return new ScoreServiceRestClient();
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
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
