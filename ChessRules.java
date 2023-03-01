import java.util.ArrayList;

public class ChessRules
{
    // the base rule
    public static class BaseRule
    {
        // checks if a move is valid
        public boolean CheckMove(int newX, int newY, ChessPiece piece, ArrayList<ChessPiece> board) {  return true;  }
    }

    // the slide rule
    public static class SlideRule extends BaseRule
    {
        // the direction
        private int dirX;
        private int dirY;

        // the max length the piece can slide
        private int maxLength;

        public SlideRule(int dirX, int dirY, int maxLength)
        {
            this.dirX = dirX;
            this.dirY = dirY;
            this.maxLength = maxLength;
        }

        // checks if there is a chess piece on a given position/spot
        private boolean CheckForPiece(ChessPiece origonalPiece, ArrayList<ChessPiece> board, int x, int y)
        {
            // looping through all the pieces
            for (ChessPiece piece : board)
            {
                // checking if the piece is at the location being checked
                if (piece != origonalPiece && piece.GetX() == x && piece.GetY() == y) return true;
            }
            // no pieces overlap the location given
            return false;
        }

        // checks if there are any pieces on the path
        private boolean CheckForPiecesOnPath(ChessPiece origonalPiece, ArrayList<ChessPiece> board, int factor, int x, int y)
        {
            // checking for pieces along the path
            for (int i = 0; i <= factor; i++)
            {
                // checking for a piece at the location
                if (CheckForPiece(origonalPiece, board, x, y)) return true;

                // moving the pieces position
                x += dirX;
                y += dirY;
            }

            // there were no pieces found
            return false;
        }

        // checks if a move is valid (in regard to the rule)
        public boolean CheckMove(int newX, int newY, ChessPiece piece, ArrayList<ChessPiece> board)
        {
            // the position of the piece before moving
            int xOld = piece.GetX();
            int yOld = piece.GetY();

            // the change in position
            int dx = newX - xOld;
            int dy = newY - yOld;

            // checking if the piece is in the correct direction
            float factor = (float) dx / dirX;
            if (dy / factor == (float) dirY && factor <= maxLength && Math.floor(factor) == Math.ceil(factor))
            {
                // checking if there are pieces in the way
                return !CheckForPiecesOnPath(piece, board, (int) factor, xOld, yOld);
            }

            return false;
        }
    }
}


