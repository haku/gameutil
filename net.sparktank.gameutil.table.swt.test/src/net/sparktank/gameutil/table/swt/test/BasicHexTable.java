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

import java.util.Collection;

import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
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
public class BasicHexTable implements HexPiecePainter, HexTableEventListener {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static void main(String[] args) {
		BasicHexTable a = new BasicHexTable();
		a.run();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTableConfig config;
	
	public BasicHexTable () {
		// Create table.
		HexTableImpl table = new HexTableImpl(20, 20);
		this.config = new HexTableConfig(table, table.getHexCoordinates(0, 0));
		this.config.setEventListener(this);
		
		// Add test objects.
		this.config.getHexTable().addHexPiece(new Mecha(this.config.getHexTable().getHexCoordinates(3, 3), "Alpha"));
		this.config.getHexTable().addHexPiece(new Mecha(this.config.getHexTable().getHexCoordinates(2, 5), "Beta"));
	}
	
	private void run() {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setText ("HexTable Test");
		shell.setSize (800, 800);
		shell.setLayout(new FillLayout());
		
		// Setup GUI.
		Canvas canvas = new Canvas(shell, SWT.NONE);
		HexTablePainter painter = new HexTablePainter(this.config, canvas, this);
		HexTableMouseListener mouseListener = new HexTableMouseListener(this.config);
		canvas.addPaintListener(painter);
		canvas.addMouseListener(mouseListener);
		
		// Show shell.
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
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
	
	@Override
	public void cellClicked (HexCoordinates cell) {
		Collection<? extends HexPiece> pieces = this.config.getHexTable().getHexPieces(cell);
		if (pieces != null && pieces.size() > 0) {
			System.out.println ("piece=" + pieces.toArray()[0]);
		}
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
