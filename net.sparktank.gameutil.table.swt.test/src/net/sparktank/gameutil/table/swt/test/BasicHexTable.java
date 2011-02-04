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

package net.sparktank.gameutil.table.swt.test;

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;
import net.sparktank.gameutil.table.swt.HexPiecePainter;
import net.sparktank.gameutil.table.swt.HexTableConfig;
import net.sparktank.gameutil.table.swt.HexTableEventListener;
import net.sparktank.gameutil.table.swt.HexTableMouseListener;
import net.sparktank.gameutil.table.swt.HexTablePainter;
import net.sparktank.gameutil.table_impl.HexTableImpl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A simple app to test drawing a HexTable implementation using HexTablePainter.
 *
 * @author haku
 */
public class BasicHexTable {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setText ("HexTable Test");
		shell.setSize (800, 800);
		shell.setLayout(new FillLayout());
		
		// Create table.
		HexTable table = new HexTableImpl(20, 20);
		HexTableConfig config = new HexTableConfig(table, table.getHexCoordinates(0, 0));
		config.setEventListener(eventListener);
		
		// Setup GUI.
		Canvas canvas = new Canvas(shell, SWT.NONE);
		HexTablePainter painter = new HexTablePainter(config, canvas, piecePainter);
		HexTableMouseListener mouseListener = new HexTableMouseListener(config);
		canvas.addPaintListener(painter);
		canvas.addMouseListener(mouseListener);
		
		// Add test objects.
		Mecha mecha = new Mecha();
		mecha.setHexCoordinates(table.getHexCoordinates(3, 3));
		table.addHexPiece(mecha);
		
		Mecha mecha2 = new Mecha();
		mecha2.setHexCoordinates(table.getHexCoordinates(2, 5));
		table.addHexPiece(mecha2);
		
		// Show shell.
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private static HexPiecePainter piecePainter = new HexPiecePainter() {
		@Override
		public void paintHexPiece(HexPiece piece, GC gc, Rectangle rect) {
			switch (piece.getId()) {
				case (Mecha.ID):
					Mecha.paintHexPiece(piece, gc, rect);
					break;
				
				default:
					Color c = gc.getBackground();
					gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
					gc.fillOval(rect.x + rect.width / 2 - 5, rect.y + rect.height / 2 - 5, 10, 10);
					gc.setBackground(c);
						
			}
		}
	};
	
	private static HexTableEventListener eventListener = new HexTableEventListener() {
		
		@Override
		public void cellClicked(HexCoordinates cell) {
			System.out.println("cellClicked=" + cell);
		}
		
	};
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
