import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("check move: " + CheckMove("rnbqkbnr/pppppppp/////PPPPPPPP/RNBQKBNR", 4, 6, 4, 4));

        // rnbqkbnr/pppppppp/////PPPPPPPP/RNBQKBNR
        // pawn = "P", knight = "N", bishop = "B", rook = "R", queen = "Q" and king = "K"
        // r: rook
        // n: knight
        // b: bishop
        // q: queen
        // k: king
        // p: pawn
    }

    // checks if a move is valid
    public static boolean CheckMove(String fenString, int oldX, int oldY, int newX, int newY)
    {
        // loading the board
        ArrayList<ChessPiece> board = GetPiecesFromFenString(fenString);

        // finding the piece being moved
        ChessPiece piece = null;
        for (ChessPiece p : board)
            if (p.GetX() == oldX && p.GetY() == oldY) {  piece = p; break;  }

        // checking if no piece was found
        if (piece == null) return false;

        // checking if that move is valid
        return piece.CheckMove(newX, newY, board);
    }

    // a basic bubble sort for sorting an array list of chess pieces
    public static ArrayList<ChessPiece> SortPieces(ArrayList<ChessPiece> originalBoard)
    {
        // the final sorted pieces
        ChessPiece[] sorted = originalBoard.toArray(new ChessPiece[0]);

        // sorting stuff (probably bubble sort because im really lazy)
        boolean isSorted = false;
        while (!isSorted)
        {
            isSorted = true;
            for (int i = 1; i < originalBoard.size(); i++)
            {
                if (sorted[i - 1].GetFenPriority() > sorted[i].GetFenPriority())
                {
                    ChessPiece original = sorted[i];
                    sorted[i] = sorted[i - 1];
                    sorted[i - 1] = original;
                    isSorted = false;  // only will be set to false if it has to switch something
                }
            }
        }

        // printing the results to see if its correctly sorted
        for (int i = 0; i < originalBoard.size(); i++) System.out.println(sorted[i].GetFenPriority());

        // returning the sorted pieces
        return new ArrayList<>(Arrays.asList(sorted));
    }

    public static int IsPositiveInt(String strNum)
    {
        try {  return Integer.parseInt(strNum);  }
        catch (NumberFormatException nfe) {  return -1;  }
    }

    public static ArrayList<ChessPiece> GetPiecesFromFenString(String fenString)
    {
        // create a hash map of rules for different pieces
        HashMap<String, ArrayList<ChessRules.BaseRule>> rules = new HashMap<>();
        ArrayList<ChessRules.BaseRule> pieceRules;

        // r: rook
        // n: knight
        // b: bishop
        // q: queen
        // k: king
        // p: pawn

        // top pieces
        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.SlideRule(1, 0, 100));
        pieceRules.add(new ChessRules.SlideRule(0, 1, 100));
        rules.put("r", pieceRules);  // black rook

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.JumpRule(1, 2, true, false));
        pieceRules.add(new ChessRules.JumpRule(1, -2, true, false));
        pieceRules.add(new ChessRules.JumpRule(-1, 2, true, false));
        pieceRules.add(new ChessRules.JumpRule(-1, -2, true, false));

        pieceRules.add(new ChessRules.JumpRule(2, 1, true, false));
        pieceRules.add(new ChessRules.JumpRule(2, -1, true, false));
        pieceRules.add(new ChessRules.JumpRule(-2, 1, true, false));
        pieceRules.add(new ChessRules.JumpRule(-2, -1, true, false));
        rules.put("n", pieceRules);  // black knight

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.SlideRule(-1, 1, 100));
        pieceRules.add(new ChessRules.SlideRule(1, 1, 100));
        rules.put("b", pieceRules);  // black bishop

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.SlideRule(1, 0, 100));
        pieceRules.add(new ChessRules.SlideRule(0, 1, 100));
        pieceRules.add(new ChessRules.SlideRule(-1, 1, 100));
        pieceRules.add(new ChessRules.SlideRule(1, 1, 100));
        rules.put("q", pieceRules);  // black queen

        pieceRules = new ArrayList<>();
        rules.put("k", pieceRules);  // black king

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.JumpRule(0, 2, false, false, 1));
        pieceRules.add(new ChessRules.JumpRule(0, 1, false, false));
        pieceRules.add(new ChessRules.JumpRule(-1, 1, true, true));
        pieceRules.add(new ChessRules.JumpRule(1, 1, true, true));
        rules.put("p", pieceRules);  // black pawn

        // bottom pieces
        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.SlideRule(1, 0, 100));
        pieceRules.add(new ChessRules.SlideRule(0, 1, 100));
        rules.put("R", pieceRules);  // white rook

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.JumpRule(1, 2, true, false));
        pieceRules.add(new ChessRules.JumpRule(1, -2, true, false));
        pieceRules.add(new ChessRules.JumpRule(-1, 2, true, false));
        pieceRules.add(new ChessRules.JumpRule(-1, -2, true, false));

        pieceRules.add(new ChessRules.JumpRule(2, 1, true, false));
        pieceRules.add(new ChessRules.JumpRule(2, -1, true, false));
        pieceRules.add(new ChessRules.JumpRule(-2, 1, true, false));
        pieceRules.add(new ChessRules.JumpRule(-2, -1, true, false));
        rules.put("N", pieceRules);  // white knight

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.SlideRule(-1, 1, 100));
        pieceRules.add(new ChessRules.SlideRule(1, 1, 100));
        rules.put("B", pieceRules);  // white bishop

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.SlideRule(1, 0, 100));
        pieceRules.add(new ChessRules.SlideRule(0, 1, 100));
        pieceRules.add(new ChessRules.SlideRule(-1, 1, 100));
        pieceRules.add(new ChessRules.SlideRule(1, 1, 100));
        rules.put("Q", pieceRules);  // white queen

        pieceRules = new ArrayList<>();
        rules.put("K", pieceRules);  // white king

        pieceRules = new ArrayList<>();
        pieceRules.add(new ChessRules.JumpRule(0, -2, false, false, 6));
        pieceRules.add(new ChessRules.JumpRule(0, -1, false, false));
        pieceRules.add(new ChessRules.JumpRule(-1, -1, true, true));
        pieceRules.add(new ChessRules.JumpRule(1, -1, true, true));
        rules.put("P", pieceRules);  // white pawn

        // the current position
        int x = 0;
        int y = 0;

        // the array of pieces
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        // looping through each character
        for (int i = 0; i < fenString.length(); i++)
        {
            // reading the character at the position
            String letter = String.valueOf(fenString.charAt(i));
            int isInt = IsPositiveInt(letter);

            // operating based on the character
            if (isInt != -1) x += isInt;
            else if (letter.equals("/")) {y++; x = 0;}
            else
            {
                if (letter.toUpperCase().equals(letter)) pieces.add(new ChessPiece(x, y, ChessPiece.Sides.White, letter.toLowerCase(), rules.get(letter)));
                else pieces.add(new ChessPiece(x, y, ChessPiece.Sides.Black, letter, rules.get(letter)));
                x++;
            }
        }

        return pieces;
    }

    // gets a fen string from a array list of pieces
    public static String GetFenString(ArrayList<ChessPiece> board)
    {
        // the pieces need to be sorted first
        ArrayList<ChessPiece> sortedBoard = SortPieces(board);

        // getting the initial pieces position
        String fenString = "";
        int lastX = -1;
        int lastY = 0;

        // looping through all the pieces
        for (ChessPiece piece : sortedBoard)
        {
            // getting the char and position of the piece
            String pieceChar = piece.GetChar();
            int x = piece.GetX();
            int y = piece.GetY();

            // checking how many ranks or rows need to be moved from the last piece
            int difX = x - lastX;
            if (y == lastY && difX > 1) fenString += difX - 1;
            else if (y != lastY)
            {
                for (int i = 0; i < y - lastY; i++) fenString += "/";
                if (x != 0) fenString += x;
            }

            // adding the char representing the piece
            fenString += pieceChar;

            // updating the last x and y positions
            lastX = x;
            lastY = y;
        }

        // returning the fenString
        return fenString;
    }
}
