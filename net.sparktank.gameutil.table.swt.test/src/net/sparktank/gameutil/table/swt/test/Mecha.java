package net.sparktank.gameutil.table.swt.test;

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table_impl.AbstractHexPiece;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import net.sparktank.gameutil.table.swt.HexTablePainter;

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
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString () {
		StringBuilder s = new StringBuilder();
		s.append("Mecha");
		s.append(getId());
		if (this.name != null) {
			s.append("/");
			s.append(this.name);
		}
		if (this.getHexCoordinates() != null) {
			s.append("/");
			s.append(this.getHexCoordinates());
		}
		return s.toString();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	HexPiecePainter methods.
	
	static public void paintHexPiece(HexPiece piece, GC gc, Rectangle rect) {
		Color c = gc.getBackground();
		gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_DARK_RED));
		gc.fillOval(rect.x + rect.width / 2 - 5, rect.y + rect.height / 2 - 5, 10, 10);
		gc.setBackground(c);
		
		HexTablePainter.drawTextHVCen(gc, rect.x + rect.width/2, rect.y + rect.height/2 - 5, piece.getName());
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
