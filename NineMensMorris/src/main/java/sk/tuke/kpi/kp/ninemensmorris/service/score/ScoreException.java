package sk.tuke.kpi.kp.ninemensmorris.service.score;

public class ScoreException extends RuntimeException
{
    public ScoreException()
    {
    }

    public ScoreException(String message)
    {
        super(message);
    }

    public ScoreException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ScoreException(Throwable cause)
    {
        super(cause);
    }
}
