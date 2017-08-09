package com.dev.sanskar.newscgpioneer.Frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dev.sanskar.newscgpioneer.R;
import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by Sanskar on 1/28/2017.
 */
public class HomeFrag extends Fragment {
    RelativeLayout relativeLayout;

    PullToRefreshView mPullToRefreshView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view =  inflater.inflate(R.layout.layout__home_frag, container, false);
        RecyclerView mRecHome = (RecyclerView) view.findViewById(R.id.post_list_home);
        mRecHome.setHasFixedSize(false);
        LinearLayoutManager linlayManHome = new LinearLayoutManager(getActivity());
        linlayManHome.setOrientation(LinearLayoutManager.VERTICAL);
        linlayManHome.canScrollVertically();
        mRecHome.setLayoutManager(linlayManHome);

        final int REFRESH_DELAY = 1000;
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });



        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
