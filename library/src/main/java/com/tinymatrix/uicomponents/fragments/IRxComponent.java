package com.tinymatrix.uicomponents.fragments;

import rx.Subscription;

/**
 * Created by kirtan on 24/09/15.
 */
public interface IRxComponent
{
    void addRxCall(Subscription subscription);
    void cancelRxCalls();
}
