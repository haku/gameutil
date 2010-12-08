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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sparktank.gameutil.table.Cell;
import net.sparktank.gameutil.table.CellAnnotation;
import net.sparktank.gameutil.table.Coordinates;
import net.sparktank.gameutil.table.Piece;
import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;

public class HexCellImpl implements HexCell {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTable parent;
	private final HexCoordinates coordinates;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexCellImpl (HexTable parent, HexCoordinates coordinates) {
		this.parent = parent;
		this.coordinates = coordinates;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexCell methods.
	
	@Override
	public HexCoordinates getHexCoordinates() {
		return this.coordinates;
	}
	
	@Override
	public Collection<? extends HexCell> getAdjacentHexCells(int range) {
		Map<Integer, List<? extends HexCoordinates>> adjCoords = this.coordinates.getAdjacentHexCoordinates(range);
		
		// Collapse down the collections of coordinates at each range.
		Collection<HexCoordinates> coords = new LinkedList<HexCoordinates>();
		for (List<? extends HexCoordinates> coordListAtRange : adjCoords.values()) {
			coords.addAll(coordListAtRange);
		}
		
		Collection<? extends HexCell> ret = this.parent.getHexCells(coords);
		return ret;
	}
	
	@Override
	public int measureHexDistanceTo (HexCell otherCell) {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Collection<? extends HexPiece> getHexPieces() {
		throw new RuntimeException("Not implemented.");
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Cell methods.
	
	@Override
	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	@Override
	public Collection<? extends Cell> getAdjacentCells(int range) {
		return getAdjacentHexCells(range);
	}
	
	@Override
	public Collection<? extends CellAnnotation> getCellAnnotations() {
		throw new RuntimeException("Not implemented.");
	}
	
	@Override
	public Collection<? extends Piece> getPieces() {
		return getHexPieces();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public String toString() {
		return "hexCell(" + this.coordinates.getX() + "," + this.coordinates.getY() + ")";
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
