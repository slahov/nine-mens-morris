package sk.tuke.kpi.kp.ninemensmorris.service.user;

public class UserException extends RuntimeException
{
    public UserException()
    {

    }

    public UserException(String message, Throwable cause)
    {
        super(message,cause);
    }

    public UserException(Throwable cause)
    {
        super(cause);
    }
}
