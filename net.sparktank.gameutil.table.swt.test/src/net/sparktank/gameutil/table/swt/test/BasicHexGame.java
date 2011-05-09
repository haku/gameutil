/*
 * Copyright 2010 2011 Fae Hutter
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
import java.util.Random;

import net.sparktank.gameutil.table.hex.HexCellAnnotation;
import net.sparktank.gameutil.table.hex.HexCoordinates;
import net.sparktank.gameutil.table.hex.HexPiece;
import net.sparktank.gameutil.table.hex.HexTable;
import net.sparktank.gameutil.table.swt.HexCellAnnotationPainter;
import net.sparktank.gameutil.table.swt.HexPiecePainter;
import net.sparktank.gameutil.table.swt.HexTableCanvas;
import net.sparktank.gameutil.table.swt.HexTableEventListener;
import net.sparktank.gameutil.table_impl.HexTableImpl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * A simple app to test drawing a HexTable implementation using HexTablePainter.
 *
 * @author haku
 */
public class BasicHexGame implements HexPiecePainter, HexCellAnnotationPainter, HexTableEventListener {
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private static final int TABLE_HEIGHT = 20;
	private static final int TABLE_WIDTH = 20;
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	public static void main(String[] args) {
		BasicHexGame a = new BasicHexGame();
		a.run();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private final HexTable table;
	private HexTableCanvas tableCanvas;
	DebrisFieldAnnotation debrisField;
	
	public BasicHexGame () {
		// Create table.
		this.table = new HexTableImpl(TABLE_WIDTH, TABLE_HEIGHT);
		
		Random random = new Random(System.currentTimeMillis());
		
		// Add test objects.
		this.table.addHexPiece(new Mecha(this.table.getRandomHexCoordinates(random), "Alpha"));
		this.table.addHexPiece(new Mecha(this.table.getRandomHexCoordinates(random), "Beta"));
		this.table.addHexPiece(new Mecha(this.table.getRandomHexCoordinates(random), "Gamma"));
		
		this.debrisField = new DebrisFieldAnnotation(this.table, random, 40);
		this.table.addHexCellAnnotation(this.debrisField);
	}
	
	private void run() {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setText ("Basic hexgrid game");
		shell.setSize (800, 700);
		shell.setMaximized(true);
		shell.setLayout(new FillLayout());
		
		// Setup HexTableCanvas control.
		this.tableCanvas = new HexTableCanvas(shell);
		this.tableCanvas.setHexTable(this.table, this.table.getHexCoordinates(0, 0));
		this.tableCanvas.setBackground(this.tableCanvas.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		this.tableCanvas.setPiecePainter(this);
		this.tableCanvas.setCellAnnotationPainter(this);
		this.tableCanvas.setDrawGrid(true);
		this.tableCanvas.setGridColour(this.tableCanvas.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
		this.tableCanvas.setEventListener(this);
		
		// Show shell.
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	@Override
	public void paintHexCellAnnotation(HexCellAnnotation annotation, HexCoordinates coordinates, GC gc, Rectangle rect) {
		switch (annotation.getTypeId()) {
			case (MechaThrustAnnotation.TYPEID):
				MechaThrustAnnotation.paintHexCellAnnotation((MechaThrustAnnotation) annotation, coordinates, gc, rect);
				break;
			
			case (DebrisFieldAnnotation.TYPEID):
				DebrisFieldAnnotation.paintHexCellAnnotation((DebrisFieldAnnotation) annotation, coordinates, gc, rect);
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
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	
	private HexPiece selectedPiece = null;
	private MechaThrustAnnotation selectedPieceAnnotation = null;
	
	@Override
	public void cellClicked (HexCoordinates cell) {
		if (cell != null && this.selectedPiece == null) {
			Collection<? extends HexPiece> pieces = this.table.getHexPieces(cell);
			if (pieces != null && pieces.size() > 0) {
				HexPiece piece = pieces.iterator().next();
				if (piece instanceof Mecha) {
					Mecha mecha = (Mecha) piece;
					
					this.selectedPiece = piece;
					
					this.selectedPieceAnnotation = new MechaThrustAnnotation(cell, mecha, this.debrisField.getAffectedCells());
					this.table.addHexCellAnnotation(this.selectedPieceAnnotation);
					
					this.tableCanvas.redraw();
					System.out.println("Selected mecha " + piece);
				}
				else {
					System.out.println("Do not know how to select piece " + piece);
				}
			}
		}
		else if (this.selectedPiece != null) {
			if (cell != null && this.selectedPieceAnnotation.affectsCell(cell) && !this.debrisField.affectsCell(cell)) {
				this.table.moveHexPiece(this.selectedPiece, cell);
				System.out.println("placed piece " + this.selectedPiece);
			}
			
			this.table.removeHexCellAnnotation(this.selectedPieceAnnotation);
			this.tableCanvas.redraw();
			
			this.selectedPiece = null;
			this.selectedPieceAnnotation = null;
		}
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
