package com.example.majid_fit5;

import com.example.majid_fit5.al_rajhitakaful_TEST.data.DataSource;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.RemoteDataSource;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.AlRajhiTakafulError;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.order.Order;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.request.LoginRequest;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.request.OrderRequest;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.response.AlRajhiTakafulResponse;
import com.example.majid_fit5.al_rajhitakaful_TEST.data.models.response.CurrentUserResponse;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.CountDownLatch;

import retrofit2.Response;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemoteDataSourceJunitTest {
    private static String token;
    private static String orderID;
    private static String pinCode = "9218"; //Put the pin you got here...
    private RemoteDataSource remoteDataSource;
    private CountDownLatch latch;
    private String phoneNumber; // change it when you test.
    private int acualResponse;
    private Order order;
    private CurrentUserResponse userResponse;

    @Before
    public void init() {
        remoteDataSource = RemoteDataSource.getInstance();
        latch = new CountDownLatch(1);//1 thread witch is Retrofit thread.
        phoneNumber = "966566391001"; // change it when you test.
    }

    @Ignore
    @Test
    public void AgetOTPApiServiceTest() throws InterruptedException {
        remoteDataSource.OtpCall(phoneNumber, new DataSource.OTPCallback() {
            @Override
            public void onOTPResponse(Response<AlRajhiTakafulResponse> responseCode) {
                acualResponse = responseCode.code();
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError errorCode) {
                acualResponse = errorCode.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(201, acualResponse);
    }

    /**
     * If you do not have the token, you need to execute getOTPApiServiceTest() test case to git pin code and execute this test case after that.
     *
     * @throws InterruptedException
     */

    @Test
    public void BloginAPiServiceTest() throws InterruptedException {
        remoteDataSource.login(new LoginRequest(phoneNumber, pinCode), new DataSource.LoginCallback() {
            @Override
            public void onLoginResponse(CurrentUserResponse currentUser) {
                token = currentUser.getUser().getAuthToken();
                System.out.println("Token " + token);
                remoteDataSource.changeTokenInRequestHeader(token);
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                acualResponse = error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(401, acualResponse);
        assertNotNull(token);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     *
     * @throws InterruptedException
     */
    @Test
    public void CcreateOrderAPiServiceTest() throws InterruptedException {
//        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.createOrder(new OrderRequest(0.0f, 0.0f), new DataSource.CreateOrderCallback() {
            @Override
            public void onCreateOrderResponse(Order currentOrder) {
                order = currentOrder;
                orderID = currentOrder.getId();
                System.out.println("New Order ID: " + currentOrder.getId());
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                acualResponse = error.getCode();
                System.out.println("onFailure :: acualResponse " + acualResponse);
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(401, acualResponse);
        assertNotNull(order);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     *
     * @throws InterruptedException
     */
    @Test
    public void DgetCurrentUserAPiServiceTest() throws InterruptedException {
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.getCurrentUser(new DataSource.GetCurrentUserCallBack() {
            @Override
            public void onGetCurrentUser(CurrentUserResponse currentUser) {
                userResponse = currentUser;
                orderID = currentUser.getCurrentOrder().getId();
                System.out.println("Token: " + userResponse.getUser().getAuthToken());
                System.out.println("Order ID:  " + userResponse.getCurrentOrder().getId());
                acualResponse = 201;
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                acualResponse = error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(401, acualResponse);
        assertNotNull(userResponse);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * You need to execute getCurrentUserAPiServiceTest() test case in order to get the order id, so you can use it to delete the order.
     *
     * @throws InterruptedException
     */
    @Test
    public void EgetOrderApiServiceTest() throws InterruptedException {
//        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
//        orderID = "AF7914"; // put the id of the order here after calling getCurrentUserAPiServiceTest() test case.
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.getOrder(orderID, new DataSource.GetOrderCallBack() {
            @Override
            public void onGetOrder(Order currentOrder) {
                order = currentOrder;
                System.out.println("Order ID: " + order.getId());
                System.out.println("Order Status:  " + order.getStatus());
                acualResponse = 200;
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                acualResponse = error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertNotNull(order);
        assertEquals(200, acualResponse);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * You need to execute getCurrentUserAPiServiceTest() test case in order to get the order id, so you can use it to delete the order.
     *
     * @throws InterruptedException
     */
    @Test
    public void FcancelOrderApiServiceTest() throws InterruptedException {
//        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
//        orderID = "AF7914"; // put the id of the order here after calling getCurrentUserAPiServiceTest() test case.
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.cancelOrderC(orderID, new DataSource.CancelOrderCallBack() {
            @Override
            public void onOrderCanceled() {
                acualResponse = 204;
                System.out.println("Order is deleted successfully");
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                acualResponse = error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(204, acualResponse);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     *
     * @throws InterruptedException
     */
    @Test
    public void GlogoutAPiServiceTest() throws InterruptedException {
//        token="aDGQt5Gfq5UP2esN9zZ0"; // put the token here ..
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.logout(new DataSource.LogoutCallback() {
            @Override
            public void onLogoutResponse(Response<AlRajhiTakafulResponse> response) {
                acualResponse = response.code();
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                acualResponse = error.getCode();
                System.out.println("onFailure :: acualResponse " + acualResponse);
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(acualResponse, 204);
    }
}