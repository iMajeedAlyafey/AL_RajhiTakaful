package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;
import com.example.majid_fit5.al_rajhitakaful.base.BaseFragment;
import com.example.majid_fit5.al_rajhitakaful.base.Injection;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.OrderRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.design.widget.Snackbar;

public class CreateOrderMapFragment extends BaseFragment implements CreateOrderContract.View, OnMapReadyCallback, GoogleMap.OnCameraIdleListener, View.OnClickListener{
    private CreateOrderContract.Presenter mPresenter;
    private View mFragmentRootView, mBottomSheetView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Double [] mCoordinates =new Double[]{0.0d,0.0d}; //default
    private LatLng mCurrentLatLng ;
    private Marker mMarker;
    private MarkerOptions mMarkerOptions;
    private final int LOCATION_REQUEST_CODE =1111;
    private final int CAMERA_REQUEST_CODE =2222;
    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Button mBtnRequestHome, mBtnHideBottomSheet,mBtnRequestBottomSheet;
    private ImageView mImgView;
    private Uri mImgUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CreateOrderPresenter(Injection.provideDataRepository());
        mPresenter.onBind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentRootView = inflater.inflate(R.layout.fragment_map, container, false);
        init();
        return mFragmentRootView;
    }
    private void init() {
        mMapView = mFragmentRootView.findViewById(R.id.map);
        mBtnRequestHome = mFragmentRootView.findViewById(R.id.but_request); // mBtnRequestHome
        mBtnRequestHome.setOnClickListener(this);
        initiateBottomSheet();
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
        mMarkerOptions = new MarkerOptions()
                .position(mCurrentLatLng)
                .visible(false)
                .title(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_current_location));
        mMarker=mGoogleMap.addMarker(mMarkerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng,16f)); // for zooming.
        mGoogleMap.setMyLocationEnabled(true); // icon of current location.
    }
    /**
     * This is responsible on creating bottom sheet in home screen.
     * The mBottomSheetDialog needs ACTIVITY to be created, not fragment.
     */
    private void initiateBottomSheet() {
        mBottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_layout,null);
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(mBottomSheetView);
        mBottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetView.getParent());
        mBottomSheetBehavior.setPeekHeight(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,600,getResources().getDisplayMetrics()));
        mBtnRequestBottomSheet= mBottomSheetView.findViewById(R.id.btn_request2);// buttons inside bottom sheet
        mBtnRequestBottomSheet.setOnClickListener(this);
        mImgView = mBottomSheetView.findViewById(R.id.img);// image inside bottom sheet
        mImgView.setOnClickListener(this);
        mBtnHideBottomSheet = mBottomSheetView.findViewById(R.id.btn_hide); // buttons inside bottom sheet
        mBtnHideBottomSheet.setOnClickListener(this);
    }
    private void checkLocationPermission() {
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
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
            case LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getCurrentLocationCoordinates(); //gps call
                 else
                    Toast.makeText( getActivity(), AlRajhiTakafulApplication.getInstance().getString(R.string.msg_location_permission_denied), Toast.LENGTH_LONG).show();
                break;
            case CAMERA_REQUEST_CODE:
                boolean ok=true;
                for (int i = 0; i < permissions.length; i++) { // 2 permissions..
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        ok=false;
                }
                    if(ok) takePhoto();
                    else  Snackbar.make(mBottomSheetView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_camera_permission_denied), Snackbar.LENGTH_LONG).show();
                break;
        }
    }
    @Override
    public void onCameraIdle() {
        mGoogleMap.clear();
        mCurrentLatLng=mGoogleMap.getCameraPosition().target;
        mGoogleMap.addMarker(mMarkerOptions.position(mCurrentLatLng));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_request: // of home
                mBottomSheetDialog.show();
                break;
            case R.id.btn_hide: // of bottom sheet
                mBottomSheetDialog.cancel();
            if(mImgUri!=null)
                break;
            case R.id.btn_request2: // of bottom sheet
                mBottomSheetDialog.cancel();
                mPresenter.createOrder(new OrderRequest((float)mCurrentLatLng.latitude,(float)mCurrentLatLng.longitude));
                Snackbar.make(mFragmentRootView, "Request is sent successfully", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.img: // of bottom sheet
              checkPhotoPermission();
                break;
        }
    }
    private void checkPhotoPermission() {
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                return ;
            }
        }takePhoto();
    }
    private void takePhoto() {
        Intent intent= new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(intent,13333);// If  request code >= 0, this code will be returned in requestCode in onActivityResult() when the activity exits.
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==13333 &&data != null) {
            if(resultCode== Activity.RESULT_OK ) {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data"); // key "data" is for photo..
            mImgView.setImageBitmap(bitmap); // show the photo.
            mImgUri=getImageUri(getActivity(),bitmap);// to get URI of photo, first compressIt , save it and get the path..
            }
        }
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {//This takes context and bitmap, save the photo and return its Uri to get it later in any time.
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "img", null);
        return Uri.parse(path);
    }

    @Override
    public void showLoading() {

    }
    @Override
    public void hideLoading() {

    }

    @Override
    public void onCreateOrderSuccess(Order order) {
        Snackbar.make(mFragmentRootView, "Order id is:"+ order.getId(), Snackbar.LENGTH_LONG).show();

        // clear the injection before going to next activity.
    }

    @Override
    public void onCreateOrderFailure(AlRajhiTakafulError error) {
        Snackbar.make(mFragmentRootView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_try_again)+". Error code"+error.getCode(), Snackbar.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
