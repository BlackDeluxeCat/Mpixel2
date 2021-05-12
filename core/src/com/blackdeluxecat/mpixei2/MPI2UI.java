package com.blackdeluxecat.mpixei2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class MPI2UI {
	public MPI2Process process;
	
	public Texture bgTexture;
	public Texture cursorTexture;
	public Texture buttonUp;
	public Texture buttonDown;
	public Texture buttonChecked;
	
	public TextFieldStyle textStyle;
	public LabelStyle labelStyle;
	public TextButtonStyle textButtonStyle;
	
	public BitmapFont bitmapFont;
	
	public Table rootTable;
	public Table fileTable;
	public Table imageTable;
	public Table buttonTable;
	
	private TextField dirTextField;
	private Label dirCheckLabel, colorMapLabel;
	private TextButton dirCheckButton, processButton;
	
	private Image originImage, colorfiedImage, colorMapImage;
	public Pixmap origin, colorfied, colorMap;
	private Texture originTexture, colorfiedTexture, colorMapTexture;
	
	public MPI2UI() {
		init();
	}
	
	public void init() {
		bgTexture = MPI2UI.standardFieldTexture();
		cursorTexture = MPI2UI.cursorTypingTexture();
		buttonUp = MPI2UI.pureFieldTexture(new Color(1f,1f,1f,1f), Vars.BUTTON_WIDTH, Vars.BUTTON_HEIGHT);
		buttonDown = MPI2UI.pureFieldTexture(new Color(0.5f,0.5f,0.5f,1f), Vars.BUTTON_WIDTH, Vars.BUTTON_HEIGHT);
		buttonChecked = MPI2UI.pureFieldTexture(new Color(0.5f,0.7f,0.5f,1f), Vars.BUTTON_WIDTH, Vars.BUTTON_HEIGHT);
		
		
		bitmapFont = new BitmapFont(Gdx.files.internal("fonts/zh_cn_16.fnt"));
		bitmapFont.getData().setScale(1f);
		
		textStyle = new TextFieldStyle();
		textStyle.font = bitmapFont;
		textStyle.fontColor = new Color(0.6f, 0.5f, 1f, 1f);
		textStyle.selection = new TextureRegionDrawable(buttonChecked);
		textStyle.background = new TextureRegionDrawable(buttonDown); 
		textStyle.cursor = new TextureRegionDrawable(cursorTexture);
		
		labelStyle = new LabelStyle();
		labelStyle.font = bitmapFont;
		labelStyle.fontColor = new Color(1f, 1f, 1f, 1f);
		labelStyle.background = new TextureRegionDrawable(bgTexture);
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = bitmapFont;
		textButtonStyle.fontColor = new Color(1f,1f,1f,1f);
		textButtonStyle.up = new TextureRegionDrawable(buttonUp);
		textButtonStyle.down = new TextureRegionDrawable(buttonDown);
		textButtonStyle.checked = new TextureRegionDrawable(buttonChecked);
		
		origin = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		colorfied = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		colorMap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		
		originTexture = new Texture(1,1, Format.RGBA8888);
		colorfiedTexture = new Texture(1,1,Format.RGBA8888);
		colorMapTexture = new Texture(1,1,Format.RGBA8888);
	}
	
	public void tableSetup(Stage stage) {
		dirTextField = new TextField(Vars.INIT_PATH, textStyle);
		dirTextField.setSize(Vars.TEXT_FIELD_WIDTH, Vars.TEXT_FIELD_HEIGHT);
		
		dirCheckLabel = new Label("[当前]", labelStyle);
		dirCheckLabel.setSize(Vars.TEXT_FIELD_WIDTH, Vars.TEXT_FIELD_HEIGHT);
		
		dirCheckButton = new TextButton("Import", textButtonStyle);
		dirCheckButton.setSize(Vars.BUTTON_WIDTH, Vars.BUTTON_HEIGHT);
		dirCheckButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				FileHandle file = Gdx.files.absolute(dirTextField.getText());
				Gdx.app.log("", file.path() + "");
				dirCheckLabel.setText("[当前] " + ((file.exists())? dirTextField.getText():"(not a vaild file)"));
				if(!file.exists()) return;
				try {
					origin.dispose();
					origin = new Pixmap(file);
				} catch(Exception e) {
					Gdx.app.log("ERROR", "", e);
					origin = null;
					return;
				}
				//originTexture.dispose();
				originTexture = new Texture(origin);
			}
		});
		
		processButton = new TextButton("Go", textButtonStyle);
		processButton.setSize(Vars.BUTTON_WIDTH, Vars.BUTTON_HEIGHT);
		processButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				colorMap.dispose();
				colorMap = MPI2Process.getColorMap(origin);
				colorMapTexture.dispose();
				colorMapTexture = new Texture(colorMap);
				
				//colorfied.dispose();
				colorfied = MPI2Process.colorfy(origin);
				Gdx.app.log("", colorfied.isDisposed() + "");
				colorfiedTexture.dispose();
				colorfiedTexture = new Texture(colorfied);
			}
		});
		
		colorMapLabel = new Label("COLORMAP", labelStyle);

		originImage = new Image();
		colorfiedImage = new Image();
		colorMapImage = new Image();
		
		
		
		
		rootTable = new Table();
		fileTable = new Table();
		imageTable = new Table();
		buttonTable = new Table();
		
		buttonTable.add(dirCheckButton).pad(10f);
		buttonTable.add(processButton).pad(10f);
		
		fileTable.add(dirCheckLabel).left().minWidth(800f);
		fileTable.row();
		fileTable.add(dirTextField).left().minWidth(800f);
		fileTable.row();
		fileTable.add(buttonTable).left();
		fileTable.top().left();
		
		imageTable.add(new Label("ORIGIN", labelStyle));
		imageTable.add(new Label("PROCESSED", labelStyle));
		imageTable.add(colorMapLabel);
		imageTable.row();
		imageTable.add(originImage).minHeight(400f).pad(3f).getActor().setScaling(Scaling.fit);
		imageTable.add(colorfiedImage).pad(3f).getActor().setScaling(Scaling.fit);
		imageTable.add(colorMapImage).pad(3f).center().grow();
		
		rootTable.defaults();
		rootTable.add(fileTable);
		rootTable.row();
		rootTable.add(imageTable);
		
		rootTable.setFillParent(true);
		stage.addActor(rootTable);
	}

	public void update() {
		originImage.setDrawable(new TextureRegionDrawable(originTexture));
		colorfiedImage.setDrawable(new TextureRegionDrawable(colorfiedTexture));
		colorMapImage.setDrawable(new TextureRegionDrawable(colorMapTexture));
		
		colorMapLabel.setText("COLORS: " + MPI2Process.colorCounts);
	}
	
	//called when dispose
	public void dispose() {
		bgTexture.dispose();
		cursorTexture.dispose();
		buttonUp.dispose();
		buttonDown.dispose();
		buttonChecked.dispose();
		bitmapFont.dispose();
		
		origin.dispose();
		colorfied.dispose();
		colorMap.dispose();
		
		originTexture.dispose();
		colorfiedTexture.dispose();
		colorMapTexture.dispose();
	}
	
	public static Texture standardFieldTexture() {
		Pixmap pixmap = new Pixmap(Vars.TEXT_FIELD_WIDTH, Vars.TEXT_FIELD_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(0.5f, 0.5f, 0.5f, 1f);
		pixmap.drawRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
		Texture ret = new Texture(pixmap);
		pixmap.dispose();
		return ret;
	}
	
	public static Texture cursorTypingTexture() {
		Pixmap pixmap = new Pixmap(2, 20, Pixmap.Format.RGBA8888);
		pixmap.setColor(1f, 0f, 1f, 1f);
		pixmap.fill();
		Texture tex = new Texture(pixmap);
		pixmap.dispose();
		return tex;
	}
	
	public static Texture pureFieldTexture(Color color, int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.drawRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
		Texture ret = new Texture(pixmap);
		pixmap.dispose();
		return ret;
	}
}
