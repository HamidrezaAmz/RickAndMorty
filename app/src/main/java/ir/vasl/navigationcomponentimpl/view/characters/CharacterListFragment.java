package ir.vasl.navigationcomponentimpl.view.characters;

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
import androidx.recyclerview.widget.GridLayoutManager;

import com.vasl.recyclerlibrary.MyCustomView;
import com.vasl.recyclerlibrary.globalEnums.ListStatus;
import com.vasl.recyclerlibrary.globalInterfaces.MyCustomViewCallBack;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.models.CharacterModel;
import ir.vasl.navigationcomponentimpl.models.NetworkResult;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseFragment;
import ir.vasl.navigationcomponentimpl.utils.PublicValues;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;
import ir.vasl.navigationcomponentimpl.view.characters.adapters.CharacterListAdapter;
import ir.vasl.navigationcomponentimpl.viewModels.CharacterViewModel;

public class CharacterListFragment extends BaseFragment implements GlobalListCallback, MyCustomViewCallBack {

    @BindView(R.id.myCustomView)
    MyCustomView myCustomView;

    private static final String TAG = "CharacterListFragment";

    private CharacterViewModel characterViewModel;

    private CharacterListAdapter adapter;

    public CharacterListFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_characters;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initViewModel() {

        if (getActivity() == null)
            return;

        characterViewModel = new ViewModelProvider(getActivity()).get(CharacterViewModel.class);
        characterViewModel.exposeNetworkLiveData().observe(getActivity(), new Observer<NetworkResult>() {
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
        characterViewModel.getLiveData().observe(getActivity(), new Observer<List<CharacterModel.Result>>() {
            @Override
            public void onChanged(List<CharacterModel.Result> results) {
                adapter.setCharacters(results);
                if (results.size() == 0) {
                    myCustomView.setStatus(ListStatus.EMPTY);
                }
            }
        });

        characterViewModel.generateCharacters();
    }

    private void initView() {

        if (getContext() == null)
            return;

        adapter = new CharacterListAdapter(this);
        myCustomView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        myCustomView.getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), PublicValues.SPAN_COUNT));
        myCustomView.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        myCustomView.getRecyclerView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        myCustomView.getRecyclerView().setHasFixedSize(true);
        myCustomView.getRecyclerView().setAdapter(adapter);
        myCustomView.setMyCustomViewCallBack(this);

    }

    @Override
    public void onItemClicked(Object object) {
        if (object instanceof CharacterModel.Result) {
            Intent intent = new Intent(getActivity(), CharacterDetailActivity.class);
            intent.putExtra(PublicValues.KEY_EXTRA_DATA, (Serializable) object);
            startActivity(intent);
        }
    }

    @Override
    public void onRetryClicked() {
        if (characterViewModel != null)
            characterViewModel.generateCharacters();
    }

    @Override
    public void onRefresh(int page) {
        if (characterViewModel != null)
            characterViewModel.generateCharacters();
    }
}