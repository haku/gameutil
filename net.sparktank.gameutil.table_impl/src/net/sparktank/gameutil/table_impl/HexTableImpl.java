package net.sparktank.gameutil.table_impl;

import java.util.Collection;

import net.sparktank.gameutil.table.Annotation;
import net.sparktank.gameutil.table.Cell;
import net.sparktank.gameutil.table.CellAnnotation;
import net.sparktank.gameutil.table.Piece;
import net.sparktank.gameutil.table.PieceAnnotation;
import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;

public class HexTableImpl implements HexTable {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Fields.
	
	/*
	 * TODO define fields to track dimensions of the board.
	 * This may well involve assuming some form of board alignment for the in memory model...
	 * 
	 */
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Constructors.
	
	public HexTableImpl (/* TODO Require dimensions here. */) {
		
		// TODO
		
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexTable methods.
	
	@Override
	public Collection<HexCell> getAllHexCells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<HexPiece> getAllHexPieces() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Table methods.
	
	@Override
	public Collection<Cell> getAllCells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Piece> getAllPieces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Annotation> getAllAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CellAnnotation> getAllCellAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PieceAnnotation> getAllPieceAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
