package sk.tuke.kpi.kp.ninemensmorris;


import sk.tuke.kpi.kp.ninemensmorris.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.ninemensmorris.core.Board;

public class Main
{
    public static void main(String[] args)
    {
        Board board = new Board();
        ConsoleUI consoleUI = new ConsoleUI(board);
        consoleUI.menu();

    }
}

