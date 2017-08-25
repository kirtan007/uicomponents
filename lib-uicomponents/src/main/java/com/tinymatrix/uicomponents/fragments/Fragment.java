package com.tinymatrix.uicomponents.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.Animation;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.activities.Activity;
import com.tinymatrix.uicomponents.annotations.SmartFragment;
import com.tinymatrix.uicomponents.annotations.SmartOptionMenu;
import com.tinymatrix.uicomponents.fragments.controllers.KeyboardController;
import com.tinymatrix.uicomponents.fragments.controllers.ProgressController;
import com.tinymatrix.uicomponents.fragments.controllers.SnackBarController;
import com.tinymatrix.uicomponents.fragments.controllers.ToolbarController;
import com.tinymatrix.uicomponents.models.ToolbarStyle;
import com.tinymatrix.uicomponents.utils.AutoSaveInstance;
import com.tinymatrix.uicomponents.utils.EventBusUtil;
import com.tinymatrix.uicomponents.views.ProgressView;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class Fragment extends android.support.v4.app.Fragment
{
    private CompositeSubscription subscriptionList = new CompositeSubscription();
    protected Activity activity;
    protected Fragment self;
    protected Context selfContext;
    private SmartFragment smartFragment;
    private SmartOptionMenu smartOptionMenu;
    private ProgressController progressController;

    public Fragment()
    {
        super();
        processAnnotations();
        self = this;
    }

    private void processAnnotations()
    {
        Class<?> classz = this.getClass();
        if (classz.isAnnotationPresent(SmartFragment.class))
        {
            smartFragment = classz.getAnnotation(SmartFragment.class);
        }
        if (classz.isAnnotationPresent(SmartOptionMenu.class))
        {
            smartOptionMenu = classz.getAnnotation(SmartOptionMenu.class);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        selfContext = this.getContext();
        activity = (Activity) getActivity();
    }

    public abstract void setUpUI(Bundle savedInstanceState);

    public abstract void attachEventListeners();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (smartOptionMenu != null)
        {
            setHasOptionsMenu(true);
        }
        ToolbarStyle toolbarStyle = smartFragment.toolbarStyle();
        configureToolbar(toolbarStyle);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if(!hidden)
        {
            configureToolbar(smartFragment.toolbarStyle());
            setupToolbar(getTinyMatrixActivity().getToolbar());
        }
        else
        {
            //getTinyMatrixActivity().clearToolbarViews();
        }
    }

    public void configureToolbar(ToolbarStyle toolbarStyle)
    {
        getTinyMatrixActivity().getToolbarController().configureToolbar(toolbarStyle);
        if(toolbarStyle!=ToolbarStyle.NONE)
        {
            setupToolbar(getTinyMatrixActivity().getToolbar());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return getSuperView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        AutoSaveInstance.loadFieldsFromBundle(savedInstanceState, this, Fragment.class);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        if (smartOptionMenu != null)
        {
            inflater.inflate(smartOptionMenu.menuResId(), menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private View getSuperView(LayoutInflater inflater, ViewGroup container)
    {
        View superView = inflater.inflate(R.layout.f_tinymatrix, container, false);
        ViewGroup viewGroup = (ViewGroup)superView.findViewById(R.id.f_container);
        ViewStub vsContent = (ViewStub) superView.findViewById(R.id.f_content);
        vsContent.setLayoutResource(smartFragment.layoutResId());
        vsContent.inflate();

        progressController = new ProgressController(viewGroup,R.id.f_progress);
        progressController.setProgressView(createProgressView());

        if (smartFragment.enableButterKnife())
        {
            ButterKnife.bind(this, superView);
        }
        if (smartFragment.enableFragmentArgs())
        {
            FragmentArgs.inject(this);
        }
        return superView;
    }

    public IProgressView createProgressView()
    {
        ProgressView progressView = new ProgressView(getContext());
        progressView.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,CoordinatorLayout.LayoutParams.MATCH_PARENT));
        return progressView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setUpUI(savedInstanceState);
        attachEventListeners();
        if (smartFragment.enableEventBus())
        {
            EventBusUtil.getInstance().getEventBus().register(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        AutoSaveInstance.saveFieldsToBundle(outState, this, Fragment.class);
    }

    public void addRxCall(Subscription subscription)
    {
        if (subscriptionList.isUnsubscribed())
        {
            subscriptionList = new CompositeSubscription();
        }
        subscriptionList.add(subscription);
    }


    public void cancelRxCalls()
    {
        if (subscriptionList.hasSubscriptions())
        {
            subscriptionList.unsubscribe();
            subscriptionList.clear();
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();
        ((Activity)getActivity()).setCurrentFragment(this);
    }

    @Override
    public void onStop()
    {
        if (smartFragment.enableEventBus())
        {
            EventBusUtil.getInstance().getEventBus().unregister(this);
        }
        cancelRxCalls();
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        getKeyboardController().hideKeyboard();
        super.onDestroyView();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim)
    {
        if (Activity.disableExitAnimation)
        {
            Animation a = new Animation()
            {
            };
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public SnackBarController getSnackBarController()
    {
        return ((Activity)getActivity()).getSnackBarController();
    }
    public KeyboardController getKeyboardController()
    {
        return ((Activity)getActivity()).getKeyBoardController();
    }
    public ProgressController getProgressController()
    {
        return progressController;
    }

    public ToolbarController getToolbarController()
    {
        return  getTinyMatrixActivity().getToolbarController();
    }

    public Activity getTinyMatrixActivity()
    {
        return (Activity)getActivity();
    }

    public abstract void setupToolbar(Toolbar toolbar);
}
