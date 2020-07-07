package ir.vasl.navigationcomponentimpl.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ir.vasl.navigationcomponentimpl.models.LocationModel;
import ir.vasl.navigationcomponentimpl.repository.Repository;

public class LocationViewModel extends NetworkViewModel {

    private MutableLiveData<List<LocationModel.Result>> liveData = new MutableLiveData<>();

    public LocationViewModel() {
        generateLocations();
    }

    public LiveData<List<LocationModel.Result>> getLiveData() {
        return liveData;
    }

    public void setLiveData(List<LocationModel.Result> resultList) {
        this.liveData.postValue(resultList);
    }

    public void generateLocations() {
        Repository.getInstance().getLocations(this);
    }
}
