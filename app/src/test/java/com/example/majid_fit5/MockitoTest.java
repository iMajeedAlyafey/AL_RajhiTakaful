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
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import retrofit2.Response;
import static org.junit.Assert.*;

public class MockitoTest {
    private RemoteDataSource remoteDataSource;
    private CountDownLatch latch ;
    private String phoneNumber; // change it when you test.
    private String token = "";
    private int responseExpected;
    private Order order;
    private String orderID;
    private CurrentUserResponse userResponse;

    @Before
    public void init() {
        remoteDataSource = RemoteDataSource.getInstance();
        latch= new CountDownLatch(1);//1 thread witch is Retrofit thread.
        phoneNumber = "00966541909490"; // change it when you test.
    }

    @Test
    public void getOTPApiServiceTest() throws InterruptedException {
        remoteDataSource.OtpCall(phoneNumber, new DataSource.OTPCallback() {
            @Override
            public void onOTPResponse(Response<AlRajhiTakafulResponse> responseCode) {
                responseExpected = responseCode.code();
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError errorCode) {
                responseExpected = errorCode.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(responseExpected, 201);
    }

    /**
     * If you do not have the token, you need to execute getOTPApiServiceTest() test case to git pin code and execute this test case after that.
     * @throws InterruptedException
     */
    @Test
    public void loginAPiServiceTest() throws InterruptedException {
        String pinCode = "3922"; //Put the pin you got here...
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
                responseExpected = error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(responseExpected, 401);
        assertNotNull(token);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * @throws InterruptedException
     */
    @Test
    public void logoutAPiServiceTest() throws InterruptedException {
        token="aDGQt5Gfq5UP2esN9zZ0"; // put the token here ..
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.logout(new DataSource.LogoutCallback() {
            @Override
            public void onLogoutResponse(Response<AlRajhiTakafulResponse> response) {
                responseExpected = response.code();
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                responseExpected = error.getCode();
                System.out.println("onFailure :: responseExpected " + responseExpected);
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(responseExpected, 204);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * @throws InterruptedException
     */
    @Test
    public void createOrderAPiServiceTest() throws InterruptedException {
        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.createOrder(new OrderRequest(0.0f, 0.0f), new DataSource.CreateOrderCallback() {
            @Override
            public void onCreateOrderResponse(Order currentOrder) {
                order = currentOrder;
                System.out.println("New Order ID: " + order.getId());
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                responseExpected = error.getCode();
                System.out.println("onFailure :: responseExpected " + responseExpected);
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(responseExpected, 401);
        assertNotNull(order);
    }
    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * @throws InterruptedException
     */
    @Test
    public void getCurrentUserAPiServiceTest() throws InterruptedException {
        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.getCurrentUser(new DataSource.GetCurrentUserCallBack() {
            @Override
            public void onGetCurrentUser(CurrentUserResponse currentUser) {
                userResponse=currentUser;
                System.out.println("Token: " + userResponse.getUser().getAuthToken());
                System.out.println("Order ID:  " + userResponse.getCurrentOrder().getId());
                responseExpected=201;
                latch.countDown();
            }

            @Override
            public void onFailure(AlRajhiTakafulError error) {
                responseExpected=error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(responseExpected, 401);
        assertNotNull(userResponse);
        assertEquals(userResponse.getUser().getAuthToken(),token);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * You need to execute getCurrentUserAPiServiceTest() test case in order to get the order id, so you can use it to delete the order.
     * @throws InterruptedException
     */
    @Test
    public void cancelOrderApiServiceTest() throws InterruptedException{
        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
        orderID = "AF7914"; // put the id of the order here after calling getCurrentUserAPiServiceTest() test case.
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.cancelOrderC(orderID, new DataSource.CancelOrderCallBack() {
            @Override
            public void onOrderCanceled() {
                responseExpected=204;
                System.out.println("Order is deleted successfully");
                latch.countDown();
            }
            @Override
            public void onFailure(AlRajhiTakafulError error) {
                responseExpected=error.getCode();
                latch.countDown();
            }
        });
        latch.await();
        assertNotEquals(responseExpected,401);
        assertNotEquals(responseExpected,404);
        assertEquals(responseExpected,204);
    }

    /**
     * Executing loginAPiServiceTest() test case is a MUST, in order to get the token.
     * You need to execute getCurrentUserAPiServiceTest() test case in order to get the order id, so you can use it to delete the order.
     * @throws InterruptedException
     */
    @Test
    public void getOrderApiServiceTest()throws InterruptedException{
        token="8Fk3J9Q1Bpb38FhobAx"; // put the token here ..
        orderID = "AF7914"; // put the id of the order here after calling getCurrentUserAPiServiceTest() test case.
        remoteDataSource.changeTokenInRequestHeader(token);// temporary to change the token in the header of the request.
        remoteDataSource.getOrder(orderID, new DataSource.GetOrderCallBack() {
            @Override
            public void onGetOrder(Order currentOrder) {
                order=currentOrder;
                System.out.println("Order ID: " +order.getId());
                System.out.println("Order Status:  " + order.getStatus());
                responseExpected=200;
                latch.countDown();
            }
            @Override
            public void onFailure(AlRajhiTakafulError error) {
                responseExpected=error.getCode();
                latch.countDown();
            }});
        latch.await();
        assertNotNull(order);
        assertEquals(responseExpected,200);
        assertNotEquals(responseExpected,401);
    }

}