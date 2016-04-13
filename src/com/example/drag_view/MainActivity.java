package com.example.drag_view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ImageView iv;
    private myDragListener mDragListener;
    private ImageView2DragListener m2DragListener;
    private ImageView iv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView)this.findViewById(R.id.imageView1);
		iv2 = (ImageView)this.findViewById(R.id.imageView2);
		mDragListener = new myDragListener();
		m2DragListener = new ImageView2DragListener();
		iv2.setOnDragListener(m2DragListener);
		iv.setOnDragListener(mDragListener);
		iv.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				ClipData.Item item = new ClipData.Item((CharSequence) iv.getTag());
				ClipData dragData = new ClipData((CharSequence) iv.getTag(),new String[] {
			        ClipDescription.MIMETYPE_TEXT_PLAIN },item);
				View.DragShadowBuilder myShadow = new MyDragShadowBuilder(iv);
				iv.startDrag(dragData, myShadow, null, 0);
				return true;
			}
		});
		
	}
	private static class MyDragShadowBuilder extends View.DragShadowBuilder{
		private static Drawable shadow;
		public MyDragShadowBuilder(View v){
			super(v);
			shadow = new ColorDrawable(Color.LTGRAY);
		}
		@Override
		public void onProvideShadowMetrics(Point shadowSize,
				Point shadowTouchPoint) {
			// TODO Auto-generated method stub
			int width;
			int height;
			width = getView().getWidth() / 2;
			height = getView().getHeight() / 2;
			shadow.setBounds(0,0,width,height);
			shadowSize.set(width, height);
			shadowTouchPoint.set(width/2, height/2);
		}
		@Override
		public void onDrawShadow(Canvas canvas) {
			// TODO Auto-generated method stub
			shadow.draw(canvas);
		}
		
	}

	private class ImageView2DragListener implements View.OnDragListener{

		@Override
		public boolean onDrag(View v, DragEvent event) {
			// TODO Auto-generated method stub
			
			final int action = event.getAction();
			Log.d("gaojian","action = "+action);
			switch(action){
				case DragEvent.ACTION_DRAG_STARTED:
					iv2.setColorFilter(Color.CYAN);
					iv2.invalidate();
					return true;
			case DragEvent.ACTION_DRAG_ENTERED :
					iv2.setColorFilter(Color.GREEN);
					iv2.invalidate();
					return true;
			case DragEvent.ACTION_DRAG_LOCATION:
				    iv2.setColorFilter(Color.YELLOW);
				    iv2.invalidate();
				    return true;
			case DragEvent.ACTION_DRAG_EXITED:
					iv2.setColorFilter(Color.BLUE);
					iv2.invalidate();
					return true;
			case DragEvent.ACTION_DROP:
				    iv2.setColorFilter(Color.RED);
				    iv2.invalidate();
				    return true;
			case DragEvent.ACTION_DRAG_ENDED:
					iv2.clearColorFilter();
					iv2.invalidate();
					if(event.getResult()){
						//Toast.makeText(MainActivity.this, "The drop was handled.", Toast.LENGTH_LONG).show();
					}else{
						//Toast.makeText(MainActivity.this, "The drop didn't work.", Toast.LENGTH_LONG).show();
					}
					return true;
			default : 
					Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
					break;
			}
			return false;
		}
    	
    }

    private class myDragListener implements View.OnDragListener{

		@Override
		public boolean onDrag(View v, DragEvent event) {
			// TODO Auto-generated method stub
			
			final int action = event.getAction();
			switch(action){
				case DragEvent.ACTION_DRAG_STARTED:
					if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
						iv.setColorFilter(Color.BLUE);
						iv.invalidate();
						return true;
					}else{
						return false;
					}
			case DragEvent.ACTION_DRAG_ENTERED :
					iv.setColorFilter(Color.GREEN);
					iv.invalidate();
					return true;
			case DragEvent.ACTION_DRAG_LOCATION:
					return true;
			case DragEvent.ACTION_DRAG_EXITED:
					iv.setColorFilter(Color.BLUE);
					iv.invalidate();
					return true;
			case DragEvent.ACTION_DROP:
					ClipData.Item item = event.getClipData().getItemAt(0);
					CharSequence dragData = item.getText();
					//Toast.makeText(MainActivity.this, "dragData = "+dragData, Toast.LENGTH_LONG).show();
					iv.clearColorFilter();
					iv.invalidate();
					return true;
			case DragEvent.ACTION_DRAG_ENDED:
					iv.clearColorFilter();
					iv.invalidate();
					if(event.getResult()){
						//Toast.makeText(MainActivity.this, "The drop was handled.", Toast.LENGTH_LONG).show();
					}else{
						//Toast.makeText(MainActivity.this, "The drop didn't work.", Toast.LENGTH_LONG).show();
					}
					return true;
			default : 
					Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
					break;
			}
			return false;
		}
    	
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
