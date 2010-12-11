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

package net.sparktank.gameutil.table.hex;

import java.util.Collection;
import java.util.List;

import net.sparktank.gameutil.table.Table;

/**
 * 
 * Note:
 * A HexTable may be any shape as long as every cell is adjacent to another cell.
 * For this reason there are no getWidth() or getHeight() methods as they would not mean anything.
 * 
 * 
 */
public interface HexTable extends Table {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	/**
	 * Returns all the cells on the table.
	 */
	public Collection<? extends HexCell> getHexCells ();
	
	/**
	 * Returns all the cells on the table that are referenced by the coordinates parameter.
	 * Any illegal entries (coordinates for which no cell exists) are ignored.
	 * The HexCell will be returned in the same order as the HexCoordinates were passed in.
	 */
	public List<? extends HexCell> getHexCells (List<? extends HexCoordinates> coordinates);
	
	/**
	 * Fetch a specific cell based on its coordinates.
	 */
	public HexCell getHexCell (HexCoordinates coordinates);
	public HexCell getHexCell (Integer coordinatesHash);
	public HexCell getHexCell (int coordinatesHash);
	public HexCell getHexCell (int x, int y);
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public Collection<? extends HexPiece> getHexPieces ();
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
