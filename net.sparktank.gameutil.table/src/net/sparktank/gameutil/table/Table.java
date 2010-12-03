package net.sparktank.gameutil.table;

import java.util.Collection;

public interface Table {
	
	public Collection<Cell> getAllCells ();
	public Collection<Piece> getAllPieces ();
	
	public Collection<Annotation> getAllAnnotations ();
	public Collection<CellAnnotation> getAllCellAnnotations ();
	public Collection<PieceAnnotation> getAllPieceAnnotations ();
	
}
