package net.sparktank.gameutil.table.swt;

import net.sparktank.gameutil.table.hex.HexCellAnnotation;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public interface HexCellAnnotationPainter {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public void paintHexCellAnnotation (HexCellAnnotation annotation, GC gc, Rectangle rect);
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
