package net.sparktank.gameutil.table_impl;

import net.sparktank.gameutil.table.Coordinates;
import net.sparktank.gameutil.table.Vector;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexVector;

public abstract class AbstractHexPiece implements HexPiece {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private HexCoordinates coords;
	private HexVector vector;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public AbstractHexPiece () {
		this.coords = null;
		this.vector = null;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexPiece methods.
	
	@Override
	public HexCoordinates getHexCoordinates() {
		return this.coords;
	}
	
	@Override
	public void setHexCoordinates(HexCoordinates coordinates) {
		this.coords = coordinates;
	}
	
	@Override
	public HexVector getHexVector() {
		return this.vector;
	}
	
	@Override
	public void setHexVector(HexVector vector) {
		this.vector = vector;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Piece methods.
	
	@Override
	public Coordinates getCoordinates() {
		return getHexCoordinates();
	}
	
	@Override
	public Vector getVector() {
		return getHexVector();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
