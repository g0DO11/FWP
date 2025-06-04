package com.inhyung.fwp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LineView extends View {
    private List<Line> lines = new ArrayList<>();
    private Paint paint;

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);
    }

    public void addLine(float x1, float y1, float x2, float y2) {
        lines.add(new Line(x1, y1, x2, y2));
        invalidate(); // 다시 그리기
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Line line : lines) {
            canvas.drawLine(line.x1, line.y1, line.x2, line.y2, paint);
        }
    }

    private static class Line {
        float x1, y1, x2, y2;
        Line(float x1, float y1, float x2, float y2) {
            this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
        }
    }

    public void clearLines() {
        lines.clear();
        invalidate();
    }
}

