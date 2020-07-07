package ir.vasl.navigationcomponentimpl.repository;

import ir.vasl.navigationcomponentimpl.repository.Retrofit.RetrofitClient;
import ir.vasl.navigationcomponentimpl.viewModels.CharacterViewModel;
import ir.vasl.navigationcomponentimpl.viewModels.EpisodeViewModel;
import ir.vasl.navigationcomponentimpl.viewModels.LocationViewModel;

public class Repository {

    private static Repository instance;

    private static final String TAG = "Repository";

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public void getCharacters(CharacterViewModel characterViewModel) {
        RetrofitClient.getInstance().getCharacters(characterViewModel);
    }

    public void getLocations(LocationViewModel locationViewModel) {
        RetrofitClient.getInstance().getLocations(locationViewModel);
    }

    public void getEpisodes(EpisodeViewModel episodeViewModel) {
        RetrofitClient.getInstance().getEpisodes(episodeViewModel);
    }

}
