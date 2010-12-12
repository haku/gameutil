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

import net.sparktank.gameutil.table.hex.HexBearing;
import net.sparktank.gameutil.table.hex.HexCell;
import net.sparktank.gameutil.table.hex.HexCoordinates;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

public class HexTablePainter implements PaintListener {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static final double HEXPITCH = 0.866; // sqrt(0.75)
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTableConfig config;
	private final Canvas canvas;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public HexTablePainter (HexTableConfig config, Canvas canvas) {
		this.config = config;
		this.canvas = canvas;
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public void paintControl(PaintEvent e) {
		Rectangle clientArea = this.canvas.getClientArea();
		
		int cellSize = this.config.getCellSize();
		int halfCellSize = cellSize / 2;
		
		FontData fontData = e.gc.getFont().getFontData()[0];
		Font font = new Font(e.gc.getDevice(), fontData.getName(), cellSize / 4, fontData.getStyle());
		e.gc.setFont(font);
		
		HexCell firstCell = this.config.getTopLeftCell(); // The top-left most cell to draw.
		HexCell rowStart = firstCell; // The first cell of this row.
		HexCell cell = rowStart; // The cell we are currently drawing.
		int rowNumber = 0; // The row we are drawing, where 0 is at the top of the screen.
		int leftIndent = 0; // How much to indent the current row (either 0 or HALFCELLSIZE).
		while (true) {
			HexCoordinates coord = cell.getHexCoordinates();
			int x = (coord.getX() - firstCell.getCoordinates().getX() + ((rowNumber / 2) * HexBearing.EAST.getDx())) * cellSize + leftIndent;
			int y = (int) (((coord.getY() - firstCell.getCoordinates().getY()) * cellSize) * HEXPITCH);
			Rectangle rect = new Rectangle(x, y, cellSize, cellSize);
			
			String s = coord.getX() + "," + coord.getY();
			drawTextHVCen(e, rect.x + halfCellSize, rect.y + halfCellSize, s);
			e.gc.drawOval(rect.x, rect.y, cellSize, cellSize);
			
			cell = this.config.getHexTable().getHexCell(coord.getX() + HexBearing.EAST.getDx(), coord.getY() + HexBearing.EAST.getDy());
			if (cell == null || rect.x + rect.width > clientArea.width) {
				if (rect.y + rect.height > clientArea.height) break;
				
				HexBearing bearing = rowNumber % 2 == 0 ? HexBearing.SOUTHEAST : HexBearing.SOUTHWEST;
				cell = this.config.getHexTable().getHexCell(rowStart.getCoordinates().getX() + bearing.getDx(),
						rowStart.getCoordinates().getY() + bearing.getDy());
				rowStart = cell;
				rowNumber++;
				leftIndent = (rowNumber % 2) * halfCellSize;
			}
			if (cell == null) break;
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
	
	static private Rectangle drawTextHVCen (PaintEvent e, int x, int y, String... text) {
		Rectangle ret = new Rectangle(x, y, 0, 0);
		
		for (int i=0; i < text.length; i++) {
			Point textSize = e.gc.textExtent(text[i]);
			int _left = x - (textSize.x / 2);
			int _top = y + (textSize.y) * i - (textSize.y * text.length) / 2;
			
			e.gc.drawText(text[i], _left, _top, SWT.TRANSPARENT);
			
			if (ret.x > _left) ret.x = _left;
			if (ret.y > _top) ret.y = _top;
			if (ret.width < textSize.x) ret.width = textSize.x;
			ret.height = ret.height + textSize.y;
		}
		
		return ret;
	}
	
	static private Rectangle drawTextHVCen (PaintEvent e, int x, int y, String text) {
		String[] split = text.split("\n");
		return drawTextHVCen(e, x, y, split);
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
