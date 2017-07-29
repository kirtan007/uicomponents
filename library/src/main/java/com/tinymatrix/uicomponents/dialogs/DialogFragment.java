package com.tinymatrix.uicomponents.dialogs;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.annotations.SaveInstance;
import com.tinymatrix.uicomponents.annotations.SmartDialog;
import com.tinymatrix.uicomponents.fragments.IRxComponent;
import com.tinymatrix.uicomponents.utils.AutoSaveInstance;
import com.tinymatrix.uicomponents.utils.EventBusUtil;
import com.tinymatrix.uicomponents.views.ProgressView;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by kirtan on 2/10/15.
 */

public abstract class DialogFragment extends AppCompatDialogFragment implements IRxComponent
{
    private SubscriptionList subscriptionList = new SubscriptionList();
    private SmartDialog smartDialog;
    private ProgressView progressView;
    View contentView;

    @SaveInstance
    private String title = "";

    public DialogFragment()
    {
        processAnnotation();
    }

    @Override
    public void onDestroyView()
    {
        cancelRxCalls();
        super.onDestroyView();
    }

    @Override
    public void addRxCall(Subscription subscription)
    {
        if(subscriptionList.isUnsubscribed())
        {
            subscriptionList = new SubscriptionList();
        }
        subscriptionList.add(subscription);
    }

    @Override
    public void cancelRxCalls()
    {
        if (subscriptionList.hasSubscriptions())
        {
            subscriptionList.unsubscribe();
            subscriptionList.clear();
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
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        AutoSaveInstance.loadFieldsFromBundle(savedInstanceState, this, DialogFragment.class);
        getDialog().setTitle(getTitle());
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
        /* Set Cancellable */
        setCancelable(smartDialog.cancellable());
        if (isEventBusEnabled())
        {
            EventBusUtil.getInstance().getEventBus().register(this);
        }

        return view;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public View getSuperView(LayoutInflater inflater, ViewGroup container)
    {
        View superView = inflater.inflate(R.layout.f_intellicode_dialog, container, true);
        RelativeLayout rlMainContainer = (RelativeLayout) superView.findViewById(R.id.f_intellicode_dialog_rl_container);
        progressView = (ProgressView) superView.findViewById(R.id.f_intellicode_dialog_status_view);
        ViewStub vsContent = (ViewStub) superView.findViewById(R.id.f_intellicode_dialog_vs_content);
        vsContent.setLayoutResource(smartDialog.layoutResId());
        contentView = vsContent.inflate();
        if (smartDialog.enableButterKnife())
        {
            ButterKnife.bind(this, superView);
        }
        return superView;
    }

    public void showProgress()
    {
        progressView.showProgress();
    }

    public void hideProgress()
    {
        progressView.hideProgress();
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

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        AutoSaveInstance.saveFieldsToBundle(outState, this, DialogFragment.class);
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
