package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateOrderMapFragment extends BaseFragment implements OnMapReadyCallback {
    private View mView;
    private GoogleMap mGoogleMap;
    private MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        init();
        return mView;
    }

    private void init() {
        mMapView = mView.findViewById(R.id.map);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) { // called after onCreateView
        super.onViewCreated(view, savedInstanceState);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MapsInitializer.initialize(getActivity().getApplicationContext());
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng stc = new LatLng(24.758565,46.712784);
        mGoogleMap.addMarker(new MarkerOptions()
                .position(stc)
                .title("Morni"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stc,16f)); // for zooming.
    }
}
