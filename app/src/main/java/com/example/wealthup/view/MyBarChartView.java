package com.example.wealthup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.wealthup.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBarChartView extends View {

    private static final String TAG = "MyBarChartView";
    private final Paint barPaint;
    private final Paint highlightBarPaint;
    private final Paint textPaint;
    private final List<Float> barValues = new ArrayList<>();
    private List<String> labels = Arrays.asList("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb");
    private int highlightIndex = -1;

    private float barWidthPx;
    private float barSpacingPx;
    private float textMarginTopPx;
    private float minBarHeightPx;
    private float cornerRadiusPx;

    private float chartWidth;
    private float chartHeight;
    private float maxBarHeight;
    private float totalBarsWidth;
    private float startX;

    public MyBarChartView(Context context) {
        this(context, null);
    }

    public MyBarChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);

        highlightBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightBarPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#666666"));
        textPaint.setTextAlign(Paint.Align.CENTER);

        Typeface poppinsBoldTypeface = ResourcesCompat.getFont(getContext(), R.font.poppins_bold);
        if (poppinsBoldTypeface != null) {
            textPaint.setTypeface(poppinsBoldTypeface);
            Log.d(TAG, "Poppins Bold font loaded and applied to textPaint.");
        } else {
            Log.e(TAG, "Failed to load Poppins Bold font for textPaint.");
        }

        barWidthPx = dpToPx(30);
        barSpacingPx = dpToPx(8);
        textMarginTopPx = dpToPx(4);
        minBarHeightPx = dpToPx(2);
        cornerRadiusPx = dpToPx(20);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyBarChartView);
            try {
                int defaultBarColor = Color.parseColor("#DAA5BA");
                int defaultHighlightColor = Color.parseColor("#8C2155");

                barPaint.setColor(a.getColor(R.styleable.MyBarChartView_barColor, defaultBarColor));
                highlightBarPaint.setColor(a.getColor(R.styleable.MyBarChartView_highlightBarColor, defaultHighlightColor));
                float defaultBarWidth = dpToPx(40);
                barWidthPx = a.getDimension(R.styleable.MyBarChartView_barWidth, defaultBarWidth);

                float barLabelTextSize = a.getDimension(R.styleable.MyBarChartView_barLabelTextSize, -1f);
                if (barLabelTextSize != -1f) {
                    textPaint.setTextSize(barLabelTextSize);
                } else {
                    textPaint.setTextSize(dpToPx(12));
                }

            } finally {
                a.recycle();
            }
        } else {
            barPaint.setColor(Color.parseColor("#DAA5BA"));
            highlightBarPaint.setColor(Color.parseColor("#8C2155"));
            textPaint.setTextSize(dpToPx(12));
        }
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        chartWidth = w - getPaddingLeft() - getPaddingRight();
        chartHeight = h - getPaddingTop() - getPaddingBottom();
        maxBarHeight = chartHeight - textPaint.getTextSize() - textMarginTopPx;

        calculateBarPositions();
    }

    private void calculateBarPositions() {
        if (barValues.isEmpty()) {
            totalBarsWidth = 0;
            startX = getPaddingLeft() + chartWidth / 2f;
            return;
        }

        totalBarsWidth = (barWidthPx + barSpacingPx) * barValues.size() - barSpacingPx;
        if (barValues.size() == 1) {
            totalBarsWidth = barWidthPx;
        }

        startX = getPaddingLeft() + (chartWidth - totalBarsWidth) / 2f;
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (barValues.isEmpty()) {
            float xPos = getWidth() / 2f;
            float yPos = (getHeight() / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f);
            canvas.drawText("Sem dados de gastos", xPos, yPos, textPaint);
            return;
        }

        if (maxBarHeight <= 0) {
            float xPos = getWidth() / 2f;
            float yPos = (getHeight() / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f);
            canvas.drawText("Erro: altura inválida!", xPos, yPos, textPaint);
            return;
        }

        float currentX = startX;
        for (int i = 0; i < barValues.size(); i++) {

            float value = Math.max(0f, Math.min(1f, barValues.get(i)));
            float barHeight = maxBarHeight * value;

            if (value > 0 && barHeight < minBarHeightPx) {
                barHeight = minBarHeightPx;
            } else if (value == 0) {
                barHeight = 0;
            }

            Paint currentPaint = (i == highlightIndex) ? highlightBarPaint : barPaint;

            float left = currentX;
            float top = chartHeight - barHeight + getPaddingTop();
            float right = currentX + barWidthPx;
            float bottom = chartHeight + getPaddingTop();

            canvas.drawRoundRect(left, top, right, bottom, cornerRadiusPx, cornerRadiusPx, currentPaint);

            String label = (i < labels.size()) ? labels.get(i) : "";
            float textX = currentX + barWidthPx / 2f;
            float textY = chartHeight + textMarginTopPx + textPaint.getTextSize() + getPaddingTop();
            canvas.drawText(label, textX, textY, textPaint);

            currentX += barWidthPx + barSpacingPx;
        }
    }

    public void setBarValues(List<Float> values, int highlightIndex) {
        this.barValues.clear();

        if (values != null && !values.isEmpty()) {
            float max = 0f;
            for (Float v : values) {
                if (v != null && v > max) {
                    max = v;
                }
            }

            if (max == 0f) {
                for (Float ignored : values) {
                    this.barValues.add(0f);
                }
            } else {
                for (Float v : values) {
                    this.barValues.add(v != null ? v / max : 0f);
                }
            }
        }

        this.highlightIndex = highlightIndex;
        Log.d(TAG, "setBarValues (normalized) called. Original: " + values + ", highlightIndex: " + highlightIndex);

        calculateBarPositions();
        invalidate();
    }

    public void setLabels(List<String> labels) {
        if (labels != null) {
            this.labels = new ArrayList<>(labels);
            invalidate();
        }
    }

    public void setHighlightIndex(int index) {
        if (this.highlightIndex != index) {
            this.highlightIndex = index;
            invalidate();
        }
    }
}