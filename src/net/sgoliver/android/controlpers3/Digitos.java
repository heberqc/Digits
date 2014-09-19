package net.sgoliver.android.controlpers3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class Digitos extends View {

	static final char DIGITS[] = { '9', '8', '7', '6', '5', '4', '3', '2',
			'\u00B1', '/', '0', '1', '\u21D0' };
	int hView;
	int wView;
	int nLine = 3;
	int nCol = 4;
	float wButton;
	float hButton;
	StringBuilder input;
	char digit = '0';

	public Digitos(Context context) {
		super(context);
	}

	public Digitos(Context context, AttributeSet attrs, int defaultStyle) {
		super(context, attrs, defaultStyle);
	}

	public Digitos(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(calcDimen(widthMeasureSpec),
				calcDimen(heightMeasureSpec));
	}

	private int calcDimen(int limitSpec) {
		int val = 200;
		int modo = MeasureSpec.getMode(limitSpec);
		int limite = MeasureSpec.getSize(limitSpec);
		if (modo == MeasureSpec.AT_MOST) {
			val = limite;
		} else if (modo == MeasureSpec.EXACTLY) {
			val = limite;
		}
		return val;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		hView = getMeasuredHeight();
		wView = getMeasuredWidth();
		wButton = wView / nCol;
		hButton = hView / (nLine + 1);
//		canvas.drawColor(0xff708090);
		canvas.drawColor(0xff009900);
		Paint paint = new Paint();
		paint.setColor(0xffff0000);
		canvas.drawRect(0.75f * wView, 0, wView, hButton, paint);
		paint.setColor(0xffffffff);
		paint.setTextSize(hView / (nLine + 2));
		paint.setTextAlign(Paint.Align.CENTER);
		for (int i = 0; i < nCol; i++) {
			for (int j = 0; j < nLine; j++) {
				canvas.drawText(String.valueOf(DIGITS[(j * nCol) + i]), wButton
						* (2 * i + 1) / 2, (4 * j + 7) * hButton / 4, paint);
			}
		}
		if (input == null) {
			input = new StringBuilder();
		}
		canvas.drawText(String.valueOf(DIGITS[12]), 0.875f * wView,
				3 * hButton / 4, paint);
		paint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText(input.toString(), 0.74f * wView, 3 * hButton / 4, paint);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		if (action == MotionEvent.ACTION_DOWN) {
			if (y > hButton) {
				digit = DIGITS[(int) (y / hButton - 1) * nCol + (int) (x / wButton)];
				input.append(digit);
				this.invalidate();
			}
		}
		return super.onTouchEvent(event);
	}

}
