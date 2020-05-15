package ru.max.swfoc.ui.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class UIMenu {
	
	public UIMenu(Shell shell){
		 Menu menuBar = new Menu(shell, SWT.BAR);
		  
		  Menu fileMenu = new Menu(menuBar);
		  MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		   fileItem.setText("File");
		   fileItem.setMenu(fileMenu);
		   MenuItem newItem = new MenuItem(fileMenu, SWT.NONE);
		   newItem.setText("New");
		   MenuItem openItem = new MenuItem(fileMenu, SWT.NONE);
		   openItem.setText("Open...");
		   MenuItem saveItem = new MenuItem(fileMenu, SWT.NONE);
		   saveItem.setText("Save");
		   MenuItem exitItem = new MenuItem(fileMenu, SWT.NONE);
		   exitItem.setText("Exit\tCtrl+Q");
		   exitItem.setAccelerator (SWT.MOD1 + 'Q');
		   exitItem.addListener (SWT.Selection, new Listener () {
			    @Override
			    public void handleEvent (Event e) {
			        System.exit(0);
			    }
			});
		   
		   
		   Menu languageMenu = new Menu(menuBar);
		   MenuItem languageItem = new MenuItem(menuBar, SWT.CASCADE);
		   languageItem.setText("Language");
		   languageItem.setMenu(languageMenu);
		   MenuItem rusLangItem = new MenuItem(languageMenu, SWT.NONE);
		   rusLangItem.setText("�������");
		   MenuItem engLangItem = new MenuItem(languageMenu, SWT.NONE);
		   engLangItem.setText("English");
		   
		   
		   Menu runSWFOCMenu = new Menu(menuBar);
		   MenuItem runSWFOCItem = new MenuItem(menuBar, SWT.CASCADE);
		   runSWFOCItem.setText("Run");
		   runSWFOCItem.setMenu(runSWFOCMenu);
		   
		   
		   Menu helpMenu = new Menu(menuBar);
		   MenuItem helpItem = new MenuItem(menuBar, SWT.CASCADE);
		   helpItem.setText("Help");
		   helpItem.setMenu(helpMenu);
		   MenuItem aboutItem = new MenuItem(helpMenu, SWT.NONE);
		   aboutItem.setText("About program");
		   MenuItem manualItem = new MenuItem(helpMenu, SWT.NONE);
		   manualItem.setText("Manual");
		   shell.setMenuBar(menuBar);
	}
}
