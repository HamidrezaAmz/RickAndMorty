package ir.vasl.navigationcomponentimpl.view.episodes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
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
import ir.vasl.navigationcomponentimpl.databinding.ActivityEpisodeDetailsBinding;
import ir.vasl.navigationcomponentimpl.models.CharacterModel;
import ir.vasl.navigationcomponentimpl.models.EpisodeModel;
import ir.vasl.navigationcomponentimpl.models.NetworkResult;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseActivity;
import ir.vasl.navigationcomponentimpl.utils.PublicValues;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;
import ir.vasl.navigationcomponentimpl.view.characters.CharacterDetailActivity;
import ir.vasl.navigationcomponentimpl.view.characters.adapters.CharacterListAdapter;
import ir.vasl.navigationcomponentimpl.viewModels.CharacterViewModel;

public class EpisodeDetailsActivity extends BaseActivity implements GlobalListCallback, MyCustomViewCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActivityEpisodeDetailsBinding binding;

    private CharacterViewModel characterViewModel;

    private CharacterListAdapter adapter;

    private MyCustomView myCustomView;

    private static final String TAG = "EpisodeDetailsActivity";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_episode_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId());

        extractExtra();
        initViews();
    }

    private void extractExtra() {

        if (getIntent() == null
                || getIntent().getExtras() == null
                || !getIntent().getExtras().containsKey(PublicValues.KEY_EXTRA_DATA))
            return;

        EpisodeModel.Result result = (EpisodeModel.Result) getIntent().getExtras().getSerializable(PublicValues.KEY_EXTRA_DATA);

        if (binding != null)
            binding.setResult(result);
    }

    private void initViews() {

        myCustomView = findViewById(R.id.myCustomView); // WTF! not work with butter-knife :|

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Episode Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        adapter = new CharacterListAdapter(this);
        myCustomView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        myCustomView.getRecyclerView().setLayoutManager(new GridLayoutManager(this, PublicValues.SPAN_COUNT));
        myCustomView.getRecyclerView().addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        myCustomView.getRecyclerView().setHasFixedSize(true);
        myCustomView.getRecyclerView().setAdapter(adapter);
        myCustomView.setMyCustomViewCallBack(this);

        initViewModels();
    }

    private void initViewModels() {

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);
        characterViewModel.exposeNetworkLiveData().observe(this, new Observer<NetworkResult>() {
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
        characterViewModel.getLiveData().observe(this, new Observer<List<CharacterModel.Result>>() {
            @Override
            public void onChanged(List<CharacterModel.Result> results) {
                adapter.setCharacters(results);
                if (results.size() == 0)
                    myCustomView.setStatus(ListStatus.EMPTY);
            }
        });

        characterViewModel.generateCharacters();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onItemClicked(Object object) {
        if (object instanceof CharacterModel.Result) {
            Intent intent = new Intent(EpisodeDetailsActivity.this, CharacterDetailActivity.class);
            intent.putExtra(PublicValues.KEY_EXTRA_DATA, (Serializable) object);
            startActivity(intent);
        }
    }
}