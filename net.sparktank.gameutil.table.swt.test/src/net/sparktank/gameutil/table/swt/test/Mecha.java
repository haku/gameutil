package net.sparktank.gameutil.table.swt.test;

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table_impl.AbstractHexPiece;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

public class Mecha extends AbstractHexPiece {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final int ID = 100;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final String name;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public Mecha (HexCoordinates coords, String name) {
		super(coords);
		this.name = name;
	}
	
	public Mecha (HexCoordinates coords) {
		super(coords);
		this.name = null;
	}
	
	public Mecha () {
		super();
		this.name = null;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public int getId() {
		return ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString () {
		if (this.name != null) return "Mecha" + getId() + "/" + this.name;
		return "Mecha" + getId();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexPiecePainter methods.
	
	static public void paintHexPiece(HexPiece piece, GC gc, Rectangle rect) {
		Color c = gc.getBackground();
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_YELLOW));
		gc.fillOval(rect.x + rect.width / 2 - 5, rect.y + rect.height / 2 - 5, 10, 10);
		gc.setBackground(c);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
