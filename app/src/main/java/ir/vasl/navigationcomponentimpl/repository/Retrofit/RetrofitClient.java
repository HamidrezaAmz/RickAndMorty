package ir.vasl.navigationcomponentimpl.repository.Retrofit;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import ir.vasl.navigationcomponentimpl.models.CharacterModel;
import ir.vasl.navigationcomponentimpl.models.EpisodeModel;
import ir.vasl.navigationcomponentimpl.models.LocationModel;
import ir.vasl.navigationcomponentimpl.models.NetworkResult;
import ir.vasl.navigationcomponentimpl.utils.GlobalEnum.ResultEnum;
import ir.vasl.navigationcomponentimpl.utils.PublicValues;
import ir.vasl.navigationcomponentimpl.viewModels.CharacterViewModel;
import ir.vasl.navigationcomponentimpl.viewModels.EpisodeViewModel;
import ir.vasl.navigationcomponentimpl.viewModels.LocationViewModel;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ir.vasl.navigationcomponentimpl.utils.PublicValues.BASE_URL;

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    private static RetrofitClient instance;

    private RichAndMortyService richAndMortyService;

    public static RetrofitClient getInstance() {
        if (instance == null)
            instance = new RetrofitClient();
        return instance;
    }

    public RetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        richAndMortyService = retrofit.create(RichAndMortyService.class);
    }

    public void getCharacters(CharacterViewModel characterViewModel) {

        characterViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Loading));
        Log.i(TAG, "callAPI: -------------------------");

        Call<CharacterModel> characterModelCall = richAndMortyService.getCharacterList(PublicValues.START_PAGE);
        characterModelCall.enqueue(new Callback<CharacterModel>() {
            @Override
            public void onResponse(Call<CharacterModel> call, Response<CharacterModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse: " + response.toString());
                    characterViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Success));
                    characterViewModel.setLiveData(response.body().results);
                } else {
                    Log.d(TAG, "Response error " + response.raw().toString());
                    characterViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure));
                }
            }

            @Override
            public void onFailure(Call<CharacterModel> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
                characterViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure, t.getMessage()));
            }
        });
    }

    public void getLocations(LocationViewModel locationViewModel) {

        locationViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Loading));
        Log.i(TAG, "callAPI: -------------------------");

        Call<LocationModel> locationModelCall = richAndMortyService.getLocationList(PublicValues.START_PAGE);
        locationModelCall.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse: " + response.toString());
                    locationViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Success));
                    locationViewModel.setLiveData(response.body().results);
                } else {
                    Log.d(TAG, "Response error " + response.raw().toString());
                    locationViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure));
                }
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
                locationViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure, t.getMessage()));
            }
        });
    }

    public void getEpisodes(EpisodeViewModel episodeViewModel) {

        episodeViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Loading));
        Log.i(TAG, "callAPI: -------------------------");

        Call<EpisodeModel> episodeModelCall = richAndMortyService.getEpisodeList(PublicValues.START_PAGE);
        episodeModelCall.enqueue(new Callback<EpisodeModel>() {
            @Override
            public void onResponse(Call<EpisodeModel> call, Response<EpisodeModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse: " + response.toString());
                    episodeViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Success));
                    episodeViewModel.setLiveData(response.body().results);
                } else {
                    Log.d(TAG, "Response error " + response.raw().toString());
                    episodeViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure));
                }
            }

            @Override
            public void onFailure(Call<EpisodeModel> call, Throwable t) {
                Log.d(TAG, "Error " + t.getMessage());
                episodeViewModel.exposeNetworkLiveData().postValue(new NetworkResult(ResultEnum.Failure, t.getMessage()));
            }
        });
    }

}
