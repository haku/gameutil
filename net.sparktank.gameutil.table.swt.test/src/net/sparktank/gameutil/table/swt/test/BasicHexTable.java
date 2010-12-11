package net.sparktank.gameutil.table.swt.test;

import net.sparktank.gameutil.table.hex.HexTable;
import net.sparktank.gameutil.table.swt.HexTablePainter;
import net.sparktank.gameutil.table_impl.HexTableImpl;

import org.eclipse.swt.SWT;
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
		
		HexTable table = new HexTableImpl(20, 20);
		
		Canvas canvas = new Canvas(shell, SWT.NONE);
		HexTablePainter painter = new HexTablePainter(canvas, table);
		canvas.addPaintListener(painter);
		
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
//	- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
}
