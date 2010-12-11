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

import net.sparktank.gameutil.table.Coordinates;
import net.sparktank.gameutil.table.hex.HexBearing;
import net.sparktank.gameutil.table.hex.HexCoordinates;

public class HexCoordinatesImpl implements HexCoordinates {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public final int x;
	public final int y;
	
	public final int hash;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexCoordinatesImpl (int x, int y) {
		this.x = x;
		this.y = y;
		
		/* Since this is an immutable class,
		 * we can cache the hash code.
		 * Calculate in constructor to be thread-safe.
		 */
		this.hash = hashCoordinates(this.x, this.y);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexCoordinates methods.
	
	@Override
	public List<? extends HexCoordinates> getAdjacentHexCoordinates() {
		List<HexCoordinates> ret = new LinkedList<HexCoordinates>();
		for (HexBearing bearing : HexBearing.values()) {
			HexCoordinates coord = new HexCoordinatesImpl(getX() + bearing.getDx(), getY() + bearing.getDy());
			ret.add(coord);
		}
		return ret;
	}
	
	@Override
	public Map<Integer, List<? extends HexCoordinates>> getAdjacentHexCoordinates(int range) {
		if (range < 1) throw new IllegalArgumentException("Range must be 1 or more.");
		
		/*
		 * NOTE: At some point we may want to worry about the number of HexCoordinatesImpl
		 * objects that will be created and the recycling of them...
		 */
		
		// Using this to look up if we already have one is way faster than Collection.contains().
		Map<HexCoordinates, Void> allCoords = new HashMap<HexCoordinates, Void>();
		allCoords.put(this, null); // Where we are starting.
		
		// Our main return object.
		Map<Integer, List<? extends HexCoordinates>> coordMap = new HashMap<Integer, List<? extends HexCoordinates>>();
		
		// r = 0;  Where we are starting.
		List<HexCoordinates> coordsAtR0 = new LinkedList<HexCoordinates>();
		coordsAtR0.add(this);
		coordMap.put(Integer.valueOf(0), coordsAtR0);
		
		// r = 1;
		List<? extends HexCoordinates> coordsAtR1 = this.getAdjacentHexCoordinates();
		coordMap.put(Integer.valueOf(1), coordsAtR1);
		for (HexCoordinates coordAtR1 : coordsAtR1) {
			allCoords.put(coordAtR1, null);
		}
		
		// r > 1;
		for (int r = 1; r < range; r++) {
			Integer R = Integer.valueOf(r);
			List<? extends HexCoordinates> coordsAtR = coordMap.get(R);
			List<HexCoordinates> coordsBeyondR = new LinkedList<HexCoordinates>();
			
			for (HexCoordinates coordAtR : coordsAtR) {
				List<? extends HexCoordinates> beyondCoords = coordAtR.getAdjacentHexCoordinates();
				for (HexCoordinates beyondCoord : beyondCoords) {
					if (!allCoords.containsKey(beyondCoord)){
						coordsBeyondR.add(beyondCoord);
						allCoords.put(beyondCoord, null);
					}
				}
			}
			
			coordMap.put(Integer.valueOf(r + 1), coordsBeyondR);
		}
		
		return coordMap;
	};
	
	@Override
	public int measureHexDistanceTo (HexCoordinates otherCoord) {
		int dist = 0;
		
		int cx = this.getX();
		int cy = this.getY();
		
		int tx = otherCoord.getX();
		int ty = otherCoord.getY();
		
		while (true) {
			if (cx == tx && cy == ty) break;
			
			dist++;
			
			if (cx < tx) cx++; else if (cx > tx) cx--;
			if (cy < ty) cy++; else if (cy > ty) cy--;
		}
		
		return dist;
	}
	
	@Override
	public List<HexCoordinates> getHexPathTo(HexCoordinates otherCoord) {
		List<HexCoordinates> path = new LinkedList<HexCoordinates>();
		path.add(this);
		
		int tx = otherCoord.getX();
		int ty = otherCoord.getY();
		
		HexCoordinates c = this;
		while (true) {
			if (c.equals(otherCoord)) break;
			
			int cx = c.getX();
			int cy = c.getY();
			
			if (cx < tx) cx++; else if (cx > tx) cx--;
			if (cy < ty) cy++; else if (cy > ty) cy--;
			
			HexCoordinates nextCoord = new HexCoordinatesImpl(cx, cy);
			path.add(nextCoord);
			c = nextCoord;
		}
		
		return path;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Coordinates methods.
	
	@Override
	public int getX() {
		return this.x;
	}
	
	@Override
	public int getY() {
		return this.y;
	}
	
	@Override
	public Map<Integer, ? extends Collection<? extends Coordinates>> getAdjacentCoordinates(int range) {
		return getAdjacentHexCoordinates(range);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	Meta-magic feat (reduce casting time).
	
	public boolean equals (HexCoordinates that) {
		return that == null ? false : this.x == that.getX() && this.y == that.getY();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public String toString () {
		return "hex(" + this.x + "," + this.y + ")";
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public boolean equals (Object aThat) {
		if ( aThat == null ) return false;
		if ( this == aThat ) return true;
		if ( !(aThat instanceof HexCoordinatesImpl) ) return false;
		HexCoordinatesImpl that = (HexCoordinatesImpl)aThat;
		
		return this.equals(that);
	}
	
	@Override
	public int hashCode() {
		return this.hash;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	static public int hashCoordinates (int x, int y) {
		return (1 * (31 + x)) * 31 + y;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
