package com.ciscavate.lifetile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LifeTileActivity extends Activity {
	
    private Board _board;
	private BoardView _boardView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _board = new Board(4,8);
        _boardView = new BoardView(this, _board);

        
        // hide the title bar:
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide notification bar:
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        					      WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(_boardView);
    }
}