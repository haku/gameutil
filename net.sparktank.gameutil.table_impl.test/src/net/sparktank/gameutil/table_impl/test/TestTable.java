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

import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexTable;
import net.sparktank.gameutil.table_impl.HexCoordinatesImpl;
import net.sparktank.gameutil.table_impl.HexTableImpl;

import org.junit.Test;

public class TestTable {
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
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public void testHexTableHasCell (HexTable table, int x, int y, boolean expected) {
		
		HexCoordinatesImpl coord = new HexCoordinatesImpl(x, y);
		HexCell cell = table.getHexCell(coord);
		
		if (cell == null && expected) {
			throw new RuntimeException("Expected cell not found: " + coord);
		}
		else if (cell != null && !expected) {
			throw new RuntimeException("Unexpected cell found: " + coord);
		}
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
