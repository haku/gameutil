package net.sparktank.gameutil.table.hex;

import java.util.Collection;

import net.sparktank.gameutil.table.Cell;

public interface HexCell extends Cell {
	
	public HexCoordinates getHexCoordinates ();
	
	public Collection<HexPiece> getHexPieces ();
	
}
