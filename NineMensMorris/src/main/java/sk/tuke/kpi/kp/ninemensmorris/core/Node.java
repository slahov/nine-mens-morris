package sk.tuke.kpi.kp.ninemensmorris.core;

public class Node
{
    private int x, y, nodeIndex;
    private NodeState state;

    private String nodePosition = new String(new char[2]);

    private boolean isSelected = false;

    public Node(int x, int y, int nodeIndex)
    {
        this.x = x;
        this.y = y;
        this.nodeIndex = nodeIndex;
        state = NodeState.EMPTY;
    }

    public Node(NodeState nodeState)
    {
        state = nodeState;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getNodeIndex()
    {
        return nodeIndex;
    }

    public NodeState getState()
    {
        return state;
    }

    void setState(NodeState state)
    {
        this.state = state;
    }

    public String getNodePosition()
    {
        return nodePosition;
    }

    public void setNodePosition(String nodePosition)
    {
        this.nodePosition = nodePosition;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

}
