package com.li.jacky.nestedscrollingdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.li.jacky.nestedscrollingdemo.view.NestedScrollingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        startActivity(new Intent(this, NestedScrollingActivity.class));
    }
}
