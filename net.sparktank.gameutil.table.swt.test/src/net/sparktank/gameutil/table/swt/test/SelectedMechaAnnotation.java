package net.sparktank.gameutil.table.swt.test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import net.sparktank.gameutil.table.hex.HexCellAnnotation;
import net.sparktank.gameutil.table.hex.HexCoordinates;

public class SelectedMechaAnnotation implements HexCellAnnotation {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final int TYPEID = 200;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final Collection<HexCoordinates> affectedCells;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	public SelectedMechaAnnotation (HexCoordinates centre, int range) {
		List<HexCoordinates> l = new LinkedList<HexCoordinates>();
		for (List<? extends HexCoordinates> v : centre.getAdjacentHexCoordinates(range).values()) {
			l.addAll(v);
		}
		this.affectedCells = Collections.unmodifiableCollection(l);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public int getTypeId () {
		return TYPEID;
	}
	
	@Override
	public Collection<? extends HexCoordinates> getAffectedCells () {
		return this.affectedCells;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	static public void paintHexCellAnnotation(HexCellAnnotation annotation, GC gc, Rectangle rect) {
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_GREEN));
		gc.fillOval(rect.x, rect.y, rect.width, rect.height);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
