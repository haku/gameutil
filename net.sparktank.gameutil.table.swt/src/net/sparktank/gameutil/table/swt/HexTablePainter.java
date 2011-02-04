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

package net.sparktank.gameutil.table.swt;

import java.util.Collection;

import net.sparktank.gameutil.table.hex.HexBearing;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

public class HexTablePainter implements PaintListener {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final double HEXPITCH = 0.866; // sqrt(0.75)
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTableConfig config;
	private final Canvas canvas;
	private final HexPiecePainter piecePainter;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexTablePainter (HexTableConfig config, Canvas canvas, HexPiecePainter piecePainter) {
		if (config == null) throw new IllegalArgumentException("config==null");
		if (canvas == null) throw new IllegalArgumentException("canvas==null");
		if (piecePainter == null) throw new IllegalArgumentException("piecePainter==null");
		
		this.config = config;
		this.canvas = canvas;
		this.piecePainter = piecePainter;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public void paintControl(final PaintEvent e) {
		final Rectangle clientArea = this.canvas.getClientArea();
		
		final int cellSize = this.config.getCellSize();
		final int halfCellSize = cellSize / 2;
		
		final FontData fontData = e.gc.getFont().getFontData()[0];
		final Font font = new Font(e.gc.getDevice(), fontData.getName(), cellSize / 4, fontData.getStyle());
		e.gc.setFont(font);
		
		final HexCoordinates firstCoord = this.config.getTopLeftCoordinates(); // The top-left most cell to draw.
		HexCoordinates rowStart = firstCoord; // The first cell of this row.
		HexCoordinates coord = rowStart; // The cell we are currently drawing.
		int rowNumber = 0; // The row we are drawing, where 0 is at the top of the screen.
		int leftIndent = 0; // How much to indent the current row (either 0 or HALFCELLSIZE).
		while (coord != null) {
			final int x = (coord.getX() - firstCoord.getX() + ((rowNumber / 2) * HexBearing.EAST.getDx())) * cellSize + leftIndent;
			final int y = (int) (((coord.getY() - firstCoord.getY()) * cellSize) * HEXPITCH);
			final Rectangle rect = new Rectangle(x, y, cellSize, cellSize);
			
			// Draw cell.
			e.gc.setForeground(e.gc.getDevice().getSystemColor(SWT.COLOR_DARK_GRAY));
			e.gc.drawOval(rect.x, rect.y, rect.width, rect.height);
			
			// Draw pieces?
			final Collection<? extends HexPiece> pieces = this.config.getHexTable().getHexPieces(coord);
			if (pieces != null && pieces.size() > 0) {
    			for (HexPiece piece : pieces) {
    				this.piecePainter.paintHexPiece(piece, e.gc, rect);
    			}
			}
			else {// Draw coordinate label only if cell is empty.
				final String s = coord.getX() + "," + coord.getY();
				drawTextHVCen(e.gc, rect.x + halfCellSize, rect.y + halfCellSize, s);
			}
			
			// Work out which cell we need to draw next.
			coord = this.config.getHexTable().getHexCoordinates(coord.getX() + HexBearing.EAST.getDx(), coord.getY() + HexBearing.EAST.getDy());
			if (coord == null || rect.x + rect.width > clientArea.width) {
				if (rect.y + rect.height > clientArea.height) break;
				
				final HexBearing bearing = rowNumber % 2 == 0 ? HexBearing.SOUTHEAST : HexBearing.SOUTHWEST;
				coord = this.config.getHexTable().getHexCoordinates(rowStart.getX() + bearing.getDx(), rowStart.getY() + bearing.getDy());
				rowStart = coord;
				rowNumber++;
				leftIndent = (rowNumber % 2) * halfCellSize;
			}
		}
		
		font.dispose();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
//	static private Rectangle drawTextHCen (PaintEvent e, int x, int top, String text) {
//		Point textSize = e.gc.textExtent(text);
//		int _left = x - (textSize.x / 2);
//		e.gc.drawText(text, _left, top, SWT.TRANSPARENT);
//		return new Rectangle(_left, top, textSize.x, textSize.y);
//	}
	
	static public Rectangle drawTextHVCen (GC gc, int x, int y, String... text) {
		Rectangle ret = new Rectangle(x, y, 0, 0);
		
		for (int i=0; i < text.length; i++) {
			Point textSize = gc.textExtent(text[i]);
			int _left = x - (textSize.x / 2);
			int _top = y + (textSize.y) * i - (textSize.y * text.length) / 2;
			
			gc.drawText(text[i], _left, _top, SWT.DRAW_TRANSPARENT);
			
			if (ret.x > _left) ret.x = _left;
			if (ret.y > _top) ret.y = _top;
			if (ret.width < textSize.x) ret.width = textSize.x;
			ret.height = ret.height + textSize.y;
		}
		
		return ret;
	}
	
	static public Rectangle drawTextHVCen (GC gc, int x, int y, String text) {
		String[] split = text.split("\n");
		return drawTextHVCen(gc, x, y, split);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
