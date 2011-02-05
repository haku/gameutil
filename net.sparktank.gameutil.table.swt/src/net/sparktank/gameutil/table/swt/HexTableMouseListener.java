/*
 * Copyright 2010 2011 Fae Hutter
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

package net.sparktank.gameutil.table.swt;

import net.sparktank.gameutil.table.hex.HexCoordinates;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class HexTableMouseListener implements MouseListener {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTableConfig config;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexTableMouseListener (HexTableConfig config) {
		this.config = config;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	MouseListener methods.
	
	@Override
	public void mouseDown(MouseEvent e) {
		HexTableEventListener eventListener = this.config.getEventListener();
		if (eventListener != null) {
			HexCoordinates cell = getCoordinatesFromXY(e.x, e.y);
			if (cell != null) eventListener.cellClicked(cell);
		}
	}
	
	@Override
	public void mouseUp(MouseEvent e) {
//		HexCell cell = getCellFromXY(e.x, e.y);
//		System.out.println("mouseUp=" + cell);
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent e) {
//		HexCell cell = getCellFromXY(e.x, e.y);
//		System.out.println("mouseDoubleClick=" + cell);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	protected HexCoordinates getCoordinatesFromXY (int x, int y) {
		HexCoordinates topLeftCoordinates = this.config.getTopLeftCoordinates();
		int cellSize = this.config.getCellSize();
		int halfCellSize = cellSize / 2;
		
		int visibleCellY = (int) (y / (cellSize * HexTablePainter.HEXPITCH));
		
		int rowIndent = (visibleCellY % 2) * halfCellSize; // 0 or halfCellSize.
		int rowOffset = visibleCellY / 2; // The number of cells x is shifted by.  Increases by 1 for every 2 rows down.
		int visibleCellX = (int) Math.floor((x - rowIndent) / (float)cellSize) - rowOffset; // Round down, not towards 0.
		
		int cellY = topLeftCoordinates.getY() + visibleCellY;
		int cellX = topLeftCoordinates.getX() + visibleCellX;
		
		HexCoordinates cell = this.config.getHexTable().getHexCoordinates(cellX, cellY);
		
		return cell;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
