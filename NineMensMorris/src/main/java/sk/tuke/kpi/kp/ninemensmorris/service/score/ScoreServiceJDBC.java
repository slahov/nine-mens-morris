package sk.tuke.kpi.kp.ninemensmorris.service.score;

import sk.tuke.kpi.kp.ninemensmorris.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService
{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "*rHDtpp5";
    public static final String INSERT_STATEMENT = "INSERT INTO score (player, game, points, played_at) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, points, played_at FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";
    public static final String DELETE_STATEMENT = "DELETE FROM score";

    @Override
    public void addScore(Score score)
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(INSERT_STATEMENT);
        ) {
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedAt().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException(e);
        }

    }

    @Override
    public List<Score> getTopScores(String game)
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(SELECT_STATEMENT);

        )
        {
            statement.setString(1, game);
            try (var resultSet = statement.executeQuery())
            {
                var scores = new ArrayList<Score>();
                while (resultSet.next())
                {
                    scores.add(new Score(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getTimestamp(4)));
                }
                return scores;
            }
        }
        catch (SQLException e)
        {
            throw new ScoreException(e);
        }
    }

    @Override
    public void reset()
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.createStatement();

        )
        {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e)
        {
            throw new ScoreException(e);
        }
    }
}
