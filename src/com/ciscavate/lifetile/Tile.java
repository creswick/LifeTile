package com.ciscavate.lifetile;

import android.graphics.Rect;

public class Tile {
	private Rect _camView;
	private Rect _screenLoc;
	private int _num;
	
	private final boolean _isBlank;
	
	public Tile(int num, Rect camView, Rect screenLoc, boolean isBlank) {
		_camView = camView;
		_screenLoc = screenLoc;
		_num = num;
		_isBlank = isBlank;
	}
	
	public boolean isBlank() {
		return _isBlank;
	}
	
	/**
	 * Accessor for the subset of the device camera that this tile is showing.
	 * 
	 * @return
	 */
	public Rect getCamView() {
		return _camView;
	}

	/**
	 * Accessor for the on-screen location to display this tile.
	 * 
	 * @return
	 */
	public Rect getScreenLoc() {
		return _screenLoc;
	}

	public int getNum() {
		return _num;
	}
}
