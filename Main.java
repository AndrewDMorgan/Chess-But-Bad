import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main
{
    private HashMap<String, ArrayList<ChessRules.BaseRule>> pieceRules;
    private static ArrayList<String> letters;

    public Main()
    {
        // create a hash map of rules for different pieces
        pieceRules = new HashMap<>();
        ArrayList<ChessRules.BaseRule> rules;

        // r: rook
        // n: knight
        // b: bishop
        // q: queen
        // k: king
        // p: pawn

        // an array of the valid letters for pieces (to screen for invalid chars when loading fens)
        letters = new ArrayList<>();
        letters.add("r"); letters.add("n"); letters.add("b"); letters.add("q"); letters.add("k"); letters.add("p");
        letters.add("R"); letters.add("N"); letters.add("B"); letters.add("Q"); letters.add("K"); letters.add("P");

        // top pieces
        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(1, 0, 100));
        rules.add(new ChessRules.SlideRule(0, 1, 100));
        pieceRules.put("r", rules);  // black rook

        rules = new ArrayList<>();
        rules.add(new ChessRules.JumpRule(1, 2, true, false));
        rules.add(new ChessRules.JumpRule(1, -2, true, false));
        rules.add(new ChessRules.JumpRule(-1, 2, true, false));
        rules.add(new ChessRules.JumpRule(-1, -2, true, false));

        rules.add(new ChessRules.JumpRule(2, 1, true, false));
        rules.add(new ChessRules.JumpRule(2, -1, true, false));
        rules.add(new ChessRules.JumpRule(-2, 1, true, false));
        rules.add(new ChessRules.JumpRule(-2, -1, true, false));
        pieceRules.put("n", rules);  // black knight

        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(-1, 1, 100));
        rules.add(new ChessRules.SlideRule(1, 1, 100));
        pieceRules.put("b", rules);  // black bishop

        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(1, 0, 100));
        rules.add(new ChessRules.SlideRule(0, 1, 100));
        rules.add(new ChessRules.SlideRule(-1, 1, 100));
        rules.add(new ChessRules.SlideRule(1, 1, 100));
        pieceRules.put("q", rules);  // black queen

        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(1, 0, 1));
        rules.add(new ChessRules.SlideRule(0, 1, 1));
        rules.add(new ChessRules.SlideRule(-1, 1, 1));
        rules.add(new ChessRules.SlideRule(1, 1, 1));
        pieceRules.put("k", rules);  // black king

        rules = new ArrayList<>();
        rules.add(new ChessRules.JumpRule(0, 2, false, false, 1));
        rules.add(new ChessRules.JumpRule(0, 1, false, false));
        rules.add(new ChessRules.JumpRule(-1, 1, true, true));
        rules.add(new ChessRules.JumpRule(1, 1, true, true));
        rules.add(new ChessRules.EnPassant( 1, 1));
        rules.add(new ChessRules.EnPassant(-1, 1));
        pieceRules.put("p", rules);  // black pawn

        // bottom pieces
        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(1, 0, 100));
        rules.add(new ChessRules.SlideRule(0, 1, 100));
        pieceRules.put("R", rules);  // white rook

        rules = new ArrayList<>();
        rules.add(new ChessRules.JumpRule(1, 2, true, false));
        rules.add(new ChessRules.JumpRule(1, -2, true, false));
        rules.add(new ChessRules.JumpRule(-1, 2, true, false));
        rules.add(new ChessRules.JumpRule(-1, -2, true, false));

        rules.add(new ChessRules.JumpRule(2, 1, true, false));
        rules.add(new ChessRules.JumpRule(2, -1, true, false));
        rules.add(new ChessRules.JumpRule(-2, 1, true, false));
        rules.add(new ChessRules.JumpRule(-2, -1, true, false));
        pieceRules.put("N", rules);  // white knight

        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(-1, 1, 100));
        rules.add(new ChessRules.SlideRule(1, 1, 100));
        pieceRules.put("B", rules);  // white bishop

        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(1, 0, 100));
        rules.add(new ChessRules.SlideRule(0, 1, 100));
        rules.add(new ChessRules.SlideRule(-1, 1, 100));
        rules.add(new ChessRules.SlideRule(1, 1, 100));
        pieceRules.put("Q", rules);  // white queen

        rules = new ArrayList<>();
        rules.add(new ChessRules.SlideRule(1, 0, 1));
        rules.add(new ChessRules.SlideRule(0, 1, 1));
        rules.add(new ChessRules.SlideRule(-1, 1, 1));
        rules.add(new ChessRules.SlideRule(1, 1, 1));
        pieceRules.put("K", rules);  // white king

        rules = new ArrayList<>();
        rules.add(new ChessRules.JumpRule(0, -2, false, false, 6));
        rules.add(new ChessRules.JumpRule(0, -1, false, false));
        rules.add(new ChessRules.JumpRule(-1, -1, true, true));
        rules.add(new ChessRules.JumpRule(1, -1, true, true));
        rules.add(new ChessRules.EnPassant( 1, -1));
        rules.add(new ChessRules.EnPassant(-1, -1));
        pieceRules.put("P", rules);  // white pawn
    }

    // finds a piece at a given position
    public ChessPiece GetPiece(ArrayList<ChessPiece> board, int x, int y)
    {
        // finding the piece
        ChessPiece piece = null;
        for (ChessPiece p : board)
            if (p.GetX() == x && p.GetY() == y) {  piece = p; break;  }

        return piece;
    }

    // checks if an int list contains an int
    private int CheckForIntSet(int[] arrayX, int[] arrayY, int x, int y)
    {
        // checking for the value in the array
        for (int i = 0; i < arrayX.length; i++) if (arrayX[i] == x && arrayY[i] == y) return i;

        // the value was not found
        return -1;
    }

    // checks if castling is valid (assuming the king hasn't moved) (between king and rook)
    public boolean CheckCastling(String fenString, int oldKingX, int oldKingY, int newKingX, int newKingY)
    {
        // loading the board
        ArrayList<ChessPiece> board = GetPiecesFromFenString(fenString);

        // finding the piece being moved
        ChessPiece piece = GetPiece(board, oldKingX, oldKingY);

        // checking if no piece was found
        if (piece == null) return false;

        // finding the change in position of the king
        int dX = newKingX - oldKingX;
        int dY = newKingY - oldKingY;

        // checking if the change in position is 2 left or right and none up and down, then checking if the path is clear to the rook
        if (dY == 0)
        {
            if (dX == 2)
                return !ChessRules.CheckForPiecesOnPath(piece, board, 3, 1, 0, oldKingX, oldKingY, true);
            else if (dX == -2)
                return !ChessRules.CheckForPiecesOnPath(piece, board, -4, 1, 0, oldKingX, oldKingY, true);
        }

        // wasn't a valid castling
        return false;
    }

    // checks if a move is valid
    public boolean CheckMove(String fenString, String lastFenString, int oldX, int oldY, int newX, int newY)
    {
        // loading the board and piece moving
        ArrayList<ChessPiece> board = GetPiecesFromFenString(fenString);
        ChessPiece piece = GetPiece(board, oldX, oldY);

        // checking if no piece was found
        if (piece == null) return false;

        // loading the previous move to check validity of en passant
        if (lastFenString != null)
        {
            ArrayList<ChessPiece> oldBoard = GetPiecesFromFenString(lastFenString);

            // finding the number of pieces
            int numberPieces = board.size();
            if (oldBoard.size() == numberPieces)
            {
                // getting the position of pieces
                int[] xPossesOld = new int[numberPieces];
                int[] yPossesOld = new int[numberPieces];

                int[] xPossesNew = new int[numberPieces];
                int[] yPossesNew = new int[numberPieces];

                int i = 0;
                for (ChessPiece p : oldBoard) {  xPossesOld[i] = p.GetX(); yPossesOld[i] = p.GetY(); i++;  }
                i = 0;
                for (ChessPiece p : board) {  xPossesNew[i] = p.GetX(); yPossesNew[i] = p.GetY(); i++;  }

                // finding which pieces have no overlap
                ArrayList<Integer> nonOverlapsOld = new ArrayList<>();
                ArrayList<Integer> nonOverlapsNew = new ArrayList<>();

                for (i = 0; i < numberPieces; i++)
                {
                    // checking the old piece
                    int index = CheckForIntSet(xPossesNew, yPossesNew, xPossesOld[i], yPossesOld[i]);
                    if (index == -1) nonOverlapsOld.add(i);

                    // checking the new piece
                    index = CheckForIntSet(xPossesOld, yPossesOld, xPossesNew[i], yPossesNew[i]);
                    if (index == -1) nonOverlapsNew.add(i);
                }

                // making sure only 1 piece moved (aka no casting)
                if (nonOverlapsOld.size() == 1 && nonOverlapsNew.size() == 1)
                {
                    // getting the indexes and positions of the moved pieces
                    int oldIndex = nonOverlapsOld.get(0);
                    int xOld = xPossesOld[oldIndex];
                    int yOld = yPossesOld[oldIndex];

                    int newIndex = nonOverlapsNew.get(0);
                    int xNew = xPossesNew[newIndex];
                    int yNew = yPossesNew[newIndex];

                    // finding the individual pieces
                    ChessPiece oldPiece = oldBoard.get(oldIndex);
                    ChessPiece newPiece = board.get(newIndex);

                    // making sure they're both pawns, and that they moved two spaces down, none over, and they are both the same color
                    if (oldPiece.GetChar().equals(newPiece.GetChar()) && oldPiece.GetChar().equalsIgnoreCase("p") && xOld == xNew && Math.abs(yNew - yOld) == 2)
                    {
                        newPiece.SetEnPassant(true);  // making it so this piece can be taken by en passant
                    }
                }
            }
        }

        // checking if that move is valid
        return piece.CheckMove(newX, newY, board);
    }

    // a basic bubble sort for sorting an array list of chess pieces
    public ArrayList<ChessPiece> SortPieces(ArrayList<ChessPiece> originalBoard)
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

    public int IsPositiveInt(String strNum)
    {
        try {  return Integer.parseInt(strNum);  }
        catch (NumberFormatException nfe) {  return -1;  }
    }

    public ArrayList<ChessPiece> GetPiecesFromFenString(String fenString)
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
            else if (letters.contains(letter))
            {
                if (letter.toUpperCase().equals(letter)) pieces.add(new ChessPiece(x, y, ChessPiece.Sides.White, letter.toLowerCase(), pieceRules.get(letter)));
                else pieces.add(new ChessPiece(x, y, ChessPiece.Sides.Black, letter, pieceRules.get(letter)));
                x++;
            }
        }

        return pieces;
    }

    // gets a fen string from a array list of pieces
    public String GetFenString(ArrayList<ChessPiece> board)
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
