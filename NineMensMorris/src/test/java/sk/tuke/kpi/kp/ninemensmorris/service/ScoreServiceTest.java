package sk.tuke.kpi.kp.ninemensmorris.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ninemensmorris.entity.Score;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreService;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest
{
    private ScoreService scoreService = new ScoreServiceJDBC();
    @Test
    public  void testReset()
    {
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("nine mens morris").size());
    }

    @Test
    public void testAddScores()
    {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("slavka", "nine mens morris", 120, date));
        scoreService.addScore(new Score("denis", "mines", 150, date));
        scoreService.addScore(new Score("david", "tiles", 180, date));
        scoreService.addScore(new Score("majo", "nine mens morris", 100, date));

        var scores = scoreService.getTopScores("nine mens morris");
        assertEquals(2, scores.size());

        assertEquals("slavka", scores.get(0).getPlayer());
        assertEquals("nine mens morris", scores.get(0).getGame());
        assertEquals(120, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("majo", scores.get(1).getPlayer());
        assertEquals("nine mens morris", scores.get(1).getGame());
        assertEquals(100, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedAt());
    }

    @Test
    public void testGetTopScores()
    {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("slavka", "nine mens morris", 120, date));
        scoreService.addScore(new Score("denis", "nine mens morris", 150, date));
        scoreService.addScore(new Score("david", "tiles", 180, date));
        scoreService.addScore(new Score("majo", "nine mens morris", 100, date));

        var scores = scoreService.getTopScores("nine mens morris");

        assertEquals(3, scores.size());

        assertEquals("nine mens morris", scores.get(0).getGame());
        assertEquals("denis", scores.get(0).getPlayer());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("nine mens morris", scores.get(1).getGame());
        assertEquals("slavka", scores.get(1).getPlayer());
        assertEquals(120, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedAt());

        assertEquals("nine mens morris", scores.get(2).getGame());
        assertEquals("majo", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedAt());

    }

}
