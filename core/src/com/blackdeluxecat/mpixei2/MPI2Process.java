package com.blackdeluxecat.mpixei2;

import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class MPI2Process {

	public MPI2Process() {
	}

	public static final Pixmap getColorMap(Pixmap pic) {
		HashMap<Integer, int[]> intMap = new HashMap<Integer, int[]>();
		int pointer = 0;
		boolean has;
		int[] rgba;
		for(int x = 0; x < pic.getWidth(); x++) {
			Gdx.app.log("", x + " " + intMap.size());
			for(int y = 0; y < pic.getHeight(); y++) {
				
				has = false;
				rgba = decodeRGBA8888(pic.getPixel(x, y));
				
				for(Integer key : intMap.keySet()){
					if(Arrays.equals(intMap.get(key), rgba)) {
						has = true;
						break;
					}
				}
				
				if(!has) {
					intMap.put(pointer, decodeRGBA8888(pic.getPixel(x, y)));
					pointer++;
				}
				
			}
		}
		
		Pixmap colors = new Pixmap(Math.max(1,intMap.size()) / 16 + 8, Math.max(1,intMap.size()), Format.RGBA8888);
		for(Integer key : intMap.keySet()){
			colors.setColor(getIntRGBA8888(intMap.get(key)));
			colors.drawRectangle(key / 128, key.intValue(), 5,1);
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
