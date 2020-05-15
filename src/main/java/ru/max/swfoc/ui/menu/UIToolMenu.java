package ru.max.swfoc.ui.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class UIToolMenu {
	public UIToolMenu(Shell shell) {
		ToolBar toolBar = new ToolBar(shell, SWT.BORDER | SWT.HORIZONTAL);
		GridData grtool = new GridData();
		grtool.horizontalAlignment = SWT.FILL;
		toolBar.setLayoutData(grtool);
	    ToolItem newItem = new ToolItem(toolBar, SWT.DROP_DOWN);
	    newItem.setText("New");
	    ToolItem openItem = new ToolItem(toolBar, SWT.PUSH);
	    openItem.setText("Open");
	    ToolItem saveItem = new ToolItem(toolBar, SWT.PUSH);
	    saveItem.setText("Save");
	    ToolItem runItem = new ToolItem(toolBar, SWT.PUSH);
	    runItem.setText("Run");
	    

	    toolBar.setBackground(new Color(shell.getDisplay(), 255, 191, 0));
	}
}
