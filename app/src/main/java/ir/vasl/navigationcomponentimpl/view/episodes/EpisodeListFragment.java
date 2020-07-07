package ir.vasl.navigationcomponentimpl.view.episodes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vasl.recyclerlibrary.MyCustomView;
import com.vasl.recyclerlibrary.globalEnums.ListStatus;
import com.vasl.recyclerlibrary.globalInterfaces.MyCustomViewCallBack;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.models.EpisodeModel;
import ir.vasl.navigationcomponentimpl.models.NetworkResult;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;
import ir.vasl.navigationcomponentimpl.utils.PublicValues;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;
import ir.vasl.navigationcomponentimpl.view.episodes.adapter.EpisodesListAdapter;
import ir.vasl.navigationcomponentimpl.viewModels.EpisodeViewModel;

public class EpisodeListFragment extends BaseFragment implements GlobalListCallback, MyCustomViewCallBack {

    @BindView(R.id.myCustomView)
    MyCustomView myCustomView;

    private static final String TAG = "EpisodeListFragment";

    private EpisodeViewModel episodeViewModel;

    private EpisodesListAdapter adapter;

    public EpisodeListFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_episodes;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initView() {

        if (getContext() == null)
            return;

        adapter = new EpisodesListAdapter(this);
        myCustomView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        myCustomView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        myCustomView.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        myCustomView.getRecyclerView().setHasFixedSize(true);
        myCustomView.getRecyclerView().setAdapter(adapter);
        myCustomView.setMyCustomViewCallBack(this);
    }

    private void initViewModel() {

        if (getActivity() == null)
            return;

        episodeViewModel = new ViewModelProvider(getActivity()).get(EpisodeViewModel.class);
        episodeViewModel.exposeNetworkLiveData().observe(getActivity(), new Observer<NetworkResult>() {
            @Override
            public void onChanged(NetworkResult networkResult) {
                switch (networkResult.getResultType()) {
                    case Loading:
                        Log.i(TAG, "onChanged: Loading");
                        myCustomView.setStatus(ListStatus.LOADING);
                        break;

                    case Failure:
                    case Error:
                        Log.i(TAG, "onChanged: Failure | " + networkResult.getMessage());
                        myCustomView.setStatus(ListStatus.FAILURE, networkResult.getMessage());
                        break;

                    case Success:
                        Log.i(TAG, "onChanged: Success");
                        myCustomView.setStatus(ListStatus.SUCCESS);
                        break;
                }
            }
        });
        episodeViewModel.getLiveData().observe(getActivity(), new Observer<List<EpisodeModel.Result>>() {
            @Override
            public void onChanged(List<EpisodeModel.Result> results) {
                adapter.setCharacters(results);
                if (results.size() == 0) {
                    myCustomView.setStatus(ListStatus.EMPTY);
                }
            }
        });

        episodeViewModel.generateEpisodes();
    }

    @Override
    public void onRetryClicked() {
        if (episodeViewModel != null)
            episodeViewModel.generateEpisodes();
    }

    @Override
    public void onRefresh(int page) {
        if (episodeViewModel != null)
            episodeViewModel.generateEpisodes();
    }

    @Override
    public void onItemClicked(Object object) {
        if (object instanceof EpisodeModel.Result) {
            Intent intent = new Intent(getActivity(), EpisodeDetailsActivity.class);
            intent.putExtra(PublicValues.KEY_EXTRA_DATA, (Serializable) object);
            startActivity(intent);
        }
    }
}