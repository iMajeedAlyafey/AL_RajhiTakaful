package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
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
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.utility.AlertDialogUtility;
import com.example.majid_fit5.al_rajhitakaful.utility.Constants;
import com.example.majid_fit5.al_rajhitakaful.utility.PrefUtility;
import com.example.majid_fit5.al_rajhitakaful.waiting.WaitingProviderActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.design.widget.Snackbar;

import java.io.ByteArrayOutputStream;

public class CreateOrderMapFragment extends BaseFragment implements CreateOrderContract.View, OnMapReadyCallback, GoogleMap.OnCameraIdleListener, View.OnClickListener, LocationListener, GpsStatus.Listener {
    private CreateOrderContract.Presenter mPresenter;
    private View mFragmentRootView, mBottomSheetView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LatLng mCurrentLatLng, mCurrentMarkerPosition; // I have 2 LatLng, one for current position and the other for the marker.
    private MarkerOptions mMarkerOptions;
    private final int LOCATION_REQUEST_CODE = 1111, CAMERA_REQUEST_CODE = 2222;
    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LocationManager mLocationManager;
    private Button mBtnRequestHome, mBtnHideBottomSheet, mBtnRequestBottomSheet;
    private ImageView mImgView, ImgViewLogOut;
    private String mImagePath;
    private Cursor mCursor;
    private ProgressDialog mProgressDialog;

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
        init(); // needs to be here after mFragmentRootView.
        return mFragmentRootView;
    }

    private void init() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mMapView = mFragmentRootView.findViewById(R.id.map);
        ImgViewLogOut = mFragmentRootView.findViewById(R.id.map_frag_logout);
        ImgViewLogOut.setOnClickListener(this);
        mBtnRequestHome = mFragmentRootView.findViewById(R.id.but_request); // mBtnRequestHome
        mBtnRequestHome.setOnClickListener(this);
        initiateBottomSheet();
    }

    /**
     * This is responsible on creating bottom sheet in home screen.
     * The mBottomSheetDialog needs ACTIVITY to be created, not fragment.
     */
    private void initiateBottomSheet() {
        mBottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_layout,null);
        mBottomSheetDialog=new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(mBottomSheetView);
        mBottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetView.getParent());
        mBottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,600,getResources().getDisplayMetrics()));
        mBtnRequestBottomSheet= mBottomSheetView.findViewById(R.id.btn_request2);// buttons inside bottom sheet
        mBtnRequestBottomSheet.setOnClickListener(this);
        mImgView = mBottomSheetView.findViewById(R.id.img);// image inside bottom sheet
        mImgView.setOnClickListener(this);
        mBtnHideBottomSheet = mBottomSheetView.findViewById(R.id.btn_hide); // buttons inside bottom sheet
        mBtnHideBottomSheet.setOnClickListener(this);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) { // called after onCreateView
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(null);
        mMapView.onResume();
        if(savedInstanceState==null) // this is to solve the problem of showing 2 permissions one by one (Rare Case).
        checkLocationPermission(); // here important because it uses above controls.
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {// this is called when calling --> getMapAsync(this);
        mGoogleMap = googleMap;
        requestLocationUpdates();// call the interface methods...
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                return;
            }
        }
        checkIsGPSEnable(); // most important part.
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    checkIsGPSEnable();
                else{
                    Snackbar.make(mFragmentRootView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_location_permission_denied), Snackbar.LENGTH_LONG).show();
                    disableRequestButton();
                }
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

    private void checkIsGPSEnable(){ // Needs to be called after initializing the location manager.
        if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) // GPS in on..
            mMapView.getMapAsync(this); // only in this place prepare the map.

        else{ // GPS is off --Prompt the user for enabling it and WATCH for his request. The result of his actions is delivered to startActivityForResult().
            disableRequestButton(); // to prevent use from requesting.
            new AlertDialogUtility // my custom dialog..
                    (getActivity(),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.confirmation_msg),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.msg_gps_disabled_asking),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.msg_turn_on),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() { // positive button
                                @Override
                                public void onClick(DialogInterface dialog, int id) { // OK
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1234, null);
                                }},
                            new DialogInterface.OnClickListener() { // negative button
                                @Override
                                public void onClick(DialogInterface dialog, int id) { // the user cancel the pop up.
                                    if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){ // in case the user turn on the GPS directly by himself and click cancel.
                                        mMapView.getMapAsync(CreateOrderMapFragment.this);
                                    }
                                    else Snackbar.make(mFragmentRootView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_gps_disabled), Snackbar.LENGTH_LONG).show();
                                }
                            });
        }
    }
    private void requestLocationUpdates() { // this fires the functions of the Location Listener Interface.
        mLocationManager.addGpsStatusListener(this); // Important to put after it requestLocationUpdates(); , or it will not work.
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        enableRequestButton();
    }
    /**
     * This is responsible on the marker on the screen. This may be changed by user.
     */
    @Override
    public void onCameraIdle() {
        mCurrentMarkerPosition=mGoogleMap.getCameraPosition().target; // to get the position of the marker on the map.
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_request: // of home
                mBottomSheetDialog.show();
                break;
            case R.id.btn_hide: // of bottom sheet
                mBottomSheetDialog.cancel();
                break;
            case R.id.btn_request2: // of bottom sheet and responsible on creating new request.
                mBottomSheetDialog.cancel();
                mPresenter.createOrder(new OrderRequest((float)mCurrentMarkerPosition.latitude,(float)mCurrentMarkerPosition.longitude));
                break;
            case R.id.img: // of bottom sheet
                checkPhotoPermission();
                break;
            case R.id.map_frag_logout: // of bottom sheet
                confirmLogoutDialog();
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

    /**
     * This function gets the results from 2 intents : 1- Camera Intent , 2- Settings Intent.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 13333: //Camera Intent
                if(data!=null && resultCode == Activity.RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mImgView.setImageBitmap(bitmap); // show the photo.
                    Uri tempUri = getImageUri(getActivity(), bitmap);
                    mImagePath = getRealPathFromURI(tempUri);
                    mCursor.close();
                }break;
            case 1234://Settings Intent.
                if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { // after opening the setting to the user.
                    mMapView.getMapAsync(this);
                } else
                    Snackbar.make(mFragmentRootView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_gps_disabled), Snackbar.LENGTH_LONG).show();
                break;
        }
    }
    private void disableRequestButton() {
        mBtnRequestHome.setEnabled(false);
        mBtnRequestHome.setBackgroundColor(AlRajhiTakafulApplication.getInstance().getResources().getColor(R.color.Gray));
    }
    private void enableRequestButton() {
        mBtnRequestHome.setEnabled(true);
        mBtnRequestHome.setBackground(AlRajhiTakafulApplication.getInstance().getDrawable(R.drawable.background_green_no_corner));
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri uri) {
        mCursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        mCursor.moveToFirst();
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
    }
    @Override
    public void showLoading() {
        if(mProgressDialog==null){
            mProgressDialog= ProgressDialog.show(getActivity(),"","See You Later",false,false);
            mProgressDialog.setProgressDrawable(AlRajhiTakafulApplication.getInstance().getDrawable(R.drawable.custom_progressbar));
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_dialog);
        }else{
            mProgressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        mProgressDialog.cancel();
    }
    /**
     * Here after getting new order, we will redirect user to waiting activity, but we will check for photo if the URI is null, it means the user did not take photo, else make call to upload the photo.
     * @param order
     */
    @Override
    public void onCreateOrderSuccess(Order order) {
        Log.e("order", " and number is order :" + order.getId());
        if (mImagePath != null && !mImagePath.trim().isEmpty()) { // upload the photo while we redirect user to waiting activity.
            Log.e("PATH", mImagePath);
            mPresenter.uploadPhoto(order.getId(), mImagePath);//
        }
        Intent intent = new Intent(getActivity(), WaitingProviderActivity.class);
        intent.putExtra(Constants.CURRENT_ORDER,order);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCreateOrderFailure(AlRajhiTakafulError error) {
        Snackbar.make(mFragmentRootView, error.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onUploadPhotoSuccess(Order order) {
        Toast.makeText(AlRajhiTakafulApplication.getInstance(), AlRajhiTakafulApplication.getInstance().getString(R.string.photo_updated), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUploadPhotoFailure(AlRajhiTakafulError error) {
        Toast.makeText(AlRajhiTakafulApplication.getInstance(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnLogOutSuccess(AlRajhiTakafulResponse response) {
        hideLoading();
        PrefUtility.destroyToken(AlRajhiTakafulApplication.getInstance());
        onDestroy();
        getActivity().finish();
    }

    @Override
    public void OnLogOutFailure(AlRajhiTakafulError error) {
        hideLoading();
        Snackbar.make(mFragmentRootView, error.getMessage(), Snackbar.LENGTH_LONG).show();
        Log.e("",error.getMessage()+"--"+error.getCode());
    }

    private void confirmLogoutDialog() {
        new AlertDialogUtility // my custom dialog..
                (getActivity(),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.confirmation_msg),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.log_out_confirm_msg),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.log_out),
                        AlRajhiTakafulApplication.getInstance().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                showLoading();
                                mPresenter.logOut();
                            }
                        },new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) { // this is fired by calling requestLocationUpdates();
        mCurrentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
        mCurrentMarkerPosition=mCurrentLatLng; // Important...
        zoomInToCurrentLocation();
    }

    private void zoomInToCurrentLocation() { // this may be used in other place.z
        if(mGoogleMap!=null) {
            mMarkerOptions = new MarkerOptions()
                    .position(mCurrentLatLng)
                    .visible(false)
                    .title(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_current_location));
            mGoogleMap.setMyLocationEnabled(true); // icon of current location.
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mGoogleMap.addMarker(mMarkerOptions);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 15.0f));
            mLocationManager.removeUpdates(this); // if you not code this, you will get to your position every time you move the camera.
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {

    }
    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onGpsStatusChanged(int i) {
        switch (i) {
            case GpsStatus.GPS_EVENT_STARTED:
                if(mGoogleMap!=null)
                    enableRequestButton();
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                if(mGoogleMap!=null)
                    if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    Toast.makeText(AlRajhiTakafulApplication.getInstance(), AlRajhiTakafulApplication.getInstance().getString(R.string.msg_gps_disabled), Toast.LENGTH_SHORT).show();
                    disableRequestButton();
                }
                break;
        }
    }

}

