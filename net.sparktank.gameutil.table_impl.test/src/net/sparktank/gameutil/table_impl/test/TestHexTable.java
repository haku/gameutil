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

package net.sparktank.gameutil.table_impl.test;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import net.sparktank.gameutil.table.Coordinates;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexTable;
import net.sparktank.gameutil.table_impl.HexTableImpl;

import org.junit.Test;

public class TestHexTable {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Test
	public void testHexTable () {
		HexTable hexTable = new HexTableImpl(7, 10);
		
		testHexTableHasCell(hexTable, 0, 0, true);
		testHexTableHasCell(hexTable, 6, 0, true);
		testHexTableHasCell(hexTable, -4, 9, true);
		testHexTableHasCell(hexTable, 2, 9, true);
		
		testHexTableHasCell(hexTable, -1, -1, false);
		testHexTableHasCell(hexTable, 7, -1, false);
		testHexTableHasCell(hexTable, -5, 10, false);
		testHexTableHasCell(hexTable, 2, 10, false);
	}
	
	@Test
	public void testHexTableGetCells () {
		HexTable hexTable = new HexTableImpl(100, 100);
		
		HexCoordinates coord = hexTable.getHexCoordinates(50, 50);
		Map<Integer, ? extends Collection<? extends Coordinates>> adjacentCoordinates = coord.getAdjacentCoordinates(10);
		
		int n = 0;
		for (Entry<Integer, ? extends Collection<? extends Coordinates>> e : adjacentCoordinates.entrySet()) {
			n = n + e.getValue().size();
		}
		if (n != 331) throw new RuntimeException("found " + adjacentCoordinates.size() + " expected 331.");
		
		// TODO do a better test here.
	}
	
	@Test
	public void testHexTableBig () {
		HexTable hexTable = new HexTableImpl(500, 500);
		HexCoordinates coord = hexTable.getHexCoordinates(250,250);
		coord.getAdjacentCoordinates(100);
	}
	
	@Test
	public void testHexTableVeryBig () {
		HexTable hexTable = new HexTableImpl(1000, 1000);
		HexCoordinates coord = hexTable.getHexCoordinates(250,250);
		coord.getAdjacentCoordinates(100);
	}
	
	@Test
	public void testHexTableHuge () {
		HexTable hexTable = new HexTableImpl(1500, 1500);
		HexCoordinates coord = hexTable.getHexCoordinates(250,250);
		coord.getAdjacentCoordinates(100);
	}
	
	@Test
	public void testHexTableUber () {
		HexTable hexTable = new HexTableImpl(2000, 2000);
		HexCoordinates coord = hexTable.getHexCoordinates(250,250);
		coord.getAdjacentCoordinates(100);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public void testHexTableHasCell (HexTable table, int x, int y, boolean expected) {
		
		HexCoordinates coord = table.getHexCoordinates(x, y);
		
		if (coord == null && expected) {
			throw new RuntimeException("Expected cell not found: " + x + "," + y);
		}
		else if (coord != null && !expected) {
			throw new RuntimeException("Unexpected cell found: " + x + "," + y);
		}
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
