package com.example.lanhuajian.customeventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lanhuajian.customeventbus.eventbus.CustomEventBusBean;
import com.example.lanhuajian.customeventbus.eventbus.EventBus;
import com.example.lanhuajian.customeventbus.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        startActivity(new Intent(this, SecondActivity.class));
    }

    @Subscribe
    public void getMessage(final CustomEventBusBean customEventBusBean) {
        Log.i("Blues", "MainActivity->" + Thread.currentThread());
        Log.i("Blues", customEventBusBean.toString());

    }
}
