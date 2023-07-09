package com.example.deliverymy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliverymy.activities.BikeRideActivity;
import com.example.deliverymy.activities.FeedbackActivity;
import com.example.deliverymy.activities.InsurancePolicyActivity;
import com.example.deliverymy.activities.InviteFriendsActivity;
import com.example.deliverymy.activities.LoginActivity;
import com.example.deliverymy.activities.MapTestActivity;
import com.example.deliverymy.activities.MessageActivity;
import com.example.deliverymy.activities.MyWallet;
import com.example.deliverymy.activities.NotificationActivity;
import com.example.deliverymy.activities.OrderHistoryActivity;
import com.example.deliverymy.activities.PickUpDetailActivity;
import com.example.deliverymy.activities.PromotionsActivity;
import com.example.deliverymy.activities.RideActivity;
import com.example.deliverymy.activities.TermsAndCondition;
import com.example.deliverymy.activities.ViewProfileActivity;
import com.example.deliverymy.adapters.ViewPagerAdapter;
import com.example.deliverymy.adapters.ViewPagerAdapter1;
import com.example.deliverymy.utils.CheckInternetConection;
import com.example.deliverymy.utils.DialogClasses;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

import static androidx.core.view.GravityCompat.START;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager, viewPagerCustomer;
    TabLayout tabLayout;
    ImageView myrideimg, dispatchimg, shoppingimg, helperimg;
    TextView headerpointval,points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerCustomer = findViewById(R.id.viewPagerCustomer);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        myrideimg = findViewById(R.id.myrideimg);
        shoppingimg = findViewById(R.id.shoppingimg);
        dispatchimg = findViewById(R.id.dispatchimg);
        helperimg = findViewById(R.id.helperimg);
        points = findViewById(R.id.points);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        ViewPagerAdapter1 viewPagerAdapter1 = new ViewPagerAdapter1(this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerCustomer.setAdapter(viewPagerAdapter1);

        tabLayout.setupWithViewPager(viewPager, true);

        if (!CheckInternetConection.isInternetConnection(MainActivity.this)) {
            DialogClasses.showDialogInternetAlert(MainActivity.this);
        } else {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new MyTimerTask(), 5000, 4000);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.royalblue));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        // get user name and email textViews

        headerpointval = headerView.findViewById(R.id.headerpointval);


        headerpointval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewProfileActivity.class));
            }
        });

        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyWallet.class));
            }
        });

        myrideimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RideActivity.class));
            }
        });

        helperimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BikeRideActivity.class));
            }
        });


        shoppingimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PickUpDetailActivity.class));
            }
        });
        dispatchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PickUpDetailActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Do your other stuff here


        MenuItem item = menu.getItem(0);

//        Log.d("showdata", "onCreateOptionsMenu:gender "+gender);
        /*if (gender.equalsIgnoreCase("male"))
        {
            item.setIcon(getResources().getDrawable(R.drawable.boyicon));
        }
        else {
            item.setIcon(getResources().getDrawable(R.drawable.girlicon));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /*Intent intent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(intent);*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            /*Intent intent = new Intent(MainActivity.this,UserProfile.class);
            startActivity(intent);*/
        } else if (id == R.id.myWallet) {
            startActivity(new Intent(MainActivity.this, MyWallet.class));

        } else if (id == R.id.nav_myjobs) {
            /*startActivity(new Intent(MainActivity.this, MyJobs.class));*/
            startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));

        } else if (id == R.id.notifications) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_message) {
            Intent intent = new Intent(MainActivity.this, MessageActivity.class);
            startActivity(intent);
        } else if (id == R.id.feedback) {
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.invitefriends) {
            startActivity(new Intent(MainActivity.this, InviteFriendsActivity.class));
        } else if (id == R.id.promotions) {
            startActivity(new Intent(MainActivity.this, PromotionsActivity.class));
        }else if (id == R.id.nav_insurance) {
            startActivity(new Intent(MainActivity.this, InsurancePolicyActivity.class));
        }else if (id == R.id.nav_termsconditions) {
            startActivity(new Intent(MainActivity.this, TermsAndCondition.class));
        }else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(START);
        return true;
    }


    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                        viewPagerCustomer.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                        viewPagerCustomer.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                        viewPagerCustomer.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                        viewPagerCustomer.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4) {
                        viewPager.setCurrentItem(5);
                        viewPagerCustomer.setCurrentItem(5);
                    } else if (viewPager.getCurrentItem() == 5) {
                        viewPager.setCurrentItem(6);
                        viewPagerCustomer.setCurrentItem(6);
                    } else if (viewPager.getCurrentItem() == 6) {
                        viewPager.setCurrentItem(7);
                        viewPagerCustomer.setCurrentItem(0);
                    }/*else if (viewPager.getCurrentItem() == 7){
                        viewPager.setCurrentItem(8);
                    }*/ else {
                        viewPager.setCurrentItem(0);
                        viewPagerCustomer.setCurrentItem(0);
                    }
                }
            });

        }
    }
}