package com.blackdeluxecat.mpixei2;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class MPI2Process {

	public MPI2Process() {
	}

	public static final Pixmap getColorMap(Pixmap pic) {
		HashMap<Integer, Integer> intMap = new HashMap<Integer, Integer>();

		int intrgba;
		for(int x = 0; x < pic.getWidth(); x++) {
			Gdx.app.log("", x + " " + intMap.size());
			for(int y = 0; y < pic.getHeight(); y++) {
				
				intrgba = pic.getPixel(x, y);
				if(intMap.containsKey(intrgba)) {
					intMap.put(intrgba, intMap.get(intrgba) + 1);
				}else {
					intMap.put(intrgba, 1);
				}
				
			}
		}
		
		Pixmap colors = new Pixmap(Math.max(1,intMap.size()) / 128 * 8 + 8, 128 * 8, Format.RGBA8888);
		int pointer = 0;
		for(Integer key : intMap.keySet()){
			colors.setColor(key);
			colors.fillRectangle(pointer / 128 * 8, Math.floorMod(pointer, 128) * 8, 5,5);
			pointer++;
		}
		return colors;
	}
	
	public static final Pixmap colorfy(Pixmap pic) {
		int pixel = pic.getPixel(763, 974);
		int[] rgba = decodeRGBA8888(pixel);
		Gdx.app.log("", rgba[0] + "" + rgba[1] + "" + rgba[2] + "" + rgba[3]);
		
		return pic;
	}
	
	public static final int[] decodeRGBA8888(int rgba) {
		int[] rgbad = new int[4];
		rgbad[3] = rgba & 255;
		rgbad[2] = rgba >> 8 & 255;
		rgbad[1] = rgba >> 16 & 255;
		rgbad[0] = rgba >> 24 & 255;
		return rgbad;
	}
	
	public static final int getIntRGBA8888(int[] rgba) {
		return ((rgba[0]<<24) | (rgba[1]<<16) | (rgba[2]<<8) | rgba[3]);
	}
}
