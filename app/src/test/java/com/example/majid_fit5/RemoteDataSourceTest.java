package com.example.majid_fit5;

import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.RemoteDataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import static org.junit.Assert.*;;

import   	android.support.test.espresso.idling.CountingIdlingResource;
/**
 * Created by Eng. Abdulmajid Alyafey on 1/15/2018.
 */

public class RemoteDataSourceTest {

    RemoteDataSource remoteDataSource;
    private static final String phoneNumber = "00966541909490";
    private static int responseExpected;
    private CountingIdlingResource testIdlingResource;

    @Before
    public void init() {
        remoteDataSource = RemoteDataSource.getInstance();
        testIdlingResource = new CountingIdlingResource("Network_Call");
    }


    @Test
    public void otpAPiServiceTest() throws InterruptedException {
        testIdlingResource.increment();

        remoteDataSource.OtpCall(phoneNumber, new DataSource.OTPCallback() {
            @Override
            public void onOTPResponse(Response<AlRajhiTakafulResponse> responseCode) {
                responseExpected=responseCode.code();
                testIdlingResource.decrement();
            }
            @Override
            public void onFailure(AlRajhiTakafulError errorCode){
                responseExpected=errorCode.getCode();
                testIdlingResource.decrement();
            }
        });

        assertEquals(responseExpected,201);
    }



}
