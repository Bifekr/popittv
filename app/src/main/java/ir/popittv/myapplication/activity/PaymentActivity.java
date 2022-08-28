package ir.popittv.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;

import java.util.Objects;

import ir.popittv.myapplication.R;

public class PaymentActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean status = false;
    private  String transactionId;
    private String amount;
    private String firstDate;
    private  String Lastdate;
    private int expire;
    //--------------

    ZarinPalBillingClient client=null;
    BillingClientStateListener billingClientStateListener;
    private final Long price=1000L;
    private final Long price2=2000L;
    private final Long price3=3000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PaymentActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        billingClientStateListener = new BillingClientStateListener() {
            @Override
            public void onClientSetupFinished(@NonNull ClientState clientState) {

            }

            @Override
            public void onClientServiceDisconnected() {

            }
        };


        FutureCompletionListener<Receipt> futureCompletionListener = new FutureCompletionListener<Receipt>() {
            @Override
            public void onComplete(TaskResult<Receipt> task) {
               status= Objects.requireNonNull(task.getSuccess()).isSuccess();
                amount= String.valueOf(task.getSuccess().getAmount());
                firstDate = task.getSuccess().getDate();
                transactionId = task.getSuccess().getTransactionID();
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (status){
                        editor.putBoolean("status", true);
                        editor.putInt("expire", expire);
                        editor.putString("amount",  amount);
                        editor.putString("firstDate",firstDate);
                        editor.putString("transactionId", transactionId);
                        editor.commit();
                        recreate();
                        Toast.makeText(PaymentActivity.this, "پرداخت موفق"+ firstDate, Toast.LENGTH_SHORT).show();
                    }else{
                        editor.putBoolean("status", false);
                        Toast.makeText(PaymentActivity.this, "khl,tr"+ expire, Toast.LENGTH_SHORT).show();
                    }
                });




            }
        } ;

        client = ZarinPalBillingClient.newBuilder(this)
                .enableShowInvoice()
                .setListener(billingClientStateListener)
                .build();

        Button code1=findViewById(R.id.btnCode1);
        Button code2=findViewById(R.id.btnCode2);
        Button code3=findViewById(R.id.btnCode3);
        code1.setOnClickListener(v->{
            expire =1;
            client.launchBillingFlow(getPurchaseAsPaymentRequest(price),futureCompletionListener);
        });
        code2.setOnClickListener(v->{
            expire =2;
            client.launchBillingFlow(getPurchaseAsPaymentRequest(price2),futureCompletionListener);
        });
        code3.setOnClickListener(v->{
            expire =3;
            client.launchBillingFlow(getPurchaseAsPaymentRequest(price3),futureCompletionListener);
        });

//-----------onCreate---------------------------------///
    }//-----------onCreate---------------------------------///


    private Purchase getPurchaseAsPaymentRequest(long price){

        String description = "Payment Request via ZarinPal SDK";
        String callback = "https://pikoboom.ir" ; // Your Server address


        //-----------
        String MERCHENT_ID = "6a5ecf11-5142-479f-940b-dc931a2a368c";
        return Purchase.newBuilder()

                .asPaymentRequest(MERCHENT_ID, price, callback, description)
                .build();
    }
}