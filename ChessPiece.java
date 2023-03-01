import java.util.ArrayList;

public class ChessPiece
{
    // the position of the piece
    private int x;
    private int y;
    private ArrayList<ChessRules.BaseRule> rules;

    public ChessPiece(int x, int y, ArrayList<ChessRules.BaseRule> rules)
    {
        this.x = x;
        this.y = y;
        this.rules = rules;
    }

    // checks if a move is valid
    public boolean CheckMove(int newX, int newY, ArrayList<ChessPiece> board)
    {
        // looping through all the rules and making sure they're all valid
        for (ChessRules.BaseRule rule : rules)
        {
            // checking if the rule is valid
            if (!rule.CheckMove(newX, newY, this, board)) return false;
        }

        // all the rules are valid with this move
        return true;
    }

    // getters for the position
    public int GetX() {  return x;  }
    public int GetY() {  return y;  }
}
