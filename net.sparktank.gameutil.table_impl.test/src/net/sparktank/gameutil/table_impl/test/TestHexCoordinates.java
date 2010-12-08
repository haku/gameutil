package net.sparktank.gameutil.table_impl.test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table_impl.HexCoordinatesImpl;

import org.junit.Test;

public class TestHexCoordinates {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Test
	public void testGetAdjacentHexCoordinates () {
		HexCoordinatesImpl coord = new HexCoordinatesImpl(1, 1);
		
		List<? extends HexCoordinates> adjacentHexCoordinates = coord.getAdjacentHexCoordinates();
		for (HexCoordinates adjCoord : adjacentHexCoordinates) {
			System.out.print(" ");
			System.out.print(adjCoord);
		}
		System.out.println();
	}
	
	@Test
	public void testGetAdjacentHexCoordinatesRange1 () {
		HexCoordinatesImpl coord = new HexCoordinatesImpl(1, 1);
		
		Map<Integer, List<? extends HexCoordinates>> adjacentHexCoordinates = coord.getAdjacentHexCoordinates(60);
		for (Entry<Integer, List<? extends HexCoordinates>> adjCoords : adjacentHexCoordinates.entrySet()) {
			System.out.print(adjCoords.getKey());
			System.out.print(" (");
			System.out.print(adjCoords.getValue().size());
			System.out.print(")");
			for (HexCoordinates adjCoord : adjCoords.getValue()) {
				System.out.print(" ");
				System.out.print(adjCoord);
			}
			System.out.println();
		}
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
