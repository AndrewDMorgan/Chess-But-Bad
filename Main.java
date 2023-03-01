import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        // an empty set of rules
        ArrayList<ChessRules.BaseRule> rules = new ArrayList<>();

        // adding the slide rule
        rules.add(new ChessRules.SlideRule(1, 1, 6));

        // creating the board
        ArrayList<ChessPiece> board = new ArrayList<>();

        // adding a new piece to the board
        ChessPiece piece = new ChessPiece(0, 0, rules);
        board.add(piece);

        // adding a new piece to the board
        board.add(new ChessPiece(3, 3, rules));

        // checking different moves
        System.out.println(piece.CheckMove(1, 1, board));
        System.out.println(piece.CheckMove(2, 2, board));
        System.out.println(piece.CheckMove(3, 3, board));
        System.out.println(piece.CheckMove(4, 4, board));
        System.out.println(piece.CheckMove(5, 5, board));
        System.out.println(piece.CheckMove(6, 6, board));
        System.out.println(piece.CheckMove(7, 7, board));
    }
}
