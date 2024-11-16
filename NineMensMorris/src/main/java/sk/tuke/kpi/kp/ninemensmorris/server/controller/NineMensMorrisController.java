package sk.tuke.kpi.kp.ninemensmorris.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.kpi.kp.ninemensmorris.core.Board;
import sk.tuke.kpi.kp.ninemensmorris.core.GameState;
import sk.tuke.kpi.kp.ninemensmorris.core.Node;
import sk.tuke.kpi.kp.ninemensmorris.core.NodeState;
import sk.tuke.kpi.kp.ninemensmorris.entity.Comment;
import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;
import sk.tuke.kpi.kp.ninemensmorris.entity.Score;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingService;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreService;

import java.util.Date;


@Controller
@RequestMapping("/ninemensmorris")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NineMensMorrisController
{
    private Board board = new Board();
    @Autowired
    private UserController userController;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    @GetMapping
    public String ninemensmorris(@RequestParam int row, @RequestParam int column) {
        board.actionPiece(row, column);
        if (board.getGameState() != GameState.PLAYING && userController.isLogged())
        {
            scoreService.addScore(new Score(userController.getLoggedUser(), "ninemensmorris", board.getFinalScore(), new Date()));
        }
        return "ninemensmorris";
    }

    @GetMapping("/addComment")
    public String addComment(@RequestParam String commentInput)
    {
        if (userController.isLogged())
        {
            commentService.addComment(new Comment(userController.getLoggedUser(), "ninemensmorris", commentInput, new Date()));
        }
        return "ninemensmorris";
    }

    @GetMapping("/addRating")
    public String addRating(@RequestParam int ratingButtons)
    {
        if (userController.isLogged())
        {
            ratingService.setRating(new Rating(userController.getLoggedUser(), "ninemensmorris", ratingButtons, new Date()));
        }
        return "redirect:/ninemensmorris/new";
    }

    @GetMapping("/new")
    public String newGame()
    {
        board = new Board();
        return "ninemensmorris";
    }

    public String getHtmlBoard()
    {
        var sb = new StringBuilder();
        sb.append("<table class='morrisboard'>\n");
        for (var row = 0; row < board.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (var column = 0; column < board.getColumnCount(); column++) {
                var node = board.getNode(row, column);
                sb.append("<td class='" + getNodeClass(node) + "'>\n");
                if (render(node))
                    sb.append("<a href='/ninemensmorris?row=" + row + "&column=" + column + "'>\n");
//                sb.append("<img src='/images/ninemensmorris/" + getNodeClass(node) + ".png'>");
//                sb.append("<span>" + getNodeText(node) + "</span>\n");
                if (render(node))
                    sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    private boolean render(Node node)
    {
        if (board.getGameState() != GameState.PLAYING)
        {
            return false;
        }
        if(board.isRemoving())
        {
            if (board.getActualPlayer() == NodeState.WHITE && node.getState() == NodeState.BLACK)
            {
                return true;
            }
            else if (board.getActualPlayer() == NodeState.BLACK && node.getState() == NodeState.WHITE)
            {
                return  true;
            }
            return false;
        }
        if (board.getRound() >= 1 && board.getRound() <= 9)
        {
            return node.getState() == NodeState.EMPTY;
        }
        else
        {
            if (board.getActualPlayer() == NodeState.WHITE && board.getSelectedPiece() == null)
            {
                return node.getState() == NodeState.WHITE;
            }
            if (board.getActualPlayer() == NodeState.BLACK && board.getSelectedPiece() == null)
            {
                return node.getState() == NodeState.BLACK;
            }
            if (board.getActualPlayer() == NodeState.WHITE && board.getSelectedPiece() != null)
            {
                return node.getState() == NodeState.EMPTY || node.getState() == NodeState.WHITE;
            }
            if (board.getActualPlayer() == NodeState.BLACK && board.getSelectedPiece() != null)
            {
                return node.getState() == NodeState.EMPTY || node.getState() == NodeState.BLACK;
            }
        }
        return false;
    }

    private String getNodeClass(Node node) {
        return switch (node.getState())
                {
                    case EMPTY -> "empty-" + node.getNodePosition() + (render(node) ? "-hover" : "");
                    case WHITE -> "white-" + node.getNodePosition() + (node.isSelected() ? "-selected" : "");
                    case BLACK -> "black-" + node.getNodePosition() + (node.isSelected() ? "-selected" : "");
                    case HORIZONTAL_JOINT -> "horizontal";
                    case VERTICAL_JOINT -> "vertical";
                    case NONE_JOINT -> "none";
                    default -> throw new IllegalArgumentException("Not valid node state" + node.getState());
                };
    }

    private String getNodeText(Node node) {
        return switch (node.getState())
                {
                    case EMPTY -> "E";
                    case WHITE -> "W";
                    case BLACK -> "B";
                    case HORIZONTAL_JOINT -> "-";
                    case VERTICAL_JOINT -> "|";
                    case NONE_JOINT -> " ";
                    default -> throw new IllegalArgumentException("Not valid node state" + node.getState());
                };
    }

    public String playerOnTurn()
    {
        return board.getActualPlayer().toString();
    }

    public GameState getState()
    {
        return board.getGameState();
    }

}
