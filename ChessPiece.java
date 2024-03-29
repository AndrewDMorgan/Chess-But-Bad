import java.util.ArrayList;

public class ChessPiece
{
    // the sides
    public enum Sides
    {
        White,
        Black
    }

    // the position of the piece
    private int x;
    private int y;
    private Sides side;
    private String pieceChar;
    private boolean enPassant = false;
    private ArrayList<ChessRules.BaseRule> rules;

    public ChessPiece(int x, int y, Sides side, String pieceChar, ArrayList<ChessRules.BaseRule> rules)
    {
        this.x = x;
        this.y = y;
        this.side = side;
        this.pieceChar = pieceChar;
        this.rules = rules;
    }

    // sets and gets enPassant
    public void SetEnPassant(boolean valid) {  enPassant = valid;  }
    public boolean GetEnPassant() {  return enPassant;  }

    // checks if a move is valid
    public boolean CheckMove(int newX, int newY, ArrayList<ChessPiece> board)
    {
        if (newX < 0 || newX > 7 || newY < 0 || newY > 7) return false;  // the piece is being moved off the board (shouldn't happen unless faulty data is given)

        // looping through all the rules and making sure they're all valid
        for (ChessRules.BaseRule rule : rules)
        {
            // checking if the rule is valid
            if (rule.CheckMove(newX, newY, this, board)) return true;
        }

        // all the rules are valid with this move
        return false;
    }

    // gets the side the piece is on
    public Sides GetSide() {  return side;  }

    // getters for the position
    public int GetX() {  return x;  }
    public int GetY() {  return y;  }

    // gets the char for use in a fen string
    public String GetChar()
    {
        // checking if the char should be capitalized or not
        if (side == Sides.White) return pieceChar.toUpperCase();
        return pieceChar;
    }

    // gets the priority for the piece when being sorted for use in a fen string
    public int GetFenPriority() {  return x + y * 8;  }
}
