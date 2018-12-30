package com.example.driesvandievoort.android;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.Favorite;
import com.example.driesvandievoort.android.Entities.User;
import com.example.driesvandievoort.android.Places.PlacesData;
import com.example.driesvandievoort.android.Varia.CurrentUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CategoryActivity extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private TextView categoryTitle;
    private ImageView imageView;
    private GoogleMap googlemap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 3000;
    double latitude,longitude;
    private static final String DATABASE_NAME = "app_db";
    public AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryTitle = (TextView)findViewById(R.id.textTitleCategory);
        imageView = (ImageView)findViewById(R.id.categoryThumbnail);

        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        int Image = intent.getExtras().getInt("Image");

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        categoryTitle.setText(Title);
        imageView.setImageResource(Image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapCategory);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        googlemap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,getString(R.string.PermissionDenied) , Toast.LENGTH_LONG).show();
                }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googlemap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            googlemap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();

    }


    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        currentLocationmMarker = googlemap.addMarker(markerOptions);
        googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googlemap.animateCamera(CameraUpdateFactory.zoomBy(8));

        if(googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }


    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        googlePlaceUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlaceUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlaceUrl.append("&query=").append(nearbyPlace);
        googlePlaceUrl.append("&key="+getString(R.string.google_android_map_api_key));

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }


    public void checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }

        }
    }

    public void onClickFavorite(View view)
    {
        new favoriteTask().execute();
    }



    private class favoriteTask extends AsyncTask<String,Integer,Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            Intent intent = getIntent();
            String Title = intent.getExtras().getString("Title");
            int Image = intent.getExtras().getInt("Image");
            Favorite favorite = new Favorite(CurrentUser.currentUser.getUsername(), Title);
            Favorite getFavorite = appDatabase.favoriteDAO().getFavorite(favorite.getUsername(), favorite.getCategoryName());
            if (getFavorite == null) {
                appDatabase.favoriteDAO().insertFavorite(favorite);
                return 1;
            } else if (getFavorite.getCategoryName().equals(favorite.getCategoryName()) && getFavorite.getUsername().equals(favorite.getUsername())) {
                appDatabase.favoriteDAO().deleteFavorite(getFavorite);
                return 0;
            } else {
                return 0;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 1) {
                Toast.makeText(getApplicationContext(), getString(R.string.favoriteSaved), Toast.LENGTH_SHORT).show();
            } else if (integer == 0) {
                Toast.makeText(getApplicationContext(), getString(R.string.favoriteDeleted), Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[2];
        PlacesData getNearbyPlacesData = new PlacesData();

        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        //Bedoeling om eventueel uit te breiden
        switch(v.getId())
        {
            case R.id.B_restaurants:
                googlemap.clear();
                String url = getUrl(latitude, longitude, Title + "Restaurant");
                dataTransfer[0] = googlemap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(CategoryActivity.this, getString(R.string.NearbyRestaurants), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
