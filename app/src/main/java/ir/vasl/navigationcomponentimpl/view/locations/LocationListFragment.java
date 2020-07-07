package ir.vasl.navigationcomponentimpl.view.locations;

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
import ir.vasl.navigationcomponentimpl.models.LocationModel;
import ir.vasl.navigationcomponentimpl.models.NetworkResult;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;
import ir.vasl.navigationcomponentimpl.utils.PublicValues;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;
import ir.vasl.navigationcomponentimpl.view.locations.adapter.LocationsListAdapter;
import ir.vasl.navigationcomponentimpl.viewModels.LocationViewModel;

public class LocationListFragment extends BaseFragment implements GlobalListCallback, MyCustomViewCallBack {

    @BindView(R.id.myCustomView)
    MyCustomView myCustomView;

    private static final String TAG = "LocationListFragment";

    private LocationViewModel locationViewModel;

    private LocationsListAdapter adapter;

    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_locations;
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

        adapter = new LocationsListAdapter(this);
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

        locationViewModel = new ViewModelProvider(getActivity()).get(LocationViewModel.class);
        locationViewModel.exposeNetworkLiveData().observe(getActivity(), new Observer<NetworkResult>() {
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
        locationViewModel.getLiveData().observe(getActivity(), new Observer<List<LocationModel.Result>>() {
            @Override
            public void onChanged(List<LocationModel.Result> results) {
                adapter.setCharacters(results);
                if (results.size() == 0) {
                    myCustomView.setStatus(ListStatus.EMPTY);
                }
            }
        });

        locationViewModel.generateLocations();
    }

    @Override
    public void onRetryClicked() {
        if (locationViewModel != null)
            locationViewModel.generateLocations();
    }

    @Override
    public void onRefresh(int page) {
        if (locationViewModel != null)
            locationViewModel.generateLocations();
    }

    @Override
    public void onItemClicked(Object object) {
        if (object instanceof LocationModel.Result) {
            Intent intent = new Intent(getActivity(), LocationDetailsActivity.class);
            intent.putExtra(PublicValues.KEY_EXTRA_DATA, (Serializable) object);
            startActivity(intent);
        }
    }
}