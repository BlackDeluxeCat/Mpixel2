package com.blackdeluxecat.mpixei2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blackdeluxecat.mpixei2.MPixeI2;
import com.blackdeluxecat.mpixei2.Vars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MPixel2";
		config.width = (int) Vars.WIN_WIDTH;
		config.height = (int) Vars.WIN_HEIGHT;
		new LwjglApplication(new MPixeI2(), config);
	}
}
