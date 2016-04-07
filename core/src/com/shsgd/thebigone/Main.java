package com.shsgd.thebigone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
	public static final String title = "The Gudone";
	public static final int V_WIDTH=600, V_HEIGHT=600; //Virtual height and width


	@Override
	public void create () {
		setScreen(new PlayScreen(this));
	}


	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render () {

		super.render();
	}
}
