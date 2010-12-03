package net.sparktank.gameutil.table;

import java.util.Collection;

public interface Cell {
	
	public Coordinates getCoordinates ();
	
	public Collection<CellAnnotation> getCellAnnotations ();
	public Collection<Piece> getPieces ();
	
}
