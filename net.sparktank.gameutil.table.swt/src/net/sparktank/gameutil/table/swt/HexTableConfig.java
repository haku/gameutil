package net.sparktank.gameutil.table.swt;

import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexTable;

public class HexTableConfig {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private static final int DEFAULTCELLSIZE = 30; // px.
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTable hexTable;
	
	private HexCell topLeftCell = null;
	private int cellSize = DEFAULTCELLSIZE;
	private HexTableEventListener eventListener = null;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexTableConfig (HexTable hexTable, HexCell topLeftCell) {
		this.hexTable = hexTable;
		this.topLeftCell = topLeftCell;
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
	
	public void setTopLeftCell(HexCell topLeftCell) {
		this.topLeftCell = topLeftCell;
	}
	public HexCell getTopLeftCell() {
		return this.topLeftCell;
	}
	
	public void setEventListener(HexTableEventListener eventListener) {
		this.eventListener = eventListener;
	}
	
	public HexTableEventListener getEventListener() {
		return this.eventListener;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
