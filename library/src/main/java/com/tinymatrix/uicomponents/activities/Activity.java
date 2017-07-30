package com.tinymatrix.uicomponents.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tinymatrix.uicomponents.R;
import com.tinymatrix.uicomponents.annotations.SmartActivity;
import com.tinymatrix.uicomponents.fragments.Fragment;
import com.tinymatrix.uicomponents.fragments.IBackPressSupport;
import com.tinymatrix.uicomponents.fragments.IDrawer;
import com.tinymatrix.uicomponents.fragments.IProgressView;
import com.tinymatrix.uicomponents.fragments.NormalDrawer;
import com.tinymatrix.uicomponents.fragments.controllers.BackStackController;
import com.tinymatrix.uicomponents.fragments.controllers.KeyboardController;
import com.tinymatrix.uicomponents.fragments.controllers.ProgressController;
import com.tinymatrix.uicomponents.fragments.controllers.SnackBarController;
import com.tinymatrix.uicomponents.fragments.controllers.ToolbarController;
import com.tinymatrix.uicomponents.models.ToolbarStyle;
import com.tinymatrix.uicomponents.utils.EventBusUtil;
import com.tinymatrix.uicomponents.views.ProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by kirtan on 3/12/14.
 */
public abstract class Activity extends AppCompatActivity
{
    protected SmartActivity smartActivity;
    public Fragment currentFragment;
    public android.app.Activity selfContext;
    protected CompositeSubscription subscriptionList = new CompositeSubscription();
    public KeyboardController keyboardController;

    public BackStackController backStackController;
    public SnackBarController snackBarController;
    private ToolbarController toolbarController;
    private ProgressController progressController;
    private IDrawer drawer;
    private IProgressView progressView;

    private View superView;
    Context context;
    CoordinatorLayout coordinatorLayout;

    public Activity()
    {
        processAnnotation();
    }

    protected void processAnnotation()
    {
        Class<?> classz = this.getClass();
        if (classz.isAnnotationPresent(SmartActivity.class))
        {
            smartActivity = classz.getAnnotation(SmartActivity.class);
        }
    }

    public void setCurrentFragment(Fragment fragment)
    {
        this.currentFragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        selfContext = this;
        context = Activity.this;

        /* Initialize Controllers */
        backStackController = new BackStackController(this);
        keyboardController = new KeyboardController(this);
        snackBarController = new SnackBarController(this);
        toolbarController = new ToolbarController(this);

        if (smartActivity.enableEventBus())
        {
            EventBusUtil.getInstance().getEventBus().register(this);
        }
        buildActivityLayout();
        setUpUI(savedInstanceState);
        attachEventListeners();
    }

    public void showFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            ShowConfigBuilder fragmentShowShowConfigBuilder = new ShowConfigBuilder().fragment(fragment).animate(true);
            showFragment(fragmentShowShowConfigBuilder);
        }
    }

    public void showFragment(ShowConfigBuilder showConfigBuilder)
    {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (showConfigBuilder.fragment != null)
        {
            /* Configure toolbar before showing content */
            Fragment fragment = showConfigBuilder.fragment;

            String tag = fragment.getClass().getName();
            if (currentFragment == null || !currentFragment.getClass().getName().equals(tag))
            {
                if (!showConfigBuilder.removeUptoId.equals(""))
                {
                    backStackController.clearBackStackUpto(showConfigBuilder.removeUptoId);
                }
                if (!showConfigBuilder.removeInclusiveId.equals(""))
                {
                    backStackController.clearBackStackInclusive(showConfigBuilder.removeInclusiveId);
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                /* Handle Animation */
                if (showConfigBuilder.animate)
                {
                    if (showConfigBuilder.sharedElements.size() > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                       /* Slide slideTransition = new Slide(Gravity.RIGHT);
                        slideTransition.setDuration(300);*/

                        ChangeBounds changeBoundsTransition = new ChangeBounds();
                        changeBoundsTransition.setDuration(300);

                        Fade fade = new Fade();
                        fade.setDuration(300);

                        fragment.setEnterTransition(fade);
                        fragment.setExitTransition(fade);

                        fragment.setAllowEnterTransitionOverlap(true);
                        fragment.setAllowReturnTransitionOverlap(true);
                        fragment.setSharedElementEnterTransition(changeBoundsTransition);

                        Iterator<Map.Entry<String, View>> sharedElementIterator = showConfigBuilder.sharedElements.entrySet().iterator();
                        while (sharedElementIterator.hasNext())
                        {
                            Map.Entry<String, View> entry = sharedElementIterator.next();
                            Timber.d("Element Name :" + entry.getKey());
                            ViewCompat.setTransitionName(entry.getValue(), entry.getKey());
                            transaction.addSharedElement(entry.getValue(), entry.getKey());
                        }
                    }
                    else
                    {
                        //  transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    }
                }

                if (showConfigBuilder.requestCode != -1)
                {
                    fragment.setTargetFragment(currentFragment, showConfigBuilder.requestCode);
                }

                if (showConfigBuilder.transactionType == ShowConfigBuilder.TransactionType.FRAGMENT_REPLACE)
                {
                    transaction = transaction
                            .replace(getContainerLayoutId(), fragment, tag)
                            .addToBackStack(tag);
                }
                else
                {
                    transaction = transaction
                            .hide(currentFragment)
                            .show(fragment)
                            .add(getContainerLayoutId(), fragment, tag)
                            .addToBackStack(tag);
                }

                if (!showConfigBuilder.allowStateLossCommit)
                {
                    transaction.commit();
                }
                else
                {
                    transaction.commitAllowingStateLoss();
                }
            }
        }
    }

    public static boolean disableExitAnimation = false;

    public void addRxCall(Subscription subscription)
    {
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

    public void onBackPressed()
    {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (currentFragment instanceof IBackPressSupport)
        {
            ((IBackPressSupport) currentFragment).onBackPressed();
        }
        else
        {
            if (backStackEntryCount > 1)
            {
                getSupportFragmentManager().popBackStack();
            }
            else
            {
                disableExitAnimation = true;
                compatFinish();
                disableExitAnimation = false;
                System.exit(0);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        cancelRxCalls();
        if (smartActivity.enableEventBus())
        {
            EventBusUtil.getInstance().getEventBus().unregister(this);
        }
        super.onDestroy();
    }


    public void compatFinish()
    {
        if (Build.VERSION.SDK_INT >= 21)
        {
            finishAndRemoveTask();
        }
        else
        {
            finish();
        }
    }

    private int getContainerLayoutId()
    {
        int containerLayoutId = smartActivity.fragmentContainerResId();
        if (containerLayoutId == -1)
        {
            try
            {
                throw new Exception("Please add Container Layout ID for Showing fragment");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return smartActivity.fragmentContainerResId();
    }

    public abstract void setUpUI(Bundle savedInstanceState);

    public abstract void attachEventListeners();

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static class ShowConfigBuilder
    {
        public enum TransactionType
        {
            FRAGMENT_ADD,
            FRAGMENT_REPLACE;
        }

        protected String removeUptoId = "";
        protected String removeInclusiveId = "";
        protected Fragment fragment = null;
        protected boolean animate = true;
        protected boolean reverseAnimate = false;
        protected Map<String, View> sharedElements = new HashMap<>();
        protected boolean allowStateLossCommit = false;
        protected TransactionType transactionType=TransactionType.FRAGMENT_REPLACE;
        protected int requestCode = -1;

        public ShowConfigBuilder fragment(Fragment fragment)
        {
            this.fragment = fragment;
            return this;
        }

        public ShowConfigBuilder addSharedElements(String key, View view)
        {
            sharedElements.put(key, view);
            return this;
        }

        public ShowConfigBuilder allowStateLossCommit(boolean allowStateLossCommit)
        {
            this.allowStateLossCommit = allowStateLossCommit;
            return this;
        }

        public ShowConfigBuilder reverseAnimate(boolean reverseAnimate)
        {
            this.reverseAnimate = reverseAnimate;
            return this;
        }

        public ShowConfigBuilder removeUptoId(String removeUptoId)
        {
            this.removeUptoId = removeUptoId;
            return this;
        }

        public ShowConfigBuilder removeInclusiveId(String removeInclusiveId)
        {
            this.removeInclusiveId = removeInclusiveId;
            return this;
        }

        public ShowConfigBuilder animate(boolean animate)
        {
            this.animate = animate;
            return this;
        }
    }


    //////////////////////////////////////Toolbar Control //////////////////////////////////////////
    public abstract void setupToolbar(Toolbar toolbarObj, ViewGroup contentView);

    private List<View> toolbarViews = new ArrayList<>();

    public void addViewToToolbar(View view)
    {
        //  toolbar.addView(view);
        toolbarViews.add(view);
    }

    public void enableToolbarViews()
    {
        if (toolbarViews.size() > 0)
        {
            for (View view : toolbarViews)
            {
                view.setEnabled(true);
                view.setClickable(true);
            }
        }
    }

    public void disableToolbarViews()
    {
        if (toolbarViews.size() > 0)
        {
            for (View view : toolbarViews)
            {
                view.setEnabled(false);
                view.setClickable(false);
            }
        }
    }

    public void buildActivityLayout()
    {
        coordinatorLayout = createCoordinatorLayout(Activity.this);
        coordinatorLayout.setFitsSystemWindows(false);
        progressController = new ProgressController(coordinatorLayout, R.id.a_activity_progress);
        progressController.setProgressListener(new ProgressController.IProgressListener()
        {
            @Override
            public void onShow()
            {
                toolbarController.disableViews();
            }

            @Override
            public void onHide()
            {
                toolbarController.enableViews();
            }
        });

        ToolbarStyle toolbarStyle = smartActivity.toolbarStyle();
        toolbarController.configureToolbar(toolbarStyle);
        ViewGroup contentFrameLayout = createContentView();

        coordinatorLayout.addView(contentFrameLayout);

        superView = coordinatorLayout;

        if (smartActivity.enableDrawer())
        {
            drawer = createDrawer();
            setContentView(drawer.getView());
        }
        else
        {
            setContentView(superView);
        }
        /* Adding Progress View */
        progressView = createProgressView();
        progressController.setProgressView(progressView);
        if (smartActivity.enableButterKnife())
        {
            ButterKnife.bind(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /*if (toolbarController != null)
        {
            toolbarController.onCreateOptionsMenu(menu);
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (toolbarController != null)
        {
            toolbarController.onPrepareOptionsMenu(menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public IDrawer createDrawer()
    {
        NormalDrawer animatedDrawer = new NormalDrawer(coordinatorLayout);
        return animatedDrawer;
    }

    public IProgressView createProgressView()
    {
        ProgressView progressView = new ProgressView(context);
        progressView.setLayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT));
        return progressView;
    }

    public ViewGroup createContentView()
    {
        CoordinatorLayout.LayoutParams contentLayoutParams = new CoordinatorLayout.LayoutParams(new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT));
        contentLayoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        FrameLayout contentFrameLayout = new FrameLayout(context);

        contentFrameLayout.setLayoutParams(contentLayoutParams);
        contentFrameLayout.setId(R.id.a_activity_content);

        getLayoutInflater().inflate(smartActivity.layoutResId(), contentFrameLayout, true);
        return contentFrameLayout;
    }

    private CoordinatorLayout createCoordinatorLayout(Context context)
    {
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(context);
        coordinatorLayout.setId(R.id.a_activity_coordinator_layout);
        ViewGroup.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        coordinatorLayout.setLayoutParams(layoutParams);
        return coordinatorLayout;
    }

    private int getPrimaryColor(Context context)
    {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public AppBarLayout getAppBarLayout()
    {
        return (AppBarLayout) superView.findViewById(R.id.a_activity_appbar);
    }

    public Toolbar getToolbar()
    {
        return (Toolbar) superView.findViewById(R.id.a_activity_toolbar);
    }

    public CoordinatorLayout getCoordinatorLayout()
    {
        return coordinatorLayout;
    }

    public ViewGroup getAppBarContentLayout()
    {
        return (ViewGroup) superView.findViewById(R.id.a_activity_appbar_content);
    }

    public CollapsingToolbarLayout getCollapsibleToolbarLayout()
    {
        return (CollapsingToolbarLayout) superView.findViewById(R.id.a_activity_collapsing_toolbar);
    }

    public ViewGroup getContentView()
    {
        return (ViewGroup) superView.findViewById(R.id.a_activity_content);
    }

    public SnackBarController getSnackBarController()
    {
        return snackBarController;
    }

    public BackStackController getBackStackController()
    {
        return backStackController;
    }

    public KeyboardController getKeyBoardController()
    {
        return keyboardController;
    }

    public ToolbarController getToolbarController()
    {
        return toolbarController;
    }

    public ProgressController getProgressController()
    {
        return progressController;
    }

    public IDrawer getDrawer()
    {
        return drawer;
    }
}
