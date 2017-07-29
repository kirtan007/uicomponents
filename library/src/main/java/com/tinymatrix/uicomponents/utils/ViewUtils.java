package com.tinymatrix.uicomponents.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.tinymatrix.uicomponents.drawables.TextDrawable;

/**
 * Created by kirtan on 2/06/15.
 */
public class ViewUtils
{

    public static void setBackground(View view, Drawable drawable)
    {

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk >= android.os.Build.VERSION_CODES.JELLY_BEAN)
        {
            view.setBackground(drawable);
        }
        else
        {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static Drawable getDrawable(Resources resources, @DrawableRes int drawableResId)
    {
        return ResourcesCompat.getDrawable(resources, drawableResId, null);
    }

    public static void visibleInvisibleView(View view,boolean show)
    {
        if(show)
        {
            view.setVisibility(View.VISIBLE);
        }
        else
        {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void showHideView(View view,boolean show)
    {
        if(show)
        {
            view.setVisibility(View.VISIBLE);
        }
        else
        {
            view.setVisibility(View.GONE);
        }
    }

    public static void setTextWithVisibility(AppCompatTextView textView, CharSequence text)
    {
        if (!text.toString().trim().equals(""))
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        else
        {
            textView.setVisibility(View.GONE);
        }
    }

    public static void setTextWithVisibilityInVisibility(AppCompatTextView textView, CharSequence text)
    {
        if (!text.toString().trim().equals(""))
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        else
        {
            textView.setVisibility(View.INVISIBLE);
        }
    }

    public static TextDrawable getTextDrawableFromName(String text, int backGroundColor, int size, int textSize)
    {
        TextDrawable textDrawable = TextDrawable
                .builder().beginConfig()
                .height(size)
                .width(size)
                .withBorder(2)
                .fontSize(textSize)
                .endConfig()
                .buildRound(text, backGroundColor);

        return textDrawable;
    }

    public static TextDrawable getTextDrawableFromName(String text, int size, int textSize)
    {
        TextDrawable textDrawable = TextDrawable
                .builder().beginConfig()
                .height(size)
                .width(size)
                .withBorder(2)
                .fontSize(textSize)
                .endConfig()
                .buildRound(text, Color.LTGRAY);
        return textDrawable;
    }

    /*public static TextDrawable getTextDrawableFromName(String text, int backGroundColor, int size, int textSize)
    {
        TextDrawable textDrawable = TextDrawable
                .builder().beginConfig()
                .height(size)
                .width(size)
                .withBorder(2)
                .fontSize(textSize)
                .endConfig()
                .buildRound(text, backGroundColor);

        return textDrawable;
    }*/
    /*public static TextDrawable getTextDrawableFromName(String text, int backGroundColor, int size)
    {
        TextDrawable textDrawable = TextDrawable
                .builder().beginConfig()
                .height(size)
                .width(size)
                .endConfig()
                .buildRound(text, backGroundColor);
        return textDrawable;
    }*/

    /*public static TextDrawable getTextDrawableFromName(String text, int size, int textSize)
    {
        TextDrawable textDrawable = TextDrawable
                .builder().beginConfig()
                .height(size)
                .width(size)
                .withBorder(2)
                .fontSize(textSize)
                .endConfig()
                .buildRound(text, Color.LTGRAY);
        return textDrawable;
    }*/
   /* public static TextDrawable getTextDrawableFromName(String text, int size)
    {
        TextDrawable textDrawable = TextDrawable
                .builder().beginConfig()
                .height(size)
                .width(size)
                .endConfig()
                .buildRound(text, Color.LTGRAY);

        return textDrawable;
    }*/
}
