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

import java.util.List;
import java.util.Map;

import net.sparktank.gameutil.table.Coordinates;

public interface HexCoordinates extends Coordinates {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	/**
	 * All immediately adjacent coordinates.
	 */
	public List<? extends HexCoordinates> getAdjacentHexCoordinates ();
	
	/**
	 * Return a Collection of HexCoordinatesImpl objects representing the surrounding cells.
	 * @return key = range, value = collection of coordinates.
	 */
	public Map<Integer, List<? extends HexCoordinates>> getAdjacentHexCoordinates (int range);
	
	/**
	 * Calculate distance between two hexes.  Will be one less than the number
	 * if items in the path between them.
	 */
	public int measureHexDistanceTo (HexCoordinates otherCoord);
	
	/**
	 * Return a list of HexCoordinates that represent the shortest path between
	 * two HexCoordinates.  The HexCoordinates will be returned in order.
	 * The first item in the list will be this HexCoordinates and the last item
	 * in the list will be the target HexCoordinates.
	 */
	public List<HexCoordinates> getHexPathTo (HexCoordinates otherCoord);
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
