import java.util.ArrayList;
import java.util.Arrays;

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

        /*
        // adding a new piece to the board
        ChessPiece piece = new ChessPiece(0, 0, ChessPiece.Sides.Black, "p", rules);
        board.add(piece);

        // adding a new piece to the board
        board.add(new ChessPiece(3, 3, ChessPiece.Sides.White, "p", rules));

        // checking different moves
        System.out.println(piece.CheckMove(1, 1, board));
        System.out.println(piece.CheckMove(2, 2, board));
        System.out.println(piece.CheckMove(3, 3, board));
        System.out.println(piece.CheckMove(4, 4, board));
        System.out.println(piece.CheckMove(5, 5, board));
        System.out.println(piece.CheckMove(6, 6, board));
        System.out.println(piece.CheckMove(7, 7, board));*/

        // rnbqkbnr/pppppppp/////PPPPPPPP/RNBQKBNR
        // rnbqkbnr/pppppppp/////PPPPPPPP/RNBQKBNR
        // pawn = "P", knight = "N", bishop = "B", rook = "R", queen = "Q" and king = "K"
        // r: rook
        // n: knight
        // b: bishop
        // q: queen
        // k: king
        // p: pawn

        // 6br/7p/2p1k1pP/rpPp1pP/pP1PpP/P1K1P//
        // 6br/7p/2p1k1pP/rpPp1pP/pP1PpP/P1K1P
        board = GetPiecesFromFenString("rnbqkbnr/pppppppp/////PPPPPPPP/RNBQKBNR");

        /*board.add(new ChessPiece(0, 0, ChessPiece.Sides.Black, "r", rules));
        board.add(new ChessPiece(1, 0, ChessPiece.Sides.Black, "n", rules));
        board.add(new ChessPiece(2, 0, ChessPiece.Sides.Black, "b", rules));
        board.add(new ChessPiece(3, 0, ChessPiece.Sides.Black, "q", rules));
        board.add(new ChessPiece(4, 0, ChessPiece.Sides.Black, "k", rules));
        board.add(new ChessPiece(5, 0, ChessPiece.Sides.Black, "b", rules));
        board.add(new ChessPiece(6, 0, ChessPiece.Sides.Black, "n", rules));
        board.add(new ChessPiece(7, 0, ChessPiece.Sides.Black, "r", rules));
        board.add(new ChessPiece(0, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(1, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(2, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(3, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(4, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(5, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(6, 1, ChessPiece.Sides.Black, "p", rules));
        board.add(new ChessPiece(7, 1, ChessPiece.Sides.Black, "p", rules));

        board.add(new ChessPiece(0, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(1, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(2, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(3, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(4, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(5, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(6, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(7, 6, ChessPiece.Sides.White, "p", rules));
        board.add(new ChessPiece(0, 7, ChessPiece.Sides.White, "r", rules));
        board.add(new ChessPiece(1, 7, ChessPiece.Sides.White, "n", rules));
        board.add(new ChessPiece(2, 7, ChessPiece.Sides.White, "b", rules));
        board.add(new ChessPiece(3, 7, ChessPiece.Sides.White, "q", rules));
        board.add(new ChessPiece(4, 7, ChessPiece.Sides.White, "k", rules));
        board.add(new ChessPiece(5, 7, ChessPiece.Sides.White, "b", rules));
        board.add(new ChessPiece(6, 7, ChessPiece.Sides.White, "n", rules));
        board.add(new ChessPiece(7, 7, ChessPiece.Sides.White, "r", rules));*/

        System.out.println(GetFenString(board));
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
                if (letter.toUpperCase().equals(letter)) pieces.add(new ChessPiece(x, y, ChessPiece.Sides.White, letter.toLowerCase(), new ArrayList<>()));
                else pieces.add(new ChessPiece(x, y, ChessPiece.Sides.Black, letter, new ArrayList<>()));
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
