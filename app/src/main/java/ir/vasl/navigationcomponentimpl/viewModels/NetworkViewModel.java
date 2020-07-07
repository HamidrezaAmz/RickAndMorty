package ir.vasl.navigationcomponentimpl.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ir.vasl.navigationcomponentimpl.models.NetworkResult;

public class NetworkViewModel extends ViewModel {

    MutableLiveData<NetworkResult> liveData;

    public NetworkViewModel() {
        this.liveData = new MutableLiveData<>();
    }

    public MutableLiveData<NetworkResult> exposeNetworkLiveData() {
        return liveData;
    }

}
