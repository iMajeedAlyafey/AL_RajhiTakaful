package com.example.majid_fit5.al_rajhitakaful.createorder;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
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

public class CreateOrderMapFragment extends BaseFragment implements CreateOrderContract.View, OnMapReadyCallback, GoogleMap.OnCameraIdleListener, View.OnClickListener,LocationListener,DialogInterface.OnClickListener{
    private CreateOrderContract.Presenter mPresenter;
    private View mFragmentRootView, mBottomSheetView;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LatLng mCurrentLatLng,mCurrentMarkerPosition; // I have 2 LatLng, one for current position and the other for the marker.
    private MarkerOptions mMarkerOptions;
    private final int LOCATION_REQUEST_CODE =1111,CAMERA_REQUEST_CODE =2222;
    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LocationManager mLocationManager;
    private Button mBtnRequestHome, mBtnHideBottomSheet, mBtnRequestBottomSheet;
    private ImageView mImgView, ImgViewLogOut;
    private String mImagePath;
    private Cursor mCursor;

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
        ImgViewLogOut=mFragmentRootView.findViewById(R.id.map_frag_logout);
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
        mBottomSheetBehavior.setPeekHeight(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,600,getResources().getDisplayMetrics()));
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
        checkLocationPermission();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraIdleListener(this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setMyLocationEnabled(true); // icon of current location.
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
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        mMapView.getMapAsync(this); // needs to be here after getting permissions.
        checkIsGPSEnable(); // to check if the user disable GPS or not.
    }

    private void checkIsGPSEnable(){
        if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new AlertDialogUtility // my custom dialog..
                    (getActivity(),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.confirmation_msg),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.msg_gps_disabled),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.msg_turn_on),
                            AlRajhiTakafulApplication.getInstance().getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),1234,null);
                                }
                            },new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if(requestCode==1){
            if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getCurrentLocationCoordinates(); //gps call
                 else{
                    Toast.makeText( getActivity(), AlRajhiTakafulApplication.getInstance().getString(R.string.msg_location_permission_denied), Toast.LENGTH_LONG).show();
                    mBtnRequestHome.setEnabled(false);
                    mBtnRequestHome.setBackgroundColor(AlRajhiTakafulApplication.getInstance().getResources().getColor(R.color.Gray));
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
    /**
     * This is responsible on the marker on the screen. This may be changed by user.
     */
    @Override
    public void onCameraIdle() {
        mCurrentMarkerPosition=mGoogleMap.getCameraPosition().target;
        //Toast.makeText( getActivity(),"mCurrentMarkerPosition/////"+mCurrentMarkerPosition, Toast.LENGTH_LONG).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 13333:
                if(data!=null){
                    if (resultCode == Activity.RESULT_OK) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        mImgView.setImageBitmap(bitmap); // show the photo.
                        Uri tempUri = getImageUri(getActivity(), bitmap);
                        mImagePath = getRealPathFromURI(tempUri);
                        mCursor.close();
                    }
                }
                break;
            case 1234:

                break;
        }
        if (requestCode == 13333 && data != null) {

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        mCursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        mCursor.moveToFirst();
        return mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
    }

    @Override
    public void showLoading() {

    }
    @Override
    public void hideLoading() {

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
            mPresenter.uploadPhoto(order.getId(), mImagePath);
        }
        Intent intent = new Intent(getActivity(), WaitingProviderActivity.class);
        intent.putExtra(Constants.CURRENT_ORDER,order);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCreateOrderFailure(AlRajhiTakafulError error) {
        Snackbar.make(mFragmentRootView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_try_again), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onUploadPhotoSuccess(Order order) {
        Toast.makeText(AlRajhiTakafulApplication.getInstance(), getString(R.string.photo_updated), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUploadPhotoFailure(AlRajhiTakafulError error) {
        Toast.makeText(AlRajhiTakafulApplication.getInstance(), getString(R.string.photo_upload_fail), Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnLogOutSuccess(AlRajhiTakafulResponse response) {
        PrefUtility.destroyToken(AlRajhiTakafulApplication.getInstance());
        onDestroy();
        getActivity().finish();
    }

    @Override
    public void OnLogOutFailure(AlRajhiTakafulError error) {
        Snackbar.make(mFragmentRootView, AlRajhiTakafulApplication.getInstance().getString(R.string.msg_try_log_out_again), Snackbar.LENGTH_LONG).show();
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
    public void onLocationChanged(Location location) {
        mCurrentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMarkerOptions = new MarkerOptions()
                .position(mCurrentLatLng)
                .visible(false)
                .title(AlRajhiTakafulApplication.getInstance().getString(R.string.msg_current_location));
        mGoogleMap.addMarker(mMarkerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLatLng, 15.0f));
        mLocationManager.removeUpdates(this);
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
    public void onClick(DialogInterface dialog, int which) {

    }
}

