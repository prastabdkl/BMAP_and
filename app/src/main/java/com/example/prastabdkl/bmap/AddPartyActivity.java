package com.example.prastabdkl.bmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static com.example.prastabdkl.bmap.R.id.toolbar;

/**
 * Created by Prastab Dhakal on 1/7/2017.
 */

public class AddPartyActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_party);

        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add Worker");
    }
}
