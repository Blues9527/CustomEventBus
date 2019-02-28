package com.example.lanhuajian.customeventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lanhuajian.customeventbus.eventbus.CustomEventBusBean;
import com.example.lanhuajian.customeventbus.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CustomEventBusBean("Blues", "hello!"));
                Log.i("Blues", "SecondActivity ->" + Thread.currentThread());
            }
        });
    }
}
