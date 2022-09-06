package ir.popittv.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import ir.popittv.myapplication.R;
import kotlin.jvm.internal.Intrinsics;

public class PaymentActivity extends AppCompatActivity {



    private final Long price = 9000L;
    private final Long price2 = 25000L;
    private final Long price3 = 50000L;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ZarinPalBillingClient client ;
    BillingClientStateListener billingClientStateListener;
    FutureCompletionListener<Receipt> futureCompletionListener;
    //--------------
    TextView textView;
    private boolean status = false;
    private String transactionId;
    private String amount;
    private String firstDate;
    private int expire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = PaymentActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

textView=findViewById(R.id.textView5);
        billingClientStateListener = new BillingClientStateListener() {
            @Override
            public void onClientSetupFinished(@NonNull ClientState clientState) {

            }

            @Override
            public void onClientServiceDisconnected() {

            }
        };

   /*     (new Handler(Looper.getMainLooper())).post((Runnable)(new Runnable() {
            public final void run() {
                PaymentActivity.this.purchaseCompletedListener = (FutureCompletionListener)(new FutureCompletionListener() {
                    public void onComplete(@NotNull TaskResult task) {
                        Intrinsics.checkNotNullParameter(task, "task");
                        Log.d("InAppBilling Sample: ", "onComplete Receipt is " + task.isSuccess());
                        StringBuilder var10001 = (new StringBuilder()).append("onComplete Receipt is ");
                        Receipt var10002 = (Receipt)task.getSuccess();
                        Log.d("InAppBilling Sample: ", var10001.append(String.valueOf(var10002 != null ? var10002.getAmount() : null)).toString());
                        TextView var10000;
                        if (task.isSuccess()) {
                         //   var10000 = txtReceipt;
                         //   Intrinsics.checkNotNullExpressionValue(var10000, "txtReceipt");
                            Receipt var2 = (Receipt)task.getSuccess();
                        //    var10000.setText((CharSequence)String.valueOf(var2 != null ? var2.isSuccess() : null));
                         //   var10000 = txtReceipt2;
                            //   Intrinsics.checkNotNullExpressionValue(var10000, "txtReceipt2");
                            var2 = (Receipt)task.getSuccess();
                            //    var10000.setText((CharSequence)String.valueOf(var2 != null ? var2.getStatus() : null));
                           // var10000 = txtReceipt3;
                            //     Intrinsics.checkNotNullExpressionValue(var10000, "txtReceipt3");
                            var2 = (Receipt)task.getSuccess();
                            //      var10000.setText((CharSequence)String.valueOf(var2 != null ? var2.getTransactionID() : null));
                          //  var10000 = txtReceipt4;
                            //      Intrinsics.checkNotNullExpressionValue(var10000, "txtReceipt4");
                            var2 = (Receipt)task.getSuccess();
                            //      var10000.setText((CharSequence)String.valueOf(var2 != null ? var2.getAmount() : null));
                            Log.d("InAppBilling Sample: ", "onComplete Receipt is " + task.isSuccess());
                            var10001 = (new StringBuilder()).append("onComplete Receipt is ");
                            var10002 = (Receipt)task.getSuccess();
                            Log.d("InAppBilling Sample: ", var10001.append(String.valueOf(var10002 != null ? var10002.getAmount() : null)).toString());
                            var10001 = (new StringBuilder()).append("onComplete Receipt is ");
                            var10002 = (Receipt)task.getSuccess();
                            Log.d("InAppBilling Sample: ", var10001.append(String.valueOf(var10002 != null ? var10002.getStatus() : null)).toString());
                            var10001 = (new StringBuilder()).append("onComplete Receipt is ");
                            var10002 = (Receipt)task.getSuccess();
                            Log.d("InAppBilling Sample: ", var10001.append(String.valueOf(var10002 != null ? var10002.getTransactionID() : null)).toString());
                        } else {
                         //   var10000 = txtReceipt;
                            //     Intrinsics.checkNotNullExpressionValue(var10000, "txtReceipt");
                            var10001 = (new StringBuilder()).append("Receipt failed: \n");
                            Throwable var3 = task.getFailure();
                            //    var10000.setText((CharSequence)var10001.append(var3 != null ? var3.getMessage() : null).toString());
                        }

                    }

                    // $FF: synthetic method
                    // $FF: bridge method
                    public void onComplete(Object var1) {
                        this.onComplete((TaskResult)var1);
                    }
                });
            }
        }));*/
      //  (new Handler(Looper.getMainLooper())).post((Runnable)(new Runnable() {
new Handler(Looper.getMainLooper()).post(new Runnable() {
    @Override
    public void run() {
         futureCompletionListener = task -> {
         status = Objects.requireNonNull(task.getSuccess()).isSuccess();
          //  amount = String.valueOf(task.getSuccess().getAmount());
           // firstDate = task.getSuccess().getDate();
           // transactionId = task.getSuccess().getTransactionID();
             textView.setText(Objects.requireNonNull(task.getSuccess()).getStatus());
            Log.d("TAG", "onComplete Receipt is :"+task.getSuccess().getAmount());
            Log.d("TAG", "onComplete Receipt is " +status);
         //   long unixCurrentTime = System.currentTimeMillis();
            editor.putLong("unixCurrentTime",System.currentTimeMillis());
            editor.putBoolean("status", Objects.requireNonNull(task.getSuccess()).isSuccess());
            editor.putInt("expire", expire);
            editor.putString("amount", String.valueOf(task.getSuccess().getAmount()));
            editor.putString("firstDate", task.getSuccess().getDate());
            editor.putString("transactionId", task.getSuccess().getTransactionID());
            editor.commit();
            recreate();




        };
    }
});




        client = ZarinPalBillingClient.newBuilder(this)
                .enableShowInvoice()
                .setListener(billingClientStateListener)
                .build();

        Button code1 = findViewById(R.id.btnCode1);
        Button code2 = findViewById(R.id.btnCode2);
        Button code3 = findViewById(R.id.btnCode3);
        Button back = findViewById(R.id.btnBack_Payment);
        code1.setOnClickListener(v -> {
            expire = 1;
            client.launchBillingFlow(getPurchaseAsPaymentRequest(price), futureCompletionListener);
        });
        code2.setOnClickListener(v -> {
            expire = 2;
            client.launchBillingFlow(getPurchaseAsPaymentRequest(price2), futureCompletionListener);
        });
        code3.setOnClickListener(v -> {
            expire = 3;
            client.launchBillingFlow(getPurchaseAsPaymentRequest(price3), futureCompletionListener);
        });
        back.setOnClickListener(v -> {
            startActivity(new Intent(PaymentActivity.this, UserActivity.class));
        });

//-----------onCreate---------------------------------///
    }//-----------onCreate---------------------------------///


    private Purchase getPurchaseAsPaymentRequest(long price) {
        final String description = " پرداخت و دریافت اشتراک برای برنامه پیکوبوم ";
        final String callback = "https://pikoboom.ir/"; // Your Server address
        //-----------
        final String MERCHENT_ID = "6a5ecf11-5142-479f-940b-dc931a2a368c";
        return Purchase.newBuilder()

                .asPaymentRequest(MERCHENT_ID, price, callback, description)
                .build();
    }
}