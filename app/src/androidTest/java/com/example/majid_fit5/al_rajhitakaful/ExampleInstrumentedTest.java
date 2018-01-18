package com.example.majid_fit5.al_rajhitakaful;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.runner.AndroidJUnit4;

import com.example.majid_fit5.al_rajhitakaful.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful.data.RemoteDataSource;
import com.example.majid_fit5.al_rajhitakaful.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful.data.models.response.CurrentUserResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    RemoteDataSource remoteDataSource;
    private static int responseExpected;
    private CountingIdlingResource testIdlingResource;
    Context appContext;
    private String phoneNumber = "00966541909490"; // change it when you test.
    String token="empty";

    @Before
    public void init() {
        remoteDataSource = RemoteDataSource.getInstance();
        testIdlingResource = new CountingIdlingResource("Network_Call");
        appContext = InstrumentationRegistry.getTargetContext(); // Context of the app under test.
    }

    @Test
    public void isOTP() throws InterruptedException {
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
       // IdlingRegistry.getInstance().register(testIdlingResource);
        Espresso.registerIdlingResources(testIdlingResource);
    }

    @Test
    public void loginAPiServiceTest() throws InterruptedException {
        testIdlingResource.increment();
        String pinCode="62978"; //This will be sent to your phone.
        remoteDataSource.login(new LoginRequest(phoneNumber, pinCode), new DataSource.LoginCallback() {
            @Override
            public void onLoginResponse(CurrentUserResponse currentUser) {
                token=currentUser.getUser().getAuthToken();
                System.out.println("Token "+token);
                testIdlingResource.decrement();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                responseExpected=error.getCode();
                testIdlingResource.decrement();
            }
        });
        Espresso.registerIdlingResources(testIdlingResource);
    }
}
