package sk.tuke.kpi.kp.ninemensmorris.service.comment;

import sk.tuke.kpi.kp.ninemensmorris.entity.Comment;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService
{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "*rHDtpp5";
    public static final String INSERT_STATEMENT = "INSERT INTO comment (player, game, comment, commentedOn) VALUES (?, ?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player, game, comment, commentedOn FROM comment WHERE game = ?";
    public static final String DELETE_STATEMENT = "DELETE FROM comment";
    @Override
    public void addComment(Comment comment)
    {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(INSERT_STATEMENT);
        ){
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e)
        {
            throw new CommentException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game)
    {
        try ( var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                var statement = connection.prepareStatement(SELECT_STATEMENT);
        ){
            statement.setString(1, game);
            try (var resultSet = statement.executeQuery())
            {
                var comment = new ArrayList<Comment>();
                while (resultSet.next())
                {
                    comment.add(new Comment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getTimestamp(4)));
                }
                return comment;
            }
        }
        catch (SQLException e)
        {
            throw new CommentException(e);
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
            throw new CommentException(e);
        }

    }
}
