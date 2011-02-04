package net.sparktank.gameutil.table.swt.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	private final Map<HexCoordinates, Void> affectedCellsOverthrust;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	public SelectedMechaAnnotation (HexCoordinates centre, Mecha mecha) {
		Map<HexCoordinates, Void> m = new HashMap<HexCoordinates, Void>();
		Map<HexCoordinates, Void> mo = new HashMap<HexCoordinates, Void>();
		for (Entry<Integer, List<? extends HexCoordinates>> v : centre.getAdjacentHexCoordinates(mecha.getMaxThrust()).entrySet()) {
			if (v.getKey().intValue() == 0) continue;
			if (v.getKey().intValue() >= mecha.getOverThrust()) for (HexCoordinates c : v.getValue()) mo.put(c, null); 
			for (HexCoordinates c : v.getValue()) m.put(c, null);
		}
		this.affectedCells = Collections.unmodifiableMap(m);
		this.affectedCellsOverthrust = Collections.unmodifiableMap(mo);
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
	
	public boolean isOverthrust (HexCoordinates coordinates) {
		return this.affectedCellsOverthrust.containsKey(coordinates);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	static public void paintHexCellAnnotation(SelectedMechaAnnotation annotation, HexCoordinates coordinates, GC gc, Rectangle rect) {
		gc.setBackground(gc.getDevice().getSystemColor(annotation.isOverthrust(coordinates) ? SWT.COLOR_DARK_RED : SWT.COLOR_DARK_GREEN));
		gc.fillOval(rect.x, rect.y, rect.width, rect.height);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
