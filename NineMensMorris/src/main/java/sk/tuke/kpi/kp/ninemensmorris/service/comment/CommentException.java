package sk.tuke.kpi.kp.ninemensmorris.service.comment;

public class CommentException extends RuntimeException
{
    public CommentException()
    {

    }
    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentException(Throwable cause)
    {
        super(cause);
    }
}
