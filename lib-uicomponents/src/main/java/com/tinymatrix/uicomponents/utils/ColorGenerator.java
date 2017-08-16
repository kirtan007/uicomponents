package com.tinymatrix.uicomponents.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by admin on 12/8/15.
 */
public class ColorGenerator
{

    public static ColorGenerator DEFAULT;

    public static ColorGenerator MATERIAL;

    public static ColorGenerator IOS8;

    public static ColorGenerator MATERIAL2;

    static
    {
        DEFAULT = create(Arrays.asList(
                0xfff16364,
                0xfff58559,
                0xfff9a43e,
                0xffe4c62e,
                0xff67bf74,
                0xff59a2be,
                0xff2093cd,
                0xffad62a7,
                0xff805781
        ));
        MATERIAL = create(Arrays.asList(
                0xffe57373,
                0xfff06292,
                0xffba68c8,
                0xff9575cd,
                0xff7986cb,
                0xff64b5f6,
                0xff4fc3f7,
                0xff4dd0e1,
                0xff4db6ac,
                0xff81c784,
                0xffaed581,
                0xffff8a65,
                0xffd4e157,
                0xffffd54f,
                0xffffb74d,
                0xffa1887f,
                0xff90a4ae
        ));

        MATERIAL2 = create(Arrays.asList(

                0xFFEF9A9A,
                0xFFF48FB1,
                0xFFCE93D8,
                0xFFB39DDB,
                0xFF9FA8DA,
                0xFF90CAF9,
                0xFF81D4FA,
                0xFF80DEEA,
                0xFF80CBC4,
                0xFFA5D6A7,
                0xFFC5E1A5,
                0xFFFFCC80,
                0xFFFFB74D,
                0xFFFFAB91,
                0xFFBCAAA4,
                0xFFB0BEC5
        ));
        IOS8 = create(Arrays.asList(

                0xffE4B7F0,
                0xff5BCAFF,
                0xff81F3FD,
                0xffD7D7D7,
                0xffA4E786,
                0xffFF5B37,
                0xffC643FC,
                0xffFF2A68
        ));
    }

    private final List<Integer> mColors;
    private final Random mRandom;

    public static ColorGenerator create(List<Integer> colorList)
    {
        return new ColorGenerator(colorList);
    }

    private ColorGenerator(List<Integer> colorList)
    {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
    }

    public int getRandomColor()
    {
        return mColors.get(mRandom.nextInt(mColors.size()));
    }

    public int getColor(Object key)
    {
        return mColors.get(Math.abs(key.hashCode()) % mColors.size());
    }
}
