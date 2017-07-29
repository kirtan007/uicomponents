package com.tinymatrix.uicomponents.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * @author Tristan Waddington
 */
public class TypeFaceSpan extends MetricAffectingSpan
{
    private Typeface typeface;

    public TypeFaceSpan(Typeface typeface)
    {
        this.typeface = typeface;
    }

    @Override
    public void updateMeasureState(TextPaint textPaint)
    {
        textPaint.setTypeface(typeface);
        textPaint.setFlags(textPaint.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint textPaint)
    {
        textPaint.setTypeface(typeface);
        textPaint.setFlags(textPaint.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}