package com.tinymatrix.uicomponents.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tinymatrix.uicomponents.activities.Activity;
import com.tinymatrix.uicomponents.annotations.SmartActivity;
import com.tinymatrix.uicomponents.demo.R;
import com.tinymatrix.uicomponents.fragments.AnimatedDrawer;
import com.tinymatrix.uicomponents.fragments.IDrawer;
import com.tinymatrix.uicomponents.models.ToolbarStyle;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by systemx on 15/4/17.
 */


@SmartActivity(layoutResId = R.layout.a_main, fragmentContainerResId = R.id.a_activity_content, toolbarStyle = ToolbarStyle.NORMAL_TOOLBAR, enableDrawer = true)
public class ActivityTest extends Activity
{
    @BindView(R.id.a_main_nested_scrollview)
    NestedScrollView nestedScrollView;

    @BindView(R.id.a_main_btn_no_toolbar)
    AppCompatButton btnNoToolbar;

    @BindView(R.id.a_main_btn_normal_toolbar)
    AppCompatButton btnNormalToolbar;

    @BindView(R.id.a_main_btn_collapsible_toolbar)
    AppCompatButton btnCollapsibleToolbar;

    @BindView(R.id.a_main_btn_show_progress)
    AppCompatButton btnShowProgress;

    @BindView(R.id.a_main_btn_open_drawer)
    AppCompatButton btnOpenDrawer;

    @Override
    public void setUpUI(Bundle savedInstanceState)
    {
        /* Custom Progress Test */
        //CustomProgress customProgress = new CustomProgress(this);
       // customProgress.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //getProgressController().setProgressView(customProgress);
    }

    @Override
    public IDrawer createDrawer()
    {
        return new AnimatedDrawer(getCoordinatorLayout());
    }

    @Override
    public void attachEventListeners()
    {
        btnNoToolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getToolbarController().configureNoToolbar();
            }
        });
        btnNormalToolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getToolbarController().configureNonCollapsibleToolbar();
            }
        });

        btnCollapsibleToolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getToolbarController().configureCollapsibleToolbar();
            }
        });

        btnShowProgress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getProgressController().showProgress();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        getProgressController().hideProgress();
                        /*getProgressController().showRetry(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Timber.d("Retry Clicked");
                            }
                        });*/
                    }
                }, 3000);
            }
        });

        btnOpenDrawer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDrawer().openDrawer();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setupToolbar(Toolbar toolbarObj, ViewGroup contentView)
    {
        toolbarObj.setTitle("Hello World");
        toolbarObj.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbarObj.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(selfContext, "Hello World", Toast.LENGTH_SHORT).show();
            }
        });
        View additionalView = getLayoutInflater().inflate(R.layout.v_toolbarview, contentView, false);
        contentView.addView(additionalView, 0);
    }
}
