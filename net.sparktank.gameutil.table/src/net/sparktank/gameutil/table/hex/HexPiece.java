package net.sparktank.gameutil.table.hex;

import net.sparktank.gameutil.table.Piece;

public interface HexPiece extends Piece {
	
	public HexCoordinates getHexCoordinates ();
	public HexVector getHexVector ();
	
}
