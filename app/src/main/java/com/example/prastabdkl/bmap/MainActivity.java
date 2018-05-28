package com.example.prastabdkl.bmap;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.prastabdkl.bmap.Adapter.ViewPagerAdapter;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle; //HamBurger Icon on the toolbar for the navigation bar
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    TabLayout tabLayout;
    SyncDatabase syncDatabase;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // signInUser();
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        sync_me(this);



//        //add framelayout in the home screen
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_container, new SignInFragment(), "Home")
//                .addToBackStack(null)
//                .commit();
        //getSupportActionBar().setTitle("Home");

        /*
        *
        * Navigation View
        *
        * */
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
                    public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()) {
                    case R.id.sign_in :
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, new SignInFragment())
                                .addToBackStack(null)
                                .commit();
                        item.setCheckable(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_id :
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new HomeFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.backup_id :
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new BackupFragment());
                        fragmentTransaction.commit();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.setting_id :
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });

        /*
        *
        * Control Tabs System
        *
        * */
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("PAYABLE"));
        tabLayout.addTab(tabLayout.newTab().setText("RECEIVABLE"));
        tabLayout.addTab(tabLayout.newTab().setText("WORKERS"));

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        viewPagerAdapter.addFragements(new PayableFragment(),"Payable");
        viewPagerAdapter.addFragements(new ReceivableFragment(),"Receivable");
        viewPagerAdapter.addFragements(new Wages(),"Wages");
        viewPager.setAdapter(viewPagerAdapter);
        //tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
    }

    /*
    * This method controls the menu in the toolbar
    * onCreateOptionMenu() and onOptionsItemSelected()
    *
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),AddWorkerActivity.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;

            case R.id.item2:
                Intent intent1 = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
                startActivity(intent1);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void signInUser()
//    {
//        JsonObject json = new JsonObject();
//        json.addProperty("email", "admin@example.com");
//        json.addProperty("password", "password");
//
//        Ion.with(getApplicationContext())
//                .load("https://serene-badlands-22797.herokuapp.com/api/v1/authenticate")
//                .setJsonObjectBody(json)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        if (result != null) {
//                            if (result.get("auth_token") != null){
//                                String str = result.get("auth_token").getAsString();
//                                int req_id = result.get("req_id").getAsInt();
//                                TokenAndId tokenAndId = new TokenAndId(str, req_id);
//                                Toast.makeText(getApplicationContext(), "Logged in successfully.", Toast.LENGTH_LONG).show();
//                                UserProfileFragment userProfileFragment = new UserProfileFragment();
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.main_container, new UserProfileFragment())
//                                        .addToBackStack(null)
//                                        .commit();
//                            }
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }

    void sync_me(Context context){
        System.out.println("sync database is going to call");
        syncDatabase = new SyncDatabase(context);
        syncDatabase.sync();
        System.out.println("called");
    }
}
