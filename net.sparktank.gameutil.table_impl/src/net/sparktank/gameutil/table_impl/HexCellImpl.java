package net.sparktank.gameutil.table_impl;

import java.util.Collection;

import net.sparktank.gameutil.table.CellAnnotation;
import net.sparktank.gameutil.table.Coordinates;
import net.sparktank.gameutil.table.Piece;
import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;

public class HexCellImpl implements HexCell {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexCoordinates coordinates;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexCellImpl (HexCoordinates coordinates) {
		this.coordinates = coordinates;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexCell methods.
	
	@Override
	public HexCoordinates getHexCoordinates() {
		return this.coordinates;
	}
	
	@Override
	public Collection<HexPiece> getHexPieces() {
		throw new RuntimeException("Not implemented.");
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Cell methods.
	
	@Override
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	@Override
	public Collection<CellAnnotation> getCellAnnotations() {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Collection<Piece> getPieces() {
		throw new RuntimeException("Not implemented.");
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
