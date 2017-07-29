package com.tinymatrix.uicomponents.utils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

/**
 * Created by kirtan on 22/3/15.
 */
public class EventBusUtil
{
    private static EventBusUtil instance;
    private EventBus eventBus;

    public static EventBusUtil getInstance()
    {
        if (instance == null)
        {
            instance = new EventBusUtil();
        }
        return instance;
    }

    private EventBusUtil()
    {
        EventBus.getDefault();
        EventBusBuilder eventBusBuilder = EventBus.builder();
        eventBusBuilder.throwSubscriberException(false);
        eventBus = eventBusBuilder.build();
    }

    public EventBus getEventBus()
    {
        return eventBus;
    }
}
