package com.tinymatrix.uicomponents.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Field;
import com.tinymatrix.uicomponents.annotations.SaveInstance;

/**
 * Created by admin on 6/23/16.
 */

public class AutoSaveInstance
{
    public static void saveFieldsToBundle(Bundle bundle, Object thisClass, Class<?> baseClass)
    {
        Class<?> clazz = thisClass.getClass();
        while (baseClass.isAssignableFrom(clazz))
        {
            String className = clazz.getName();
            for (Field field : clazz.getDeclaredFields())
            {
                if (field.isAnnotationPresent(SaveInstance.class))
                {
                    String name = field.getName();
                    field.setAccessible(true);
                    try
                    {
                        Object value = field.get(thisClass);
                        String key = className + name;
                        if (value instanceof Serializable)
                        {
                            bundle.putSerializable(key, (Serializable) value);
                        }
                        else if (value instanceof Parcelable)
                        {
                            bundle.putParcelable(key, (Parcelable) value);
                        }
                    }
                    catch (Exception e)
                    {
                        //Could not put this field in the bundle.
                        //Log.w("AndroidAutowire", "The field \"" + name + "\" was not added to the bundle");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Look through the Activity/Fragment and the Base Classes.  Find all fields (member variables)  annotated
     * with the {@link SaveInstance} annotation. Get the saved value for these fields from the Bundle, and
     * load the value into the field.
     *
     * @param bundle    {@link Bundle} with the Activity/Fragment's saved state.
     * @param thisClass Activity/Fragment being re-loaded
     * @param baseClass Base class of the Activity/Fragment
     */
    public static void loadFieldsFromBundle(Bundle bundle, Object thisClass, Class<?> baseClass)
    {
        if (bundle == null)
        {
            return;
        }
        Class<?> clazz = thisClass.getClass();
        while (baseClass.isAssignableFrom(clazz))
        {
            for (Field field : clazz.getDeclaredFields())
            {
                if (field.isAnnotationPresent(SaveInstance.class))
                {
                    field.setAccessible(true);
                    try
                    {
                        Object fieldVal = bundle.get(clazz.getName() + field.getName());
                        if (fieldVal != null)
                        {
                            field.set(thisClass, fieldVal);
                        }
                    }
                    catch (Exception e)
                    {
                        //Could not get this field from the bundle.
                        //Log.w("AndroidAutowire", "The field \"" + field.getName() + "\" was not retrieved from the bundle");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
