package ir.vasl.navigationcomponentimpl.repository.Retrofit;

import ir.vasl.navigationcomponentimpl.models.CharacterModel;
import ir.vasl.navigationcomponentimpl.models.EpisodeModel;
import ir.vasl.navigationcomponentimpl.models.LocationModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RichAndMortyService {

    @GET("character/")
    Call<CharacterModel> getCharacterList(@Query("page") int value);

    @GET("location/")
    Call<LocationModel> getLocationList(@Query("page") int value);

    @GET("episode/")
    Call<EpisodeModel> getEpisodeList(@Query("page") int value);

}
