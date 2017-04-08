package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieCastAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieCastContract.Presenter;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieCastFragment extends Fragment
        implements MovieCastContract.View{

    private static final String TAG=MovieCastFragment.class.getSimpleName();

    private Unbinder unbinder;
    private MovieCastAdapter adapter;
    private Presenter presenter;

    private int movieId;

    @BindView(R.id.castList)
    protected RecyclerView castList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        this.movieId=savedInstanceState.getInt(Constants.EXTRA_ID);
        initializeDependencies();
    }

    private void initializeDependencies(){
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    public static MovieCastFragment newInstance(int movieID){
        Bundle args=new Bundle();
        args.putInt(Constants.EXTRA_ID,movieID);
        MovieCastFragment fragment=new MovieCastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_cast,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    @Override
    @Inject
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start(movieId);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void showCast(@NonNull List<ActorCover> cast) {
        adapter=new MovieCastAdapter(getContext(),cast);
        castList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        castList.setHasFixedSize(true);
        castList.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        castList.setAdapter(adapter);
    }

}