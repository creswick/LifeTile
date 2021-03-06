package com.ciscavate.lifetile;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class BoardView extends View {
	
	private final Activity _context;
	
	private final Paint _textPaint = new Paint();
	
	private Rect _screenDims;

	private Board _board;

	private Paint _borderPaint; 
	
	public BoardView(Activity context, Board board) {
		super(context);
		_context = context;
		_board = board;
		
		_screenDims = getScreenDimensions();
		
		_textPaint.setTextAlign(Align.LEFT);
        _textPaint.setAntiAlias(true);
        _textPaint.setTextSize(16);
        _textPaint.setColor(Color.RED);
        
        _borderPaint = new Paint(_textPaint);
        _borderPaint.setStrokeWidth(0);
        _borderPaint.setStyle(Style.STROKE);
        
        setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				
				Log.i(LT.TAG, "Handling touch event at ("+x+","+y+")");
				
				// get the tile index at x, y.
				int target = getTile(x,y);
				Log.i(LT.TAG, "Touch event on tile @ "+target);
				if (-1 != target) {
					_board.swap(target);
					if (_board.isSolved()) {
						Log.i(LT.TAG, "Congrats!");
						Toast t = Toast.makeText(_context, R.string.congrats, Toast.LENGTH_LONG);
						t.show();
					} else {
						Log.i(LT.TAG, "Nope, not solved yet.");
					}
					// force a redraw:
					BoardView.this.invalidate();
				}

				return true;
			}
		});
	}

	protected int getTile(float x, float y) {
		for (int i = 0; i < _board.getTiles().size(); i++) {
			Rect tile = tileLoc(i, _board.getRows(), _board.getCols());
			
			if (tile.contains((int)x, (int)y)) {
				return i;
			}
		}
		return -1;
	}

	private Rect getScreenDimensions() {
		DisplayMetrics metrics = new DisplayMetrics();
		_context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		return new Rect(0, 0, metrics.widthPixels, metrics.heightPixels);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int tWidth = _screenDims.width() / _board.getCols();
		int tHeight = _screenDims.height() / _board.getRows();
		
		List<Tile> tiles = _board.getTiles();
		for (int i=0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			Rect tileLocation = tileLoc(i, _board.getRows(), _board.getCols());
	
			canvas.drawRect(tileLocation, _borderPaint);
			if ( ! tile.isBlank()) {
				canvas.drawText(""+tile.getNum(),
					tileLocation.left + tWidth / 2,
					tileLocation.bottom - tHeight / 2,
					_textPaint);
			}

//			Rect imageDims = camView(i, _board.getRows(), _board.getCols());
//			canvas.drawBitmap(getCamView(),
//							  imageDims, 
//							  tileLocation, null);
//			Log.i(LT.TAG, "drawing tile "+tile.getNum()+" at "+tileLocation.left+", "+tileLocation.top);
		}
	}

	/**
	 * Calculate the on-screen rectangle that this tile is painted to. 
	 * 
	 * @param tile
	 * @param tileCount
	 * @return
	 */
	private Rect tileLoc(int num, int rows, int cols) {
		int tRow = num / cols;
		int tCol = num % cols;
		
//		Log.i(LT.TAG, "tCol: "+tCol+"tRow: "+tRow);
		
		int tWidth = _screenDims.width() / cols;
		int tHeight = _screenDims.height() / rows;
		
		int left = tCol * tWidth;
		int top = tRow * tHeight;
		int right = left + tWidth;
		int bottom = top + tHeight;
		
		return new Rect(left, top, right, bottom);
	}

	private Rect camView(int num, int rows, int cols) {
		// TODO Auto-generated method stub
		return null;
	}

	private Bitmap getCamView() {
		
		// TODO Auto-generated method stub
		return null;
	}
}
