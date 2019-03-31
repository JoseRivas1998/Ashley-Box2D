package com.tcg.ashbox.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class KeyboardController implements InputProcessor {
    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
    public boolean isMouse1Down;
    public boolean isMouse2Down;
    public boolean isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2();

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = true;
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = true;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                up = true;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down = true;
                break;
            default:
                keyProcessed = false;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = true;
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                right = false;
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                up = false;
                break;
            case Input.Keys.S:
            case Input.Keys.DOWN:
                down = false;
                break;
            default:
                keyProcessed = false;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0) {
            isMouse1Down = true;
        } else if(button == 1) {
            isMouse2Down = true;
        } else if(button == 2) {
            isMouse3Down = true;
        }
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        if(button == 0) {
            isMouse1Down = false;
        } else if(button == 1) {
            isMouse2Down = false;
        } else if(button == 2) {
            isMouse3Down = false;
        }
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
