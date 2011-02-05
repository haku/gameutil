/*
 * Copyright 2011 Fae Hutter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

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

public class MechaThrustAnnotation implements HexCellAnnotation {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final int TYPEID = 200;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final Map<HexCoordinates, Void> affectedCells;
	private final Map<HexCoordinates, Void> affectedCellsOverthrust;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

	public MechaThrustAnnotation (HexCoordinates centre, Mecha mecha) {
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
	
	static public void paintHexCellAnnotation(MechaThrustAnnotation annotation, HexCoordinates coordinates, GC gc, Rectangle rect) {
		gc.setBackground(gc.getDevice().getSystemColor(annotation.isOverthrust(coordinates) ? SWT.COLOR_DARK_YELLOW : SWT.COLOR_DARK_GREEN));
		gc.fillOval(rect.x, rect.y, rect.width, rect.height);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
