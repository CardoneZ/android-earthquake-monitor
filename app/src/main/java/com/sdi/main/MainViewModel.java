package com.sdi.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.sdi.Earthquake;
import com.sdi.api.RequestStatus;
import com.sdi.api.StatusWithDescription;
import com.sdi.database.EqDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository repository;
    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();
    private MutableLiveData<StatusWithDescription> statusMutableLiveData = new MutableLiveData<>();

    public LiveData<StatusWithDescription> getStatusMutableLiveData() {
        return statusMutableLiveData;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        EqDatabase database = EqDatabase.getDatabase(application);
        repository = new MainRepository(database);
    }
    public LiveData<List<Earthquake>> getEqList() {
        return repository.getEqList();
    }
    public void downloadEarthquakes() {
        statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.LOADING, ""));
        repository.downloadAndSaveEarthquakes(new MainRepository.DownloadStatusListener() {
            @Override
            public void downloadSuccess() {
                statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.DONE, ""));
            }

            @Override
            public void downloadError(String message) {
                statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.LOADING, message));
            }
        });
    }

//
//    public LiveData<List<Earthquake>> getEqList(){
//        return eqList;
//    }
//
//    List<Earthquake> list = new ArrayList<>();
//
//    public void getEarthquakes(){
//        repository.getEarthquakes(earthquakeList -> {
//            eqList.setValue(earthquakeList);
//        });
        /*
        list.add(new Earthquake("aaaa","CDMX",4.0,12365498L,105.23,98.127));
        list.add(new Earthquake("bbbb","La Paz",1.8,12365498L,105.23,98.127));
        list.add(new Earthquake("cccc","Barcelona",0.5,12365498L,105.23,98.127));
        list.add(new Earthquake("dddd","Buenos Aires",3.7,12365498L,105.23,98.127));
        list.add(new Earthquake("eeee","Washington D.C",2.8,12365498L,105.23,98.127));
        eqList.setValue(list);
        */
}
