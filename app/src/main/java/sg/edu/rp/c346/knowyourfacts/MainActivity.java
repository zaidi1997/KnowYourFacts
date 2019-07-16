package sg.edu.rp.c346.knowyourfacts;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int reqCode = 12345;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String fragmentID = "fragmentID" ;


    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    ViewPager vPager;
    Button readLater;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vPager = findViewById(R.id.viewpager1);
        readLater = findViewById(R.id.readLater);

        FragmentManager fm = getSupportFragmentManager();

        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());
        al.add(new Frag3());

        adapter = new MyFragmentPagerAdapter(fm, al);

        vPager.setAdapter(adapter);

        readLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND,300);

                Intent intent = new Intent(MainActivity.this, ScheduledNotificationReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                Toast.makeText(MainActivity.this, "You will be reminded in 5mins", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Reset:
                vPager.setCurrentItem(0, true);
                Toast.makeText(this, "Reset to first page", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.previous:
                if (vPager.getCurrentItem() > 0){
                    int previousPage = vPager.getCurrentItem() - 1;
                    vPager.setCurrentItem(previousPage, true);
                }
                return true;
            case R.id.random:
                int currentPage = vPager.getCurrentItem();
                int random = getRandomPage();

                if(currentPage == random) {
                    random = getRandomPage();
                }

                vPager.setCurrentItem(random, true);
                return true;
            case R.id.next:
                int max = vPager.getChildCount();
                if (vPager.getCurrentItem() < max-1){
                    int nextPage = vPager.getCurrentItem() + 1;
                    vPager.setCurrentItem(nextPage, true);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        int currentPage = vPager.getCurrentItem();

        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(fragmentID, currentPage);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        int currentPage = sharedPreferences.getInt(fragmentID, 0);

        vPager.setCurrentItem(currentPage, false);
        super.onResume();
    }

    public int getRandomPage(){
        int minNum = 0;
        int maxNum = 2;
        int random = new Random().nextInt((maxNum - minNum) + 1) + minNum;
        return random;
    }

}
