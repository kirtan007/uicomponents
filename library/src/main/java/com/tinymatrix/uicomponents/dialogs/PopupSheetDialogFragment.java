package com.tinymatrix.uicomponents.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.annotations.SmartDialog;
import com.tinymatrix.uicomponents.fragments.IRxComponent;
import com.tinymatrix.uicomponents.utils.EventBusUtil;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by kirtan on 2/10/15.
 */

public abstract class PopupSheetDialogFragment extends android.support.design.widget.BottomSheetDialogFragment implements IRxComponent
{
    private SmartDialog smartDialog;
    private SubscriptionList subscriptionList = new SubscriptionList();
    private RelativeLayout rlMainContainer;

    public PopupSheetDialogFragment()
    {
        processAnnotation();
    }

    View contentView;


    @Override
    public void onDestroyView()
    {
        if (smartDialog.enableEventBus())
        {
            EventBusUtil.getInstance().getEventBus().unregister(this);
        }
        /*if (smartDialog.enableButterKnife())
        {
            ButterKnife.unbind(this);
        }*/
        cancelRxCalls();
        super.onDestroyView();
    }

    @Override
    public void addRxCall(Subscription subscription)
    {
        subscriptionList.add(subscription);
    }

    @Override
    public void cancelRxCalls()
    {
        if (subscriptionList.hasSubscriptions())
        {
            subscriptionList.unsubscribe();
        }
    }

    public void show(FragmentManager manager)
    {
        super.show(manager, getClass().getName());
        hideKeyboard();
    }

    @Override
    public void show(FragmentManager manager, String tag)
    {
        super.show(manager, tag);
        hideKeyboard();
    }

    @Override
    public Dialog getDialog()
    {
        return super.getDialog();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        hideKeyboard();
    }

    public interface ILayoutListener
    {
        void onLayoutCompleted();
    }

    public void setLayoutListener(ILayoutListener layoutListener)
    {
        this.layoutListener = layoutListener;
        attachLayoutListener();
    }

    private ILayoutListener layoutListener;

    private void attachLayoutListener()
    {
        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                int sdkInt = Build.VERSION.SDK_INT;
                if (sdkInt >= 16)
                {
                    getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else
                {
                    getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (layoutListener != null)
                {
                    layoutListener.onLayoutCompleted();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = getSuperView(inflater, container);

        //* Set Cancellable */
        //setCancelable(smartDialog.cancellable());

        /* Set Layout */
        // getDialog().getWindow().setContentView(smartDialog.layoutResId());
        if (smartDialog.enableEventBus())
        {
            EventBusUtil.getInstance().getEventBus().register(this);
        }

        /* Set Title */
        if (!smartDialog.title().equals(""))
        {
            getDialog().setTitle(smartDialog.title());
        }
        return view;
    }

    public View getSuperView(LayoutInflater inflater, ViewGroup container)
    {
        View superView = inflater.inflate(R.layout.f_intellicode_dialog, container, true);
        rlMainContainer = (RelativeLayout) superView.findViewById(R.id.f_intellicode_dialog_rl_container);
        ViewStub vsContent = (ViewStub) superView.findViewById(R.id.f_intellicode_dialog_vs_content);
        vsContent.setLayoutResource(smartDialog.layoutResId());
        contentView = vsContent.inflate();
        if (smartDialog.enableButterKnife())
        {
            ButterKnife.bind(this, superView);
        }
        return superView;
    }

    @Override
    public void onDestroy()
    {
        if (isEventBusEnabled())
        {
            EventBusUtil.getInstance().getEventBus().unregister(this);
        }
        super.onDestroy();
    }

    private void processAnnotation()
    {
        Class<?> classz = this.getClass();
        if (classz.isAnnotationPresent(SmartDialog.class))
        {
            smartDialog = classz.getAnnotation(SmartDialog.class);
        }
    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void dismissAllowingStateLoss()
    {
        hideKeyboard();
        super.dismissAllowingStateLoss();
    }

    protected void hideKeyboard()
    {
        if (getActivity() != null)
        {
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(android.R.id.content);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewGroup.getWindowToken(), 0);
        }
    }

    public abstract boolean isEventBusEnabled();
}
