package ru.max.swfoc.ui;

import java.io.IOException;

import org.junit.Test;

import ru.ifmo.swfoc.editor.EditorCore;
import ru.ifmo.swfoc.editor.GameEntities;
import ru.ifmo.swfoc.io.Config;
import ru.max.swfoc.ui.Graphics;
import ru.max.swfoc.ui.Window;

public class UITest {

	 @Test
	 
	 public void run() throws IOException{
		 Config config = new Config("settings.properties");
		 EditorCore editorCore = new EditorCore(config);

	     GameEntities gameEntitiesStore = editorCore.getGameEntitiesStore();
		 Window win = new Window(new Graphics(gameEntitiesStore));
		 win.run();
	     
	 }
		
}
