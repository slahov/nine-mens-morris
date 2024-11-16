package sk.tuke.kpi.kp.ninemensmorris.service.rating;

import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RatingServiceJDBC implements RatingService
{
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "*rHDtpp5";
    public static final String INSERT_STATEMENT = "INSERT INTO rating (player, game, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, rating, ratedOn FROM rating WHERE player = ? AND game = ?";
    public static final String SELECT_AVERAGE_RATING = "SELECT AVG(rating) FROM rating WHERE game = ?";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";

    @Override
    public void setRating(Rating rating)
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(INSERT_STATEMENT)
        )
        {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e)
        {
            throw new RatingException(e);
        }
    }

    @Override
    public int getAverageRating(String game)
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement = connection.prepareStatement(SELECT_AVERAGE_RATING);
        )
        {
            statement.setString(1, game);
            try (var resultSet = statement.executeQuery())
            {
                resultSet.next();
                return resultSet.getInt(1);
            }
        } catch (SQLException e)
        {
            throw new RatingException(e);
        }
    }

    @Override
    public Rating getRating(String game, String player)
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(SELECT_STATEMENT);
        )
        {
            statement.setString(1, game);
            statement.setString(2, player);
            try (var resultSet = statement.executeQuery())
            {
                var ratings = new Rating();
                while (resultSet.next())
                {
                    ratings.setRating(new Rating(resultSet.getString(1), resultSet.getString(2),resultSet.getInt(3), resultSet.getTimestamp(4)).getRating());
                }
                return ratings;
            }
        }
        catch (SQLException e)
        {
            throw new RatingException(e);
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
            throw new RatingException(e);
        }
    }
}
