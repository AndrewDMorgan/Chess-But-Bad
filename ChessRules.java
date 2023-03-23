import java.util.ArrayList;

public class ChessRules
{
    // checks if there is a chess piece on a given position/spot
    public static int CheckForPiece(ChessPiece origonalPiece, ArrayList<ChessPiece> board, int x, int y)
    {
        // looping through all the pieces
        int i = 0;
        for (ChessPiece piece : board)
        {
            // checking if the piece is at the location being checked
            if (piece != origonalPiece && piece.GetX() == x && piece.GetY() == y) return i;
            i++;
        }

        // no pieces overlap the location given
        return -1;
    }

    // checks if there are any pieces on the path
    public static boolean CheckForPiecesOnPath(ChessPiece origonalPiece, ArrayList<ChessPiece> board, int factor, int dirX, int dirY, int x, int y, boolean ignorFinalPiece)
    {
        // getting the sign of factor
        int sign = factor / Math.abs(factor);

        // checking for pieces along the path
        for (int i = 0; i < Math.abs(factor); i++)
        {
            // checking for a piece at the location
            if (CheckForPiece(origonalPiece, board, x, y) != -1) return true;

            // moving the pieces position
            x += dirX * sign;
            y += dirY * sign;
        }

        // checking if the final square has a piece on it and if it does check that it's a different color
        int finalSquare = CheckForPiece(origonalPiece, board, x, y);
        if (!ignorFinalPiece && finalSquare != -1 && board.get(finalSquare).GetSide() == origonalPiece.GetSide()) return true;  // you can't kill yourself

        // there were no pieces found
        return false;
    }

    // the base rule
    public static class BaseRule
    {
        // checks if a move is valid
        public boolean CheckMove(int newX, int newY, ChessPiece piece, ArrayList<ChessPiece> board) {  return true;  }
    }

    // the en passant rule
    public static class EnPassant extends BaseRule
    {
        // the movement pattern
        private int changeX;
        private int changeY;

        public EnPassant(int changeX, int changeY)
        {
            this.changeX = changeX;
            this.changeY = changeY;
        }

        // checks if the move is valid
        public boolean CheckMove(int newX, int newY, ChessPiece piece, ArrayList<ChessPiece> board)
        {
            // the change in position
            int difX = newX - piece.GetX();
            int difY = newY - piece.GetY();

            // making sure the movement is correct
            if (difX != changeX || difY != changeY) return false;

            // getting the adjacent piece
            ChessPiece adjacent = new Main().GetPiece(board, newX, piece.GetY());

            // making sure that piece can be taken by en passant (aka just moved two squares up/down)
            return adjacent.GetEnPassant();
        }
    }

    // the jump rule for jumping positions
    public static class JumpRule extends BaseRule
    {
        // the change in position allowed by the move
        private int changeX;
        private int changeY;

        // info on if it can kill or needs to kill
        private boolean canKill;
        private boolean needsKill;

        // the row it has to be on for the move to be preformed (-1 means any)
        private int row;

        public JumpRule(int changeX, int changeY, boolean canKill, boolean needsKill)
        {
            this.changeX = changeX;
            this.changeY = changeY;

            this.canKill = canKill;
            this.needsKill = needsKill;

            this.row = -1;  // disabled / any row
        }

        public JumpRule(int changeX, int changeY, boolean canKill, boolean needsKill, int row)
        {
            this.changeX = changeX;
            this.changeY = changeY;

            this.canKill = canKill;
            this.needsKill = needsKill;

            this.row = row;
        }

        // checks if the move is valid
        public boolean CheckMove(int newX, int newY, ChessPiece piece, ArrayList<ChessPiece> board)
        {
            if (row != -1 && row != piece.GetY()) return false;  // the wrong row for preforming the move

            // the change in position
            int difX = newX - piece.GetX();
            int difY = newY - piece.GetY();

            int kill = CheckForPiece(piece, board, newX, newY);

            // checking if a kill would happen
            if (kill != -1)
            {
                // checking if the move can cause a kill
                if (!canKill) return false;
                // checking if the kill is valid and that the move is valid
                return (board.get(kill).GetSide() != piece.GetSide()) && (difX == changeX) && (difY == changeY);
            }

            // returning if the jump is valid
            return (!needsKill) && (difX == changeX) && (difY == changeY);
        }
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

        // checks if a move is valid (in regard to the rule)
        public boolean CheckMove(int newX, int newY, ChessPiece piece, ArrayList<ChessPiece> board)
        {
            // the position of the piece before moving
            int xOld = piece.GetX();
            int yOld = piece.GetY();

            // the change in position
            int dx = newX - xOld;
            int dy = newY - yOld;

            // checking if the piece is moving in the correct direction and within bounds of the slide
            if (dirX == 0)
            {
                float factor = (float) dy / dirY;
                if (dx / factor == (float) dirX && Math.abs(factor) <= maxLength && Math.floor(factor )== Math.ceil(factor))
                {
                    // checking if there are pieces in the way
                    return !CheckForPiecesOnPath(piece, board, (int) factor, dirX, dirY, xOld, yOld, false);
                }
            }
            else
            {
                float factor = (float) dx / dirX;
                if (dy / factor == (float) dirY && Math.abs(factor) <= maxLength && Math.floor(factor) == Math.ceil(factor))
                {
                    // checking if there are pieces in the way
                    return !CheckForPiecesOnPath(piece, board, (int) factor, dirX, dirY, xOld, yOld, false);
                }
            }

            return false;
        }
    }
}


