package com.sdi.main;

import androidx.lifecycle.LiveData;

import com.sdi.api.EarthquakeJSONResponse;
import com.sdi.api.Feature;
import com.sdi.api.ApiClient;
import com.sdi.Earthquake;
import com.sdi.database.EqDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private final EqDatabase database;

    public MainRepository(EqDatabase database) {
        this.database = database;
    }

    public LiveData<List<Earthquake>> getEqList() {
        return database.eqDAO().getEarthquakes();
    }

    public interface DownloadEqsListener{
        void onEqsDownloaded(List<Earthquake> eqList);
    }

    public interface DownloadStatusListener{
        void downloadSuccess();
        void downloadError(String message);
    }

    public void downloadAndSaveEarthquakes(DownloadStatusListener downloadStatusListener) {
        ApiClient.EqService service = ApiClient.getInstance().getService();
        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call,
                                   Response<EarthquakeJSONResponse> response) {
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                EqDatabase.databaseWriteExecutor.execute(() -> {
                    database.eqDAO().insertAll(earthquakeList);
                });

                downloadStatusListener.downloadSuccess();
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable throwable) {
                downloadStatusListener.downloadError(throwable.getMessage());
            }
        });
    }

        private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJSONResponse body) {
        List<Earthquake> eqList = new ArrayList<>();

        List<Feature> features = body.getFeatures();
        for (Feature feature: features) {
            String id = feature.getId();
            double magnitude = feature.getProperties().getMagnitude();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();

            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();
            Earthquake earthquake = new Earthquake(id, place, magnitude, time,
                    latitude, longitude);
            eqList.add(earthquake);
        }

        return eqList;
    }

    public void getEarthquakes(DownloadEqsListener downloadEqsListener) {
        ApiClient.EqService service = ApiClient.getInstance().getService();
        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse > call, Response<EarthquakeJSONResponse > response) {
                List<Earthquake> earthquakeList = getEarthquakesWithMoshi(response.body());
                downloadEqsListener.onEqsDownloaded(earthquakeList);
            }
            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) { }
        });
    }
}