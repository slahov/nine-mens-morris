package sk.tuke.kpi.kp.ninemensmorris.service.score;

import sk.tuke.kpi.kp.ninemensmorris.entity.Score;

import java.util.List;

public interface ScoreService
{
    void addScore(Score score);
    List<Score> getTopScores(String game);
    void reset();
}
