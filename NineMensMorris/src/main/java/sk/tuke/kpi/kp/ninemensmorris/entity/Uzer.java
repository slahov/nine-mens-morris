package sk.tuke.kpi.kp.ninemensmorris.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Uzer implements Serializable
{
    @Id
    private String username;
    private  String password;

    public Uzer(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public Uzer()
    {
        
    }

    public String getusername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
