package sk.tuke.kpi.kp.ninemensmorris.core;

public class Board
{
    private final Node[][] nodes;
    private NodeSet[] nodeSets = new NodeSet[16];
    private GameState gameState = GameState.PLAYING;
    private final int rowCount;
    private final int columnCount;
    private NodeSet nodeSet;
    private Node selectedPiece;
    private int whitePlayerNumberOfPieces = 9;
    private int blackPlayerNumberOfPieces = 9;
    private int round = 1;
    private NodeState actualPlayer = NodeState.WHITE;
    private boolean removing = false;

    public Board()
    {
        this.rowCount = 7;
        this.columnCount = 7;
        nodes = new Node[rowCount][columnCount]; //mozno ho netreba
//        createNodeSets();
        createBoard();
    }

    public void createBoard()
    {
        createNodes();
        createNodeSets();
    }

    private void createNodes()
    {
        int i = 0;
        for (int k = 0; k <= 2; k++)
        {
            nodes[k][k] = new Node(k, k, i++);
            nodes[3][k] = new Node(3, k, i++);
            nodes[6 - k][k] = new Node(6 - k, k, i++);
            nodes[6 - k][3] = new Node(6 - k, 3, i++);
            nodes[6 - k][6 - k] = new Node(6 - k, 6 - k, i++);
            nodes[3][6 - k] = new Node(3, 6 - k, i++);
            nodes[k][6 - k] = new Node(k, 6 - k, i++);
            nodes[k][3] = new Node(k, 3, i++);
        }
    }

    private void createNodeSets()
    {
        int nSetsIndex = 0;
        int k = 0;
        Node[] node = new Node[3];
        // prechadzam polom po stlpcoch a zapisujem nody do nodeSetov
        for (int column = 0; column < columnCount; column++)
        {
            for (int row = 0; row < rowCount; row++)
            {
               if (nodes[row][column] != null)
               {
                   node[k] = nodes[row][column];
                   k++;
                   if (k == 3)
                   {
                       node[0].setNodePosition("T");
                       node[1].setNodePosition("M");
                       node[2].setNodePosition("B");
                       nodeSets[nSetsIndex] = new NodeSet(node[0], node[1], node[2]);
                       nSetsIndex++;
                       k = 0;
                   }
               }
               else if (k > 0)
               {
                   nodes[row][column] = new Node(NodeState.VERTICAL_JOINT);
               }
            }
        }
        // prechadzam polom po riadkoch a zapisujem nody do nodeSetov
        for (int row = 0; row < rowCount; row++)
        {
            for (int column = 0; column < columnCount; column++)
            {
                if (nodes[row][column] != null && nodes[row][column].getState() != NodeState.VERTICAL_JOINT)
                {
                    node[k] = nodes[row][column];
                    k++;
                    if (k == 3)
                    {
                        node[0].setNodePosition(node[0].getNodePosition() + "L");
                        node[1].setNodePosition(node[1].getNodePosition() + "M");
                        node[2].setNodePosition(node[2].getNodePosition() + "R");
                        nodeSets[nSetsIndex] = new NodeSet(node[0], node[1], node[2]);
                        nSetsIndex++;
                        k = 0;
                    }
                }
                else if (k > 0)
                {
                    nodes[row][column] = new Node(NodeState.HORIZONTAL_JOINT);
                }
            }
        }
        nodes[3][3] = new Node(NodeState.NONE_JOINT);
    }

    public boolean isNodeEmpty(int row, int column)
    {
        if (getNode(row, column) == null ||
            getNode(row, column).getState() == NodeState.HORIZONTAL_JOINT ||
            getNode(row, column).getState() == NodeState.VERTICAL_JOINT ||
            getNode(row, column).getState() == NodeState.NONE_JOINT) return false;
        return getNode(row, column).getState() == NodeState.EMPTY;
    }

    public boolean isNodeOccupied(int row, int column)
    {
        Node node = getNode(row, column);
        if (node == null ||
                node.getState() == NodeState.HORIZONTAL_JOINT ||
                node.getState() == NodeState.VERTICAL_JOINT ||
                node.getState() == NodeState.NONE_JOINT) return false;
        return node.getState() == NodeState.WHITE ||
                node.getState() == NodeState.BLACK;
    }

    //dalsi contains ktory forkom prechadza vsetkymi nodeSetami?????????

    public boolean isMill(Node node)
    {
        for (NodeSet set : nodeSets)
        {
            if (set.containsNode(node) && set.isMill())
            {
                return true;
            }
        }
        return false;
    }

    // unselectne vsetky ostatne selectnute figurky na hracej doske
    private void unselectAll()
    {
        for (int column = 0; column < columnCount; column++)
        {
            for (int row = 0; row < rowCount; row++)
            {
                if (nodes[row][column].isSelected())
                {
                    nodes[row][column].setSelected(false);
                }
            }
        }
        selectedPiece = null;
    }

    public boolean actionPiece(int row, int column) // riesim celu funkcionalitu hry, aby som obisla consoleUI
    {
        if (removing)
        {
            decrementPlayerPieces(actualPlayer);
            removePiece(row, column);
            removing = false;
            return true;
        }
        else if (round >= 1 && round <= 9) // ak round 1-9, tak setPiece(row, column, player)
        {
            if (setPiece(row, column, actualPlayer))
            {
                removing = isMill(getNode(row, column));
                if (removing)
                {
                    swapActualPlayer();
                    return true;
                }
                if (actualPlayer == NodeState.WHITE) // ak je na rade biely hrac, prirata sa kolo
                {
                    round++;
                }
                return true;
            }
        }
        else
        {
            if (!isMovePossible(actualPlayer))
            {
                return true;
            }
            if (selectedPiece == null)
            {
                selectPiece(row, column, actualPlayer);
                return true;
            }
            else
            {
                if (isNodeEmpty(row, column))
                {
                    boolean successfullMove;
                    if (actualPlayer == NodeState.BLACK && blackPlayerNumberOfPieces == 3)
                    {
                        successfullMove = jumpPiece(row, column, actualPlayer);
                    }
                    else if (actualPlayer == NodeState.WHITE && whitePlayerNumberOfPieces == 3)
                    {
                        successfullMove = jumpPiece(row, column, actualPlayer);
                    }
                    else
                    {
                        successfullMove = movePiece(row, column, actualPlayer);
                    }
                    if (successfullMove)
                    {
                        removing = isMill(getNode(row, column));
                        if (removing)
                        {
                            swapActualPlayer();
                            return true;
                        }
                        if (actualPlayer == NodeState.WHITE)
                        {
                            round++;
                        }
                    }
                    return true;
                }
                else
                {
                    selectPiece(row, column, actualPlayer);
                    return true;
                }
            }
        }
        return false;
    }

    // na zaciatku hry ma kazdy hrac k dispozicii 9 figurok, ktore mozu polozit na hraciu dosku
    // metoda setPiece urcuje ci je mozne na danu poziciu figurku polozit
    // figurku mozno polozit iba na node so statusom EMPTY
    public boolean setPiece(int row, int column, NodeState nodeState)
    {
        if (isNodeEmpty(row, column))
        {
            getNode(row, column).setState(nodeState);
            swapActualPlayer();
            return true;
        }
        return false;
    }

    private void swapActualPlayer()
    {
        actualPlayer = actualPlayer == NodeState.WHITE ? NodeState.BLACK : NodeState.WHITE; // zmena ktory hrac je na rade
    }

    // mozem aj metodu select a potom move

    public boolean selectPiece(int row, int column, NodeState nodeState)
    {
        if (isNodeOccupied(row, column) && nodeState == getNode(row, column).getState())
        {
            unselectAll();
            getNode(row, column).setSelected(true);
            selectedPiece = getNode(row, column);
            return true;
        }
        return false;
    }

    // posuvanie figurky z miesta na miesto v ramci nodeSetu

    public boolean movePiece(int row, int column, NodeState nodeState)
    {
        for (NodeSet set : nodeSets)
        {
            if (isNodeEmpty(row, column) && set.containsNode(getNode(row, column)) &&
                set.containsNode(selectedPiece) && set.areNodesNeighbors(getNode(row, column), selectedPiece))
            {
                removePiece(selectedPiece.getX(), selectedPiece.getY());
                swapActualPlayer();
                unselectAll();
                getNode(row, column).setState(nodeState);
                swapActualPlayer();
                return true;
            }
        }
        return false;
    }

    // po vytvoreni mlynu musi hrac odstranit lubovolnu figurku protihraca
    public boolean removePiece(int row, int column)
    {
        if (isNodeOccupied(row, column))
        {
            getNode(row, column).setState(NodeState.EMPTY);
            swapActualPlayer();
            return true;
        }
        return false;
    }

    // ak ma niektory z hracov na hracej doske prave 3 figurky,
    // dany hrac ma moznost skakat so svojimi figurkami po doske ako sa mu zachce
    public boolean jumpPiece(int row, int column, NodeState nodeState)
    {
        if (isNodeEmpty(row, column))
        {
            removePiece(selectedPiece.getX(), selectedPiece.getY());
            swapActualPlayer();
            unselectAll();
            getNode(row, column).setState(nodeState);
            swapActualPlayer();
            return true;
        }
        return false;
    }
    // znizovanie poctu figuriek protihraca + kontrola ci nejaky hrac uz vyhral

    public boolean decrementPlayerPieces(NodeState player)
    {
        if (player == NodeState.WHITE)
        {
            blackPlayerNumberOfPieces--;
            if (blackPlayerNumberOfPieces == 2)
            {
                setGameState(GameState.PLAYER1WON);
            }
        }
        else if (player == NodeState.BLACK)
        {
            whitePlayerNumberOfPieces--;
            if (whitePlayerNumberOfPieces == 2)
            {
                setGameState(GameState.PLAYER2WON);
            }
        }
        return false;
    }

    // vracia hodnotu z pola, konkretne policko
    public Node getNode(int row, int column)
    {
        return nodes[row][column];
    }

    // kontrola ci sa moze vykonat funkcia movePiece
    // v pripade ze funkcia vrati false, hra sa skoncila, kedze sa hrac uz nema kam posunut
    // ak najdem neprazdny node, potrebujem zistit ci sa v rovnakom nodeSete nachadza nejaky prazdny
    // ak nie isMovePossible vrati false takze niekto prehral
    public boolean isMovePossible(NodeState player)
    {
        for (int i = 0; i < getRowCount(); i++)
        {
            for (int j = 0; j < getColumnCount(); j++)
            {
                Node node = getNode(i, j);
                if (node != null && player == node.getState())

                {
                    for (NodeSet set : nodeSets)
                    {
                        if (set.containsNode(node) && set.isNeighborNodeEmpty(node))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        if (player == NodeState.WHITE)
        {
            setGameState(GameState.PLAYER2WON);
        }
        else if (player == NodeState.BLACK)
        {
            setGameState(GameState.PLAYER1WON);
        }
        return false;
    }

    // vrati z nodeSetu konkretny node na danom indexe
    public Node getNodeFromNodeSet(int index)
    {
        for (NodeSet nemamNervyNaTotoUz : nodeSets)
        {
            if (nemamNervyNaTotoUz.getFirst().getNodeIndex() == index)
            {
                return nemamNervyNaTotoUz.getFirst();
            }
            if (nemamNervyNaTotoUz.getSecond().getNodeIndex() == index)
            {
                return nemamNervyNaTotoUz.getSecond();
            }
            if (nemamNervyNaTotoUz.getThird().getNodeIndex() == index)
            {
                return nemamNervyNaTotoUz.getSecond();
            }
        }
        return null;
    }
    // na zaklade daneho indexu vrati konkretny nodeSet

    public NodeSet getNodeSet(int index)
    {
        return nodeSets[index];
    }
    public GameState getGameState()
    {
        return gameState;
    }

    public int getRowCount()
    {
        return rowCount;
    }
    public int getColumnCount()
    {
        return columnCount;
    }

    void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public int getWhitePlayerNumberOfPieces()
    {
        return whitePlayerNumberOfPieces;
    }

    public int getBlackPlayerNumberOfPieces()
    {
        return blackPlayerNumberOfPieces;
    }

    public int getRound()
    {
        return round;
    }

    private void setRound(int round)
    {
        this.round = round;
    }

    public NodeState getActualPlayer()
    {
        return actualPlayer;
    }

    public void setActualPlayer(NodeState actualPlayer)
    {
        this.actualPlayer = actualPlayer;
    }

    public boolean isRemoving()
    {
        return removing;
    }

    private void setRemoving(boolean removing)
    {
        this.removing = removing;
    }

    public Node getSelectedPiece()
    {
        return selectedPiece;
    }

    public void setSelectedPiece(Node selectedPiece)
    {
        this.selectedPiece = selectedPiece;
    }

    public int getFinalScore()
    {
        return Math.max(100 - round, 0);
    }

}

