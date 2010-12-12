/*
 * Copyright 2010 Fae Hutter
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

package net.sparktank.gameutil.table_impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sparktank.gameutil.table.Annotation;
import net.sparktank.gameutil.table.Cell;
import net.sparktank.gameutil.table.CellAnnotation;
import net.sparktank.gameutil.table.Piece;
import net.sparktank.gameutil.table.PieceAnnotation;
import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;

public class HexTableImpl implements HexTable {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Fields.
	
	private final Map<Long, HexCellImpl> cellMap;
    private final int width;
    private final int height;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Constructors.
	
	/**
	 * This creates a square or rectangular instance of HexTable.
	 * @param width number of cells wide the table should be.
	 * @param height number of cells height the table should be.
	 */
	public HexTableImpl (int width, int height) {
		this.width = width;
		this.height = height;
		this.cellMap = generateRectHexGrid(this, width, height);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexTable methods.
	
	@Override
	public Collection<? extends HexCell> getHexCells() {
		return this.cellMap.values();
	}
	
	@Override
	public List<? extends HexCell> getHexCells(List<? extends HexCoordinates> coordinates) {
		List<HexCell> ret = new LinkedList<HexCell>();
		for (HexCoordinates coord : coordinates) {
			HexCell cell = getHexCell(coord);
			if (cell != null) ret.add(cell);
		}
		return ret;
	}
	
	@Override
	public HexCell getHexCell(HexCoordinates coordinates) {
		return getHexCell(coordinates.getX(), coordinates.getY());
	}
	
	@Override
	public HexCell getHexCell(int x, int y) {
		return this.cellMap.get(Long.valueOf(HexCoordinatesImpl.longHashCoordinates(x, y)));
	}
	
	@Override
	public Collection<? extends HexPiece> getHexPieces() {
		throw new RuntimeException("Not implemented.");
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Table methods.
	
	@Override
	public Collection<? extends Cell> getCells() {
		return this.cellMap.values();
	}
	
	@Override
	public Collection<? extends Piece> getPieces() {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Collection<? extends Annotation> getAnnotations() {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Collection<CellAnnotation> getCellAnnotations() {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Collection<PieceAnnotation> getPieceAnnotations() {
		throw new RuntimeException("Not implemented.");
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public String toString() {
		return "HexTable(" + this.width + "x" + this.height + ")";
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	static helper methods.
	
	protected Map<Long, HexCellImpl> generateRectHexGrid (HexTable table, int w, int h) {
		ConcurrentMap<Long, HexCellImpl> map = new ConcurrentHashMap<Long, HexCellImpl>();
		
//		System.err.println("Cells:");
		
		int x_offset = 0;
		for (int y = 0; y < h; y++) {
			if (y > 0 && y % 2 == 0) x_offset--;
			for (int x = 0; x < w; x++) {
    			HexCoordinatesImpl coord = new HexCoordinatesImpl(x + x_offset, y);
    			HexCellImpl cell = new HexCellImpl(table, coord);
    			HexCellImpl r = map.putIfAbsent(Long.valueOf(HexCoordinatesImpl.longHashCoordinates(x + x_offset, y)), cell);
				if (r != null) throw new RuntimeException("Hash collision.  The hash for "
							+ r.getCoordinates().getX() + "," + r.getCoordinates().getY()
							+ " = the hash for " + coord.getX() + "," + coord.getY() + ".");
    			
//    			System.err.print(" ");
//    			System.err.print(coord);
			}
//			System.err.println();
		}
		
		/* Once made, the board should not be modified.
		 * If this is changed, be sure to update getter methods so that
		 * the modifiable form does not leak.
		 */
		return Collections.unmodifiableMap(map);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
