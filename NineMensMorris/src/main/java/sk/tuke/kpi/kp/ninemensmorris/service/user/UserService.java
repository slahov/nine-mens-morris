package sk.tuke.kpi.kp.ninemensmorris.service.user;

import sk.tuke.kpi.kp.ninemensmorris.entity.Uzer;

public interface UserService
{
    void addUser(Uzer uzer);
    boolean isValid(String username, String password);
    boolean validUsername(String username);
}
