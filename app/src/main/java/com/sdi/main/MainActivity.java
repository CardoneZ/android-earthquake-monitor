package com.sdi.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sdi.api.RequestStatus;
import com.sdi.earthquakes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel =
                new ViewModelProvider(this, new MainViewModelFactory(getApplication())).get(MainViewModel.class);


        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));

        EqAdapter adapter = new EqAdapter(this);
        adapter.setOnItemClickListener(earthquake ->
                Toast.makeText(MainActivity.this,earthquake.getPlace(),Toast.LENGTH_SHORT).show());
        binding.eqRecycler.setAdapter(adapter);

        Log.d("eq","ActivityMain: adapter = "+adapter.toString());

        viewModel.downloadEarthquakes();
        viewModel.getEqList().observe(this, adapter::submitList);
        viewModel.getStatusMutableLiveData().observe(this,status->{
            if(status.getStatus() == RequestStatus.LOADING){
                binding.loadingWheel.setVisibility(View.VISIBLE);
            }else{
                binding.loadingWheel.setVisibility(View.GONE);
            }
            if(status.getStatus() == RequestStatus.ERROR){
                Toast.makeText(this,status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}