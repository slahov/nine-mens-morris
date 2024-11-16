package sk.tuke.kpi.kp.ninemensmorris.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.kpi.kp.ninemensmorris.core.*;
import sk.tuke.kpi.kp.ninemensmorris.entity.Comment;
import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;
import sk.tuke.kpi.kp.ninemensmorris.entity.Score;
import sk.tuke.kpi.kp.ninemensmorris.service.comment.CommentService;
import sk.tuke.kpi.kp.ninemensmorris.service.rating.RatingService;
import sk.tuke.kpi.kp.ninemensmorris.service.score.ScoreService;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI
{
    private static final Pattern REGEX_EXPRESSION = Pattern.compile("([A-G])([1-7])");
    private final Board board;
    private char printBoard[][];
    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    private int round = 9;
    private int row, column;
    private NodeState player;
    public static final String ANSI_BLACK = "\033[1;30m";
    public static final String ANSI_WHITE = "\033[1;38;5;255;255;255m";
    public static final String ANSI_GREY = "\033[0;37m";
    public static final String ANSI_RED = "\033[0;31m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static final String ANSI_CYAN = "\033[0;36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String BROWN_BACKGROUND = "\033[48;2;115;75;30m";
    public static final String BLACK_BACKGROUND = "\033[40m";

    public ConsoleUI(Board board)
    {
        this.board = board;
    }

    public void play()
    {
//        board.createBoard();
        player = NodeState.WHITE;
        board.setPiece(1,3, NodeState.WHITE);
        board.setPiece(2,2, NodeState.WHITE);
        board.setPiece(3,5, NodeState.WHITE);
        board.setPiece(4,4, NodeState.WHITE);
        board.setPiece(3,1, NodeState.WHITE);
        board.setPiece(6,3, NodeState.WHITE);
        board.setPiece(6,6, NodeState.WHITE);
        board.setPiece(0,6, NodeState.WHITE);
        board.setPiece(2,3, NodeState.BLACK);
        board.setPiece(2,4, NodeState.BLACK);
        board.setPiece(3,4, NodeState.BLACK);
        board.setPiece(4,3, NodeState.BLACK);
        board.setPiece(4,2, NodeState.BLACK);
        board.setPiece(3,2, NodeState.BLACK);
        board.setPiece(1,1, NodeState.BLACK);
        board.setPiece(1,5, NodeState.BLACK);
        do
        {
            printBoard();
            processInput();
        } while (board.getGameState() == GameState.PLAYING);

        if (board.getGameState() == GameState.PLAYER1WON)
        {
            System.out.println("P1 WON!");
        } else if (board.getGameState() == GameState.PLAYER2WON)
        {
            System.out.println("P2 WON!");
        }
        scoreService.addScore(new Score(System.getProperty("user.name"), "nine mens morris", getFinalScore(), new Date()));

    }

    public void menu()
    {
//        scoreService.addScore(new Score(System.getProperty("user.name"), "nine mens morris", 80, new Date()));
//        scoreService.addScore(new Score(System.getProperty("user.name"), "nine mens morris", 96, new Date()));
//        commentService.addComment(new Comment(System.getProperty("user.name") + ": ", "nine mens morris", "blablabla", new Date()));
//        ratingService.setRating(new Rating(System.getProperty("user.name"), "nine mens morris", 3, new Date()));
//        ratingService.setRating(new Rating(System.getProperty("user.name"), "nine mens morris", 1, new Date()));
//        scoreService.reset();
//        commentService.reset();
//        ratingService.reset();
        boolean running = true;
        while (running)
        {
            System.out.println();
            System.out.println(BLACK_BACKGROUND + "                                                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                                                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                                                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                " + ANSI_RED + BLACK_BACKGROUND + "WELCOME TO THE GAME!" + ANSI_RESET + BLACK_BACKGROUND + "                 " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                                                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                     1. Play                         " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                 2. Leaderboard                      " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                3. Write comment                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                 4. Leave rating                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "              5. Show comment history                " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "              6. Show average rating                 " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                   7. Exit menu                      " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                                                     " + ANSI_RESET);
            System.out.println(BLACK_BACKGROUND + "                                                     " + ANSI_RESET);
            System.out.println(ANSI_BLUE + BLACK_BACKGROUND + "Select your option:                                  " + ANSI_RESET);
            var input = scanner.nextLine();
            switch (input)
            {
                case "1" -> play();
                case "2" -> printTopScores();
                case "3" ->
                {
                    input = scanner.nextLine();
                    commentService.addComment(new Comment(System.getProperty("user.name") + ": ", "nine mens morris", input, new Date()));
                }
                case "4" ->
                {
                    var inputInt = scanner.nextInt();
                    ratingService.setRating(new Rating(System.getProperty("user.name"), "nine mens morris", inputInt, new Date()));
                }
                case "5" -> printCommentHistory();
                case "6" -> System.out.println(ratingService.getAverageRating("nine mens morris"));
                case "7" -> running = false;
            }
            if (board.getGameState() == GameState.PLAYER1WON || board.getGameState() == GameState.PLAYER2WON)
            {
                running = false;
            }
        }

    }

    private void processInput()
    {
        System.out.println(ANSI_CYAN + "Player " + player + " enter input: " + ANSI_RESET);
        System.out.println(ANSI_GREY + "**Hint: Enter row (A - G) and column (1 - 7)**" + ANSI_RESET);
        if (round > 9)
        {
            System.out.println(ANSI_GREY + "Select piece which you want to move and hit ENTER." + ANSI_RESET);
        }
        scanInput();
        if (round > 9)
        {
            System.out.println(ANSI_GREY + "Enter coordinates where you want piece to be moved." + ANSI_RESET);
        }
        updateBoard();
    }

    private void updateBoard()
    {
        if (round >= 1 && round <= 9) // ak round 1-9, tak setPiece(row, column, player)
        {
            while (!board.setPiece(row, column, player)) // dokial hrac nenapise spravny input, stale sa pytam na input
            {
                System.out.println(ANSI_RED + "Can't set your piece here. Enter another!" + ANSI_RESET);
                scanInput();
            }
            // ak isMill == true, tak opytaj sa na odstranenie superovej figurky
            if (board.isMill(board.getNode(row, column)))
            {
                removePieceInput();
            }
        } else if (round > 9) // ak round > 9, tak selectPiece(row, column, player)
        {
            if (!board.isMovePossible(player))
            {
                return;
            }
            while (!board.selectPiece(row, column, player))
            {
                System.out.println(ANSI_RED + "Can't select piece. Enter another!" + ANSI_RESET);
                scanInput();
            }
            // ak selectPiece == true, tak movePiece
            Node selectedPiece = board.getNode(row, column);
            scanInput();
            if (player == NodeState.WHITE && board.getWhitePlayerNumberOfPieces() > 3 ||
                    player == NodeState.BLACK && board.getBlackPlayerNumberOfPieces() > 3)
            {
                while (!board.movePiece(row, column, player))
                {
                    System.out.println(ANSI_RED + "Can't move piece here. Try to move it to another node." + ANSI_RESET);
                    scanInput();
                }
            } else if (player == NodeState.WHITE && board.getWhitePlayerNumberOfPieces() == 3 ||
                    player == NodeState.BLACK && board.getBlackPlayerNumberOfPieces() == 3)
            {
                System.out.println(player);
                while (!board.jumpPiece(row, column, player))
                {
                    System.out.println(ANSI_RED + "Can't move piece here. Try to move it to another node." + ANSI_RESET);
                    scanInput();
                }
            }
            if (board.isMill(board.getNode(row, column)))
            {
                removePieceInput();
            }
        }
        // ternary operator (condition) ? (return if true) : (return if false);
        player = player == NodeState.WHITE ? NodeState.BLACK : NodeState.WHITE; // zmena ktory hrac je na rade
        if (player == NodeState.WHITE) // ak je na rade biely tak sa zacina nove kolo
        {
            round++;
        }
//        System.out.println("round = " + round);
    }

    private void removePieceInput()
    {
        //while (/*false kym sa nevyberie pole so superovou figurkou */) System.out.println();
        while (board.getNode(row, column).getState() == NodeState.EMPTY ||
                board.getNode(row, column).getState() == player ||
                board.getNode(row, column) == null)
        {
            printBoard();
            System.out.println("Which opponent's piece do you want to remove?");
            scanInput();
        }
        while (!board.removePiece(row, column)) // dokial hrac nezada spravny input, bude vyzvany na opatovne zadanie suradnic
        {
            System.out.println("Can't remove piece. Try to remove piece from another node.");
            scanInput();
        }
        board.decrementPlayerPieces(player);
    }

    private void scanInput()
    {
        var line = scanner.nextLine().toUpperCase(Locale.ROOT); // scanner
        var matcher = REGEX_EXPRESSION.matcher(line);// matcher
        if (matcher.matches())
        {
            row = matcher.group(1).charAt(0) - 'A';
            column = Integer.parseInt(matcher.group(2)) - 1;
        } else
        {
            System.out.println("Wrong input!"); // ak zly input, tak print "wrong input"
        }
    }

    public void printBoard()
    {
        int columnCount = board.getColumnCount();
        int rowCount = board.getRowCount();

        for (int column = 0; column < columnCount; column++)
        {
            System.out.print("  ");
            System.out.print(column + 1);
        }

        System.out.println();

        for (int row = 0; row < rowCount; row++)
        {
            System.out.print((char) ('A' + row));
            for (int column = 0; column < columnCount; column++)
            {
                String state = BROWN_BACKGROUND + "-" + ANSI_RESET;
                Node node = board.getNode(row, column);

                if (node == null)
                {
                    System.out.print(BROWN_BACKGROUND + " " + ANSI_RESET + state + BROWN_BACKGROUND + " " + ANSI_RESET);
                    continue;
                }

                NodeState nodeState = node.getState();
                if (nodeState == NodeState.EMPTY) state = ANSI_GREY + BROWN_BACKGROUND + "E" + ANSI_RESET;
                else if (nodeState == NodeState.BLACK) state = ANSI_BLACK + BROWN_BACKGROUND + "B" + ANSI_RESET;
                else if (nodeState == NodeState.WHITE) state = ANSI_WHITE + BROWN_BACKGROUND + "W" + ANSI_RESET;
                System.out.print(BROWN_BACKGROUND + " " + ANSI_RESET + state + BROWN_BACKGROUND + " " + ANSI_RESET);
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getFinalScore()
    {
        return Math.max(100 - round, 0);
    }

    private void printTopScores()
    {
        var scores = scoreService.getTopScores("nine mens morris");
        System.out.println("                ***LEADERBOARD***");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < scores.size(); i++)
        {
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i + 1, score.getPlayer(), score.getPoints());
        }
        System.out.println("----------------------------------------------------");
    }

    private void printCommentHistory()
    {
        var comments = commentService.getComments("nine mens morris");
        System.out.println("################################################");
        for (int i = 0; i < comments.size(); i++)
        {
            var comment = comments.get(i);
            System.out.printf("%d. %s %s\n", i + 1, comment.getPlayer(), comment.getComment());
        }
        System.out.println("###################################################");
    }
}

