package me.alpha12.ecarnet.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.alpha12.ecarnet.R;
import me.alpha12.ecarnet.classes.Car;
import me.alpha12.ecarnet.classes.Model;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<Car> cars = new ArrayList<>();
    public Car currentCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Model model1 = new Model("Renault", "Clio 2.2", "1.5l DCI 65ch");
        Model model2 = new Model("Peugeot", "206+", "1.4l 70ch");
        Model model3 = new Model("Citroën", "Saxo", "1.0l 50ch");

        cars.add(new Car("71 AFB 34", model1));
        cars.add(new Car("CT 091 DQ", model2));
        cars.add(new Car("XX 180 TG", model3));

        changeCar(cars.get(0));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_gas) {

        } else if (id == R.id.nav_repair) {

        }

        closeDrawer();

        return true;
    }

    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void changeCar(Car newCar) {
        LinearLayout header = (LinearLayout) findViewById(R.id.drawer_header);
        ImageView brandImageView = (ImageView) findViewById(R.id.brand_image_view);

        currentCar = newCar;

        TextView drawerTitle = (TextView) findViewById(R.id.car_name);
        drawerTitle.setText(currentCar.model.brand+" "+currentCar.model.model);

        TextView drawerDesc = (TextView) findViewById(R.id.car_desc);
        drawerDesc.setText(currentCar.plateNum+" - "+currentCar.model.engine);

        switch (currentCar.model.brand){
            case "Renault":
                header.setBackgroundResource(R.drawable.background_renault);
                brandImageView.setImageResource(R.mipmap.ic_renault);
                break;
            case "Peugeot":
                header.setBackgroundResource(R.drawable.background_peugeot);
                brandImageView.setImageResource(R.mipmap.ic_peugeot);
                break;
            case "Citroën":
                header.setBackgroundResource(R.drawable.background_citroen);
                brandImageView.setImageResource(R.mipmap.ic_citroen);
                break;
        }

        LinearLayout carsLayout = (LinearLayout) findViewById(R.id.cars_icon_layout);
        carsLayout.removeViews(2, carsLayout.getChildCount() - 2);

        for (Car car : cars) {
            if (currentCar != null && !car.plateNum.equals(currentCar.plateNum)) {
                ImageView carImage = new ImageView(getBaseContext());
                switch (car.model.brand) {
                    case "Renault":
                        carImage.setImageResource(R.mipmap.ic_renault);
                        break;
                    case "Peugeot":
                        carImage.setImageResource(R.mipmap.ic_peugeot);
                        break;
                    case "Citroën":
                        carImage.setImageResource(R.mipmap.ic_citroen);
                        break;
                }

                int size = (int) getResources().getDimension(R.dimen.other_car_size);
                int spacing = (int) getResources().getDimension(R.dimen.other_car_spacing);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
                layoutParams.setMargins(spacing, 0, 0, 0);
                carImage.setLayoutParams(layoutParams);
                carImage.setClickable(true);
                carImage.setTag(car);

                carImage.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                ImageView view = (ImageView) v;
                                //overlay is black with transparency of 0x77 (119)
                                view.getDrawable().setColorFilter(getResources().getColor(R.color.colorAccentAlpha), PorterDuff.Mode.SRC_ATOP);
                                view.invalidate();
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                Car associatedCar = (Car) v.getTag();
                                changeCar(associatedCar);
                            }
                            case MotionEvent.ACTION_CANCEL: {
                                ImageView view = (ImageView) v;
                                //clear the overlay
                                view.getDrawable().clearColorFilter();
                                view.invalidate();
                                break;
                            }
                        }

                        return true;
                    }
                });

                carsLayout.addView(carImage);
            }
        }

        closeDrawer();
    }
}
