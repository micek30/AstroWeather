package com.example.krzysiek.astro;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krzysiek.astro.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private SectionStatePagerAdapter sectionStatePagerAdapter;
    private SectionStatePagerAdapter2 sectionStatePagerAdapter2;
    private ViewPager viewPager;
    private TextView textView;

    private ActivityMainBinding activityMainBinding;

    static DateTime dateTime = new DateTime();
    static double latitude = 52;
    static double longitude = 21;
    static int refreshTime = 2000;


    static Sun sun = new Sun();
    static Moon moon = new Moon();

    static float ktore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.test);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        activityMainBinding.setDateTime(dateTime);

        if(textView.getText().equals("Phone"))
        sectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        else sectionStatePagerAdapter2 = new SectionStatePagerAdapter2(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        thread.start();
        thread2.start();
    }

    private void setupViewPager(ViewPager viewPager) {

        if(textView.getText().equals("Phone")) {
            SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new Fragment1(), "Fragment1");
            adapter.addFragment(new Fragment2(), "Fragment2");
            adapter.addFragment(new Fragment3(), "Fragment3");

            viewPager.setAdapter(adapter);
        }else{
            SectionStatePagerAdapter2 adapter = new SectionStatePagerAdapter2(getSupportFragmentManager());

            adapter.addFragment(new Fragment1(), "Fragment1");
            adapter.addFragment(new Fragment2(), "Fragment2");
            adapter.addFragment(new Fragment3(), "Fragment3");

            viewPager.setAdapter(adapter);
        }

    }

    Thread thread = new Thread() {

        private boolean stopNow = false;

        public void stopNow() {
            stopNow = true;
        }


        @Override
        public void run() {
            try {
                while (!interrupted() && !stopNow) {
                    Thread.sleep(1000);
                    if (!stopNow) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dateTime.refreshTime();
                                System.out.println(moon.getMoonPhase());
                            }
                        });
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    };

    Thread thread2 = new Thread() {

        @Override
        public void run() {
            try {
                while (!interrupted()) {
                    Thread.sleep(refreshTime);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            dateTime.refreshAllTime();
                            sun.refresh();
                            moon.refresh();
                        }
                    });
                }
            } catch (InterruptedException ignored) {
            }
        }
    };



    public void onClickMenu(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}



