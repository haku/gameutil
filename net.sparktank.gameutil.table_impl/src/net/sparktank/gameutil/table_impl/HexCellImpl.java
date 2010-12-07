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
		Collection<? extends HexCoordinates> adjCoords = this.coordinates.getAdjacentHexCoordinates(range);
		Collection<? extends HexCell> ret = this.parent.getHexCells(adjCoords);
		return ret;
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
}
