package com.shsgd.thebigone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by ryananderson on 4/6/16.
 */
public class MyInputProcessor implements InputProcessor {
    //An input processor specifically made to interact with PlayScreen
    private PlayScreen playScreen;
    public MyInputProcessor(PlayScreen playScreen){
        Gdx.input.setInputProcessor(this);
        this.playScreen = playScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.DOWN) playScreen.moveKeyDown(0);
        else if(keycode == Input.Keys.RIGHT) playScreen.moveKeyDown(1);
        else if(keycode == Input.Keys.UP) playScreen.moveKeyDown(2);
        else if(keycode == Input.Keys.LEFT) playScreen.moveKeyDown(3);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.DOWN) playScreen.moveKeyUp(0);
        else if(keycode == Input.Keys.RIGHT) playScreen.moveKeyUp(1);
        else if(keycode == Input.Keys.UP) playScreen.moveKeyUp(2);
        else if(keycode == Input.Keys.LEFT) playScreen.moveKeyUp(3);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
