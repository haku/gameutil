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

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.swt.HexTablePainter;
import net.sparktank.gameutil.table_impl.AbstractHexPiece;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class Mecha extends AbstractHexPiece {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final int TYPEID = 100;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final String name;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public Mecha (HexCoordinates coords, String name) {
		super(coords);
		this.name = name;
	}
	
	public Mecha (HexCoordinates coords) {
		super(coords);
		this.name = null;
	}
	
	public Mecha () {
		super();
		this.name = null;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public int getMaxThrust () {
		return 3;
	}
	
	public int getOverThrust () {
		return 3;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public int getTypeId() {
		return TYPEID;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString () {
		StringBuilder s = new StringBuilder();
		s.append("Mecha");
		s.append(getTypeId());
		if (this.name != null) {
			s.append("/");
			s.append(this.name);
		}
		if (this.getHexCoordinates() != null) {
			s.append("/");
			s.append(this.getHexCoordinates());
		}
		return s.toString();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexPiecePainter methods.
	
	static public void paintHexPiece(HexPiece piece, GC gc, Rectangle rect) {
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_RED));
		gc.fillOval(rect.x + rect.width / 2 - 5, rect.y + rect.height / 2 - 5, 10, 10);
		
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WHITE));
		HexTablePainter.drawTextHVCen(gc, rect.x + rect.width/2, rect.y + rect.height/2 - 5, piece.getName());
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
