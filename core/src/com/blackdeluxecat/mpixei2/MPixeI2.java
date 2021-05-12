package com.blackdeluxecat.mpixei2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MPixeI2 extends ApplicationAdapter {
	public Stage stage;
	public Viewport viewport;
	public MPI2UI ui;

	
	
	
	@Override
	public void create () {
		viewport = new FitViewport(Vars.WIN_WIDTH, Vars.WIN_HEIGHT);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		ui = new MPI2UI();

		ui.tableSetup(stage);
		
	}
	

	

	@Override
	public void render() {
		ScreenUtils.clear(0.1f, 0.1f, 0.08f, 1);
		ui.update();
		stage.act();
		stage.draw();
		
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.setWorldSize(width, height);
		//rebuildTables();
		viewport.update(width, height,true);
		
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void dispose () {
		stage.dispose();
		ui.dispose();
	}
}
