package devs.mulham.raee.sample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import com.project.kmitl57.beelife.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    static String Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button back = (Button)findViewById(R.id.button3);
        Button next = (Button)findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.super.finish();
                //startActivity(new Intent(MapsActivity.this,MainActivity4.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.super.finish();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        initialMap();
    }

    private void initialMap() {
        TextView Tag = (TextView)findViewById(R.id.textView4);
        Tag.setText(Search);
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("You Here"));

        Button button = (Button)findViewById(R.id.button2);
        //final EditText editText = (EditText)findViewById(R.id.editText3);
        final Geocoder geocoder = new Geocoder(this);
        //editText.setText(Search);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocationName(Search, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = list.get(0);
        MainActivity4.location=address;
        mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(address.getLatitude(),address.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(),address.getLongitude()),15f));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}
