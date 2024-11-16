package sk.tuke.kpi.kp.ninemensmorris.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ninemensmorris.entity.Comment;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
public class CommentServiceTest
{
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset()
    {
        commentService.reset();
        assertEquals(0, commentService.getComments("nine mens morris").size());
    }

    @Test
    public void testAddComment()
    {
        commentService.reset();
        var date = new Date();

        commentService.addComment(new Comment("slavka", "nine mens morris", "hahahah srandy", date));
        commentService.addComment(new Comment("denis", "mines", "milujem goth chicks", date));
        commentService.addComment(new Comment("david", "nine mens morris", "fajnu polievocku si dam teraz", date));
        commentService.addComment(new Comment("majo", "nine mens morris", "vyhrate! teraz idem poslat do skupiny 1 sekundove nekvalitne video", date));

        var comments = commentService.getComments("nine mens morris");
        assertEquals(3, comments.size());

        assertEquals("slavka", comments.get(0).getPlayer());
        assertEquals("nine mens morris", comments.get(0).getGame());
        assertEquals("hahahah srandy", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("david", comments.get(1).getPlayer());
        assertEquals("nine mens morris", comments.get(1).getGame());
        assertEquals("fajnu polievocku si dam teraz", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("majo", comments.get(2).getPlayer());
        assertEquals("nine mens morris", comments.get(2).getGame());
        assertEquals("vyhrate! teraz idem poslat do skupiny 1 sekundove nekvalitne video", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }

    @Test
    public void testGetComments()
    {
        commentService.reset();
        var date = new Date();

        commentService.addComment(new Comment("slavka", "nine mens morris", "hahahah srandy", date));
        commentService.addComment(new Comment("denis", "mines", "milujem goth chicks", date));
        commentService.addComment(new Comment("david", "nine mens morris", "fajnu polievocku si dam teraz", date));
        commentService.addComment(new Comment("majo", "nine mens morris", "vyhrate! teraz idem poslat do skupiny 1 sekundove nekvalitne video", date));

        var comments = commentService.getComments("nine mens morris");
        assertEquals(3, comments.size());

        assertEquals("slavka", comments.get(0).getPlayer());
        assertEquals("nine mens morris", comments.get(0).getGame());
        assertEquals("hahahah srandy", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("david", comments.get(1).getPlayer());
        assertEquals("nine mens morris", comments.get(1).getGame());
        assertEquals("fajnu polievocku si dam teraz", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("majo", comments.get(2).getPlayer());
        assertEquals("nine mens morris", comments.get(2).getGame());
        assertEquals("vyhrate! teraz idem poslat do skupiny 1 sekundove nekvalitne video", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }
}


