package com.zcj.SlideMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.zcj.SlideMenu.view.SlideMenu;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_back;
    private SlideMenu slideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });
    }
}
