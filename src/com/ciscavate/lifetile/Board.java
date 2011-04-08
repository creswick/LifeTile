package com.ciscavate.lifetile;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;

public class Board {

	private int _rows;
	private int _cols;
	
	private List<Tile> _tiles;
	
	public Board(int cols, int rows) {
		super();
		this._cols = cols;
		this._rows = rows;
		
		_tiles = buildBoard();
		randomize();
	}

	private List<Tile> buildBoard() {
		List<Tile> tiles = new LinkedList<Tile>();
		
		int num = 0;
		boolean isBlank = false;
		for (int i=0; i < _cols; i++){
			for (int j=0; j < _rows; j++) {
				if (num + 1 == _rows * _cols) {
					isBlank = true;
				}
				tiles.add(new Tile(num, null, null, isBlank));
				num++;
			}
		}
		Log.d(LT.TAG, "created tiles: "+tiles.size());

		return tiles;
	}

	private void randomize() {
		int blankIdx = _tiles.size() - 1;
		int iterations = 10 * _tiles.size();
		
		for (int i = 0; i < iterations ; i++) {
			int direction = (int) (Math.random() * 4);
			int next = -1;
			switch (direction) {
			case 0: // left
				next = getLeft(blankIdx);
				break;
			case 1: // up
				next = getAbove(blankIdx);
				break;
			case 2: // right
				next = getRight(blankIdx);
				break;
			case 3: // below
				//next = getBelow(blankIdx);
				break;
			default:
				Log.e(LT.TAG, "Unexpected direction: " + direction
						+ " in randomize!");
				next = -1;
				break;
			}

			if (-1 != next) {
				// swap tiles:
				swap(next);
				blankIdx = next;
			}
		}
	}

	/**
	 * See if tile i is next to the blank, and if so, return the index of the blank, otherwise return -1.
	 * 
	 * @param i
	 */
	public int swapIdx(int i) {
		int left = getLeft(i);
		int above = getAbove(i);
		int right = getRight(i);
		int below = getBelow(i);

		if (checkIdx(left)) {
			return left;
		} else if (checkIdx(above)){
			return above;
		} else if (checkIdx(right)){
			return right;
		} else if (checkIdx(below)){
			return below;
		}
		
		return -1;
	}

	private boolean checkIdx(int idx) {
		return idx != -1 && _tiles.get(idx).isBlank();
	}
	
	private int getLeft(int i) {
		// if we're in the first col, return null
		if (i % _cols == 0) {
			return -1;
		}
		return (i - 1);
	}

	private int getRight(int i) {
		// if we're in the last column, return null.
		if (i % _cols == _cols - 1) {
			return -1;
		}
		return (i + 1);
	}

	private int getAbove(int i) {
		// if we're in the first row, return null.
		if (i < _cols) {
			return -1;
		}
		return (i - _cols);
	}

	private int getBelow(int i) {
		// if we're in the last row, return null.
		if (i > ((_rows - 1)  * _cols)) {
			return -1;
		}
		return (i + _cols);
	}
	
	/**
	 * If possible, swap tiles at indexes i and j.
	 * 
	 * @param i
	 * @param j
	 */
	public void swap(int i) {
		int blankIdx = swapIdx(i);
		
		if (-1 != blankIdx) {
			doSwap(i, blankIdx);
		}
	}
	
	private void doSwap(int i, int j) {
		Tile tmp = _tiles.get(i);
		_tiles.set(i, _tiles.get(j));
		_tiles.set(j, tmp);
	}
	
	public List<Tile> getTiles() {
		return _tiles;
	}

	public int getRows() {
		return _rows;
	}

	public int getCols() {
		return _cols;
	}

	/** 
	 * Check to see if the game is solved.
	 * 
	 * @return
	 */
	public boolean isSolved() {
		int count = 0;
	
		for (Tile t : _tiles) {
			if (t.getNum() != count) {
				Log.i(LT.TAG, "Puzzle not solved, tile num "+t.getNum()+" is misplaced at index: "+count);
				return false;
			}
			count++;
		}
		return true;
	}

}
