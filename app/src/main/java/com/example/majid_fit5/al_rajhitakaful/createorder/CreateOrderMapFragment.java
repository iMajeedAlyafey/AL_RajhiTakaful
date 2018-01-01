package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateOrderMapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener{
    private View mRootView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Double [] mCoordinates =new Double[]{0.0d,0.0d}; //default
    LatLng mCurrentLatLng ;
    Marker mMarker;
    MarkerOptions markerOptions;
    private final int REQUEST_CODE_ASK_PERMISSIONS=1111;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_map, container, false);
        init();
        return mRootView;
    }
    private void init() {
        mMapView = mRootView.findViewById(R.id.map);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) { // called after onCreateView
        super.onViewCreated(view, savedInstanceState);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            checkLocationPermission(); // for getting the current location.
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        markerOptions= new MarkerOptions()
                .position(mCurrentLatLng)
                .visible(false)
                .title(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_current_location));
        mMarker=mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng,16f)); // for zooming.
        mGoogleMap.setMyLocationEnabled(true); // icon of current location.
    }

    private void checkLocationPermission() {
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }getCurrentLocationCoordinates();
    }

    private void getCurrentLocationCoordinates() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         mCoordinates[0]= mLocation.getLatitude();
         mCoordinates[1]= mLocation.getLongitude();
         mCurrentLatLng = new LatLng(mCoordinates[0],mCoordinates[1]);
         mMapView.getMapAsync(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocationCoordinates(); //gps call
                } else {
                    Toast.makeText( getActivity(), AlRajhiTakafulApplication.getInstance().getString(R.string.msg_permission_denied), Toast.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onCameraIdle() {
        mGoogleMap.clear();
        mGoogleMap.addMarker(markerOptions.position(mGoogleMap.getCameraPosition().target));
    }


}
