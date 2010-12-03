package net.sparktank.gameutil.table.hex;

import java.util.Collection;

import net.sparktank.gameutil.table.Table;

public interface HexTable extends Table {
	
	public Collection<HexCell> getAllHexCells ();
	public Collection<HexPiece> getAllHexPieces ();
	
}
