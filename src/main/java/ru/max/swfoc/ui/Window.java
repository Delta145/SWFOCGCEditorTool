package ru.max.swfoc.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Window {
	private Display display;
	private Shell shell;
	
	public Window(IGraphics graphics) {
		display = new Display();
		shell = new Shell(display);
		shell.setText("SWT MUT");
		shell.setMaximized(true);
		graphics.launchUI(display, shell);
	}
	
	public void run() {
		
		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	
}
