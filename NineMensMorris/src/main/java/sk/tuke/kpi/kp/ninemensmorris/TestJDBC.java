package sk.tuke.kpi.kp.ninemensmorris;

import sk.tuke.kpi.kp.ninemensmorris.entity.Score;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentServiceJDBC;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingService;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingServiceJDBC;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreService;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC
{
    public static void main(String[] args) throws Exception
    {
//        try (var conection = DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio", "postgres", "*rHDtpp5");
//            var statement = conection.createStatement();
//            var rs = statement.executeQuery("SELECT player, game, points, played_at FROM score WHERE game = 'nine mens morris' ORDER BY points DESC LIMIT 10");
//        )
//        {
//            statement.executeUpdate("INSERT INTO score (player, game, points, played_at) VALUES ('slavka', 'nine mens morris', '130', '2023-03-20 21:00')");
//        }

        ScoreService service = new ScoreServiceJDBC();
        CommentService commentService = new CommentServiceJDBC();
        RatingService ratingService = new RatingServiceJDBC();
        service.reset();
        commentService.reset();
        ratingService.reset();
        service.addScore(new Score("slavka", "nine mens morris", 110, new Date()));
        service.addScore(new Score("stano", "nine mens morris", 120, new Date()));
//        service.addScore(new Score("denis", "nine mens morris", 100, new Date()));
//        service.addScore(new Score("david", "nine mens morris", 140, new Date()));
//        service.addScore(new Score("majo", "nine mens morris", 105, new Date()));

//        var scores = service.getTopScores("nine mens morris");
//        System.out.println(scores);
    }
}

