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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sparktank.gameutil.table.Annotation;
import net.sparktank.gameutil.table.CellAnnotation;
import net.sparktank.gameutil.table.Piece;
import net.sparktank.gameutil.table.PieceAnnotation;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;

public class HexTableImpl implements HexTable {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Fields.
	
    private final int width;
    private final int height;
	
    private final Map<Long, Collection<HexPiece>> pieces;
    
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
		
		this.pieces = new HashMap<Long, Collection<HexPiece>>();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexTable methods.
	
	@Override
	public HexCoordinates getHexCoordinates (int x, int y) {
		if (y < 0 || y >= this.height) return null;
		
		int rowOffset = 0 - y / 2; // The number of cells x is shifted by.  Increases by 1 for every 2 rows down.  This will round towards 0.
		if (x < rowOffset || x >= rowOffset + this.width) return null;
		
		return new HexCoordinatesImpl(x, y);
	}
	
	@Override
	public Collection<? extends HexPiece> getHexPieces() {
		List<HexPiece> ret = new LinkedList<HexPiece>();
		for (Collection<HexPiece> e : this.pieces.values()) {
			ret.addAll(e);
		}
		return ret;
	}
	
	@Override
	public Collection<? extends HexPiece> getHexPieces (HexCoordinates coordinates) {
		return this.pieces.get(getHexCoordinatesHash(coordinates));
	}
	
	@Override
	public Collection<? extends HexPiece> getHexPieces (List<? extends HexCoordinates> coordinates) {
		List<HexPiece> ret = new LinkedList<HexPiece>();
		for (HexCoordinates coord : coordinates) {
			Collection<HexPiece> ps = this.pieces.get(getHexCoordinatesHash(coord));
			if (ps != null) ret.addAll(ps);
		}
		return ret;
	}
	
	@Override
	public void addHexPiece (HexPiece piece) {
		if (piece == null) throw new IllegalArgumentException("piece can not be null.");
		HexCoordinates coords = piece.getHexCoordinates();
		if (coords == null) throw new IllegalArgumentException("piece's coords can not be null.");
		
		Long l = getHexCoordinatesHash(coords);
		Collection<HexPiece> c = this.pieces.get(l);
		if (c == null) {
			c = new LinkedList<HexPiece>();
			this.pieces.put(l, c);
		}
		
		c.add(piece);
	}
	
	@Override
	public void updateHexPiece (HexPiece piece, HexCoordinates oldCoordinates) {
		if (oldCoordinates == null) throw new IllegalArgumentException("oldCoordinates can not be null.");
		
		Long l = getHexCoordinatesHash(oldCoordinates);
		Collection<HexPiece> c = this.pieces.get(l);
		c.remove(piece);
		
		addHexPiece(piece);
	}
	
	@Override
	public boolean removeHexPiece (HexPiece piece) {
		if (piece == null) throw new IllegalArgumentException("piece can not be null.");
		HexCoordinates coords = piece.getHexCoordinates();
		if (coords == null) throw new IllegalArgumentException("piece's coords can not be null.");
		
		Long l = getHexCoordinatesHash(coords);
		Collection<HexPiece> c = this.pieces.get(l);
		
		return c.remove(piece);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Table methods.
	
	@Override
	public Collection<? extends Piece> getPieces() {
		return getHexPieces();
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
	
	static public Long getHexCoordinatesHash (HexCoordinates coords) {
		return getHexCoordinatesHash(coords.getX(), coords.getY());
	}
	
	static public Long getHexCoordinatesHash (int x, int y) {
		return Long.valueOf(HexCoordinatesImpl.longHashCoordinates(x, y));
	}
	
//	@Deprecated
//	protected Map<Long, HexCellImpl> generateRectHexGrid (HexTable table, int w, int h) {
//		ConcurrentMap<Long, HexCellImpl> map = new ConcurrentHashMap<Long, HexCellImpl>();
//		
////		System.err.println("Cells:");
//		
//		int x_offset = 0;
//		for (int y = 0; y < h; y++) {
//			if (y > 0 && y % 2 == 0) x_offset--;
//			for (int x = 0; x < w; x++) {
//    			HexCoordinatesImpl coord = new HexCoordinatesImpl(x + x_offset, y);
//    			HexCellImpl cell = new HexCellImpl(table, coord);
//    			HexCellImpl r = map.putIfAbsent(Long.valueOf(HexCoordinatesImpl.longHashCoordinates(x + x_offset, y)), cell);
//				if (r != null) throw new RuntimeException("Hash collision.  The hash for "
//							+ r.getCoordinates().getX() + "," + r.getCoordinates().getY()
//							+ " = the hash for " + coord.getX() + "," + coord.getY() + ".");
//    			
////    			System.err.print(" ");
////    			System.err.print(coord);
//			}
////			System.err.println();
//		}
//		
//		/* Once made, the board should not be modified.
//		 * If this is changed, be sure to update getter methods so that
//		 * the modifiable form does not leak.
//		 */
//		return Collections.unmodifiableMap(map);
//	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
