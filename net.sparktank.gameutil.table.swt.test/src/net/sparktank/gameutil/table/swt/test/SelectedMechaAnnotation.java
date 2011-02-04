package net.sparktank.gameutil.table.swt.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sparktank.gameutil.table.hex.HexCellAnnotation;
import net.sparktank.gameutil.table.hex.HexCoordinates;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class SelectedMechaAnnotation implements HexCellAnnotation {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final int TYPEID = 200;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final Map<HexCoordinates, Void> affectedCells;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	public SelectedMechaAnnotation (HexCoordinates centre, int range) {
		Map<HexCoordinates, Void> m = new HashMap<HexCoordinates, Void>();
		for (List<? extends HexCoordinates> v : centre.getAdjacentHexCoordinates(range).values()) {
			for (HexCoordinates c : v) {
				m.put(c, null);
			}
		}
		this.affectedCells = Collections.unmodifiableMap(m);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public int getTypeId () {
		return TYPEID;
	}
	
	@Override
	public Collection<? extends HexCoordinates> getAffectedCells () {
		return this.affectedCells.keySet();
	}
	
	@Override
	public boolean affectsCell(HexCoordinates coordinates) {
		return this.affectedCells.containsKey(coordinates);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	static public void paintHexCellAnnotation(HexCellAnnotation annotation, GC gc, Rectangle rect) {
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_GREEN));
		gc.fillOval(rect.x, rect.y, rect.width, rect.height);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
