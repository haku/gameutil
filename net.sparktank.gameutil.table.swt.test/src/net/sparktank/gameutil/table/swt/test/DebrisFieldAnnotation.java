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
import java.util.Map;
import java.util.Random;

import net.sparktank.gameutil.table.hex.HexCellAnnotation;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexTable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class DebrisFieldAnnotation implements HexCellAnnotation {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final int TYPEID = 201;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final Map<HexCoordinates, Void> affectedCells;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public DebrisFieldAnnotation (Collection<HexCoordinates> coords) {
		Map<HexCoordinates, Void> m = new HashMap<HexCoordinates, Void>();
		for (HexCoordinates c : coords) m.put(c, null);
		this.affectedCells = Collections.unmodifiableMap(m);
	}
	
	/**
	 * Helper constructor for rectangular tables.
	 */
	public DebrisFieldAnnotation (HexTable table, int width, int height, int count) {
		Map<HexCoordinates, Void> m = new HashMap<HexCoordinates, Void>();
		
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < count; i++) {
			int y = random.nextInt(height);
			int x = random.nextInt(width) - y/2;
			HexCoordinates c = table.getHexCoordinates(x, y);
			if (c != null) m.put(c, null);
		}
		
		this.affectedCells = Collections.unmodifiableMap(m);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public int getTypeId() {
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
	
	static public void paintHexCellAnnotation (DebrisFieldAnnotation annotation, HexCoordinates coordinates, GC gc, Rectangle rect) {
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_GRAY));
		gc.fillOval(rect.x, rect.y, rect.width, rect.height);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
