package com.tinymatrix.uicomponents.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.tinymatrix.uicomponents.annotations.SmartFragment;
import com.tinymatrix.uicomponents.annotations.SmartOptionMenu;
import com.tinymatrix.uicomponents.fragments.Fragment;
import com.tinymatrix.uicomponents.models.ToolbarStyle;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by systemx on 13/5/17.
 */
@SmartOptionMenu(menuResId = R.menu.f_a)
@SmartFragment(layoutResId = R.layout.f_a,toolbarStyle = ToolbarStyle.COLLAPSIBLE_TOOLBAR)
public class FragmentA extends Fragment
{
    @BindView(R.id.f_a_btn_b)
    AppCompatButton btnStartB;

    @BindView(R.id.f_a_btn_show_snackbar)
    AppCompatButton btnShowSnackbar;

    @BindView(R.id.a_main_btn_no_toolbar)
    AppCompatButton btnNoToolbar;

    @BindView(R.id.a_main_btn_normal_toolbar)
    AppCompatButton btnNormalToolbar;

    @BindView(R.id.a_main_btn_collapsible_toolbar)
    AppCompatButton btnCollapsibleToolbar;

    @BindView(R.id.a_main_btn_show_progress)
    AppCompatButton btnShowProgress;

    @Override
    public void setUpUI(Bundle savedInstanceState)
    {
        //CustomProgress customProgress = new CustomProgress(getContext());
        //customProgress.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
       // getProgressController().setProgressView(customProgress);
        ViewGroup appBarContent =getTinyMatrixActivity().getAppBarContentLayout();
        View additionalView= getActivity().getLayoutInflater().inflate(R.layout.v_toolbarview,appBarContent,false);
        appBarContent.addView(additionalView,0);
    }

    @Override
    public void attachEventListeners()
    {
        btnStartB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((FragmentActivityTest)getActivity()).startFragmentB();
            }
        });

        btnShowSnackbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSnackBarController().getSnackBar(v,"hello").show();
            }
        });

        btnNoToolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                configureToolbar(ToolbarStyle.NONE);
            }
        });
        btnNormalToolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                configureToolbar(ToolbarStyle.NORMAL_TOOLBAR);
            }
        });
        btnCollapsibleToolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                configureToolbar(ToolbarStyle.COLLAPSIBLE_TOOLBAR);
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
                      /*  getProgressController().showRetry(R.string.internet_not_available, R.drawable.ic_signal_wifi_off_black_48dp, new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                            }
                        });*/
                    }
                },3000);
            }
        });


    }



    @Override
    public void setupToolbar(Toolbar toolbar)
    {
        Timber.d("Set new Title to toolbar");
        toolbar.setTitle("Fragment A");
    }
}
