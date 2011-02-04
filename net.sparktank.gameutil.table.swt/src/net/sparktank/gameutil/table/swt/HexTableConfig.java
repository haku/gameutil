package net.sparktank.gameutil.table.swt;

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexTable;

public class HexTableConfig {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private static final int DEFAULTCELLSIZE = 30; // px.
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTable hexTable;
	
	private HexCoordinates topLeftCoordinates = null;
	private int cellSize = DEFAULTCELLSIZE;
	private HexTableEventListener eventListener = null;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexTableConfig (HexTable hexTable, HexCoordinates topLeftCoordinates) {
		this.hexTable = hexTable;
		this.topLeftCoordinates = topLeftCoordinates;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexTable getHexTable() {
		return this.hexTable;
	}
	
	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}
	public int getCellSize() {
		return this.cellSize;
	}
	
	public void setTopLeftCoordinates(HexCoordinates topLeftCoordinates) {
		this.topLeftCoordinates = topLeftCoordinates;
	}
	public HexCoordinates getTopLeftCoordinates() {
		return this.topLeftCoordinates;
	}
	
	public void setEventListener(HexTableEventListener eventListener) {
		this.eventListener = eventListener;
	}
	
	public HexTableEventListener getEventListener() {
		return this.eventListener;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
