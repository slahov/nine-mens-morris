package sk.tuke.kpi.kp.ninemensmorris.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.kpi.kp.ninemensmorris.entity.Comment;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest
{
    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game)
    {
        return commentService.getComments(game);
    }
    @PostMapping
    public void addComment(@RequestBody Comment comment)
    {
        commentService.addComment(comment);
    }
}
