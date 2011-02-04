package net.sparktank.gameutil.table.swt;

import net.sparktank.gameutil.table.hex.HexPiece;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public interface HexPiecePainter {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public void paintHexPiece (HexPiece piece, GC gc, Rectangle rect);
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
