package ir.vasl.navigationcomponentimpl.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.vasl.navigationcomponentimpl.models.EpisodeModel;
import ir.vasl.navigationcomponentimpl.repository.Repository;

public class EpisodeViewModel extends NetworkViewModel {

    private MutableLiveData<List<EpisodeModel.Result>> liveData = new MutableLiveData<>();

    public EpisodeViewModel() {
        generateEpisodes();
    }

    public LiveData<List<EpisodeModel.Result>> getLiveData() {
        return liveData;
    }

    public void setLiveData(List<EpisodeModel.Result> resultList) {
        this.liveData.postValue(resultList);
    }

    public void generateEpisodes() {
        Repository.getInstance().getEpisodes(this);
    }
}
