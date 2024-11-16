package sk.tuke.kpi.kp.ninemensmorris.core;

public class NodeSet
{
    private Node[] nodes = new Node[3]; //FURT MAM 3 NODY V JEDNOM SETE!!!!!!

    public NodeSet(Node first, Node second, Node third)
    {
        nodes[0] = first;
        nodes[1] = second;
        nodes[2] = third;
    }

    boolean isMill()
    {
        return getFirst().getState() != NodeState.EMPTY &&
                getFirst().getState() == getSecond().getState() &&
                getSecond().getState() == getThird().getState();
    }

    public boolean containsNode(Node node)
    {
        return nodes[0] == node || nodes[1] == node || nodes[2] == node;
    }

    void setFirst(Node first)
    {
        nodes[0] = first;
    }

    void setSecond(Node second)
    {
        nodes[1] = second;
    }

    void setThird(Node third)
    {
        nodes[2] = third;
    }

    public Node getFirst()
    {
        return nodes[0];
    }

    public Node getSecond()
    {
        return nodes[1];
    }

    public Node getThird()
    {
        return nodes[2];
    }

    public Node[] getNodes()
    {
        return nodes;
    }

    void setNodes(Node[] nodes)
    {
        this.nodes = nodes;
    }

    public boolean isAnyNodeEmpty()
    {
        return getFirst().getState() == NodeState.EMPTY ||
               getSecond().getState() == NodeState.EMPTY ||
               getThird().getState() == NodeState.EMPTY;
    }

    public boolean isNeighborNodeEmpty(Node node)
    {
        if (!isAnyNodeEmpty()) { return false; }
        if (node == getSecond())
        {
            return isAnyNodeEmpty();
        }
        else
        {
            return getSecond().getState() == NodeState.EMPTY;
        }
    }

    public boolean areNodesNeighbors(Node node, Node node1)
    {
        return ((node == getFirst() || node == getThird()) && node1 == getSecond()) ||
                ((node1 == getFirst() || node1 == getThird()) && node == getSecond());
    }
}


