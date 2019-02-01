package com.example.mika.loadingproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mickael.circleloading.CircleLoaderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation of the Circle Loading
        final CircleLoaderView loader = (CircleLoaderView) findViewById(R.id.loading_circle);

        // Button used to start the loading
        final Button buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.startLoading();
            }
        });

        // Button used to stop the loading
        final Button buttonStop = findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.stopLoading();
            }
        });
    }

}
