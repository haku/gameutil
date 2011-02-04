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

import net.sparktank.gameutil.table.hex.HexCellAnnotation;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;
import net.sparktank.gameutil.table.swt.HexCellAnnotationPainter;
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
public class BasicHexGame implements HexPiecePainter, HexCellAnnotationPainter, HexTableEventListener {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static void main(String[] args) {
		BasicHexGame a = new BasicHexGame();
		a.run();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTableConfig config;
	private Canvas tableCanvas;
	
	public BasicHexGame () {
		// Create table.
		HexTableImpl table = new HexTableImpl(20, 20);
		this.config = new HexTableConfig(table, table.getHexCoordinates(0, 0));
		this.config.setEventListener(this);
		
		// Add test objects.
		this.config.getHexTable().addHexPiece(new Mecha(this.config.getHexTable().getHexCoordinates(1, 2), "Alpha"));
		this.config.getHexTable().addHexPiece(new Mecha(this.config.getHexTable().getHexCoordinates(9, 17), "Beta"));
	}
	
	private void run() {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setText ("HexTable Test");
		shell.setSize (800, 800);
		shell.setLayout(new FillLayout());
		
		// Setup GUI.
		this.tableCanvas = new Canvas(shell, SWT.NONE);
		HexTablePainter painter = new HexTablePainter(this.config, this.tableCanvas, this, this);
		HexTableMouseListener mouseListener = new HexTableMouseListener(this.config);
		this.tableCanvas.addPaintListener(painter);
		this.tableCanvas.addMouseListener(mouseListener);
		
		// Show shell.
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
	@Override
	public void paintHexCellAnnotation(HexCellAnnotation annotation, HexCoordinates coordinates, GC gc, Rectangle rect) {
		switch (annotation.getTypeId()) {
			case (SelectedMechaAnnotation.TYPEID):
				SelectedMechaAnnotation.paintHexCellAnnotation((SelectedMechaAnnotation) annotation, coordinates, gc, rect);
				break;
			
			default:
				System.err.println("No handler to draw annotation type " + annotation.getTypeId() + ".");
			
		}
	}
	
	@Override
	public void paintHexPiece(HexPiece piece, GC gc, Rectangle rect) {
		switch (piece.getTypeId()) {
			case (Mecha.TYPEID):
				Mecha.paintHexPiece(piece, gc, rect);
				break;
			
			default:
				Color c = gc.getBackground();
				gc.setBackground(gc.getDevice().getSystemColor(SWT.COLOR_RED));
				gc.fillOval(rect.x + rect.width / 2 - 5, rect.y + rect.height / 2 - 5, 10, 10);
				gc.setBackground(c);
				
		}
	}
	
	private HexPiece selectedPiece = null;
	private SelectedMechaAnnotation selectedPieceAnnotation = null;
	
	@Override
	public void cellClicked (HexCoordinates cell) {
		HexTable table = this.config.getHexTable();
		
		if (this.selectedPiece == null) {
			Collection<? extends HexPiece> pieces = table.getHexPieces(cell);
			if (pieces != null && pieces.size() > 0) {
				HexPiece piece = pieces.iterator().next();
				
				if (piece instanceof Mecha) {
					Mecha mecha = (Mecha) piece;
					
					this.selectedPiece = piece;
					
					this.selectedPieceAnnotation = new SelectedMechaAnnotation(cell, mecha);
					table.addHexCellAnnotation(this.selectedPieceAnnotation);
					
					this.tableCanvas.redraw();
					System.out.println("Selected mecha " + piece);
				}
				else {
					System.out.println("do not know how to select piece " + piece);
				}
			}
		}
		else {
			if (this.selectedPieceAnnotation.affectsCell(cell)) {
				table.moveHexPiece(this.selectedPiece, cell);
				System.out.println("placed piece " + this.selectedPiece);
			}
			
			table.removeHexCellAnnotation(this.selectedPieceAnnotation);
			this.tableCanvas.redraw();
			
			this.selectedPiece = null;
			this.selectedPieceAnnotation = null;
			
		}
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
