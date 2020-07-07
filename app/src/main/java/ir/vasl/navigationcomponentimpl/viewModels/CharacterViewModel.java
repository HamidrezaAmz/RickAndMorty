package ir.vasl.navigationcomponentimpl.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.vasl.navigationcomponentimpl.models.CharacterModel;
import ir.vasl.navigationcomponentimpl.repository.Repository;

public class CharacterViewModel extends NetworkViewModel {

    private MutableLiveData<List<CharacterModel.Result>> liveData = new MutableLiveData<>();

    public CharacterViewModel() {
        generateCharacters();
    }

    public LiveData<List<CharacterModel.Result>> getLiveData() {
        return liveData;
    }

    public void setLiveData(List<CharacterModel.Result> resultList) {
        this.liveData.postValue(resultList);
    }

    public void generateCharacters() {
        Repository.getInstance().getCharacters(this);
    }
}
