package ir.popittv.myapplication.activity

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.zarinpal.ZarinPalBillingClient
import com.zarinpal.billing.purchase.Purchase
import com.zarinpal.client.BillingClientStateListener
import com.zarinpal.client.ClientState
import com.zarinpal.provider.core.future.FutureCompletionListener
import com.zarinpal.provider.core.future.TaskResult
import com.zarinpal.provider.model.response.Receipt
import ir.popittv.myapplication.R

class MainActivity2 : AppCompatActivity() {

    private var client: ZarinPalBillingClient? = null

    companion object {
        const val TAG = "InAppBilling Sample: "
    }

    private val price=1000L;
    private val price2=1000L;
    private val price3=3000L;

    private  var expire=4

    private lateinit var purchaseCompletedListener : FutureCompletionListener<Receipt>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val txtReceipt = findViewById<TextView>(R.id.txt_receipt)
        val txtReceipt2 = findViewById<TextView>(R.id.txt_receipt2)
        val txtReceipt3 = findViewById<TextView>(R.id.txt_receipt3)
        val txtReceipt4 = findViewById<TextView>(R.id.txt_receipt4)

        val billingClientStateListener = object : BillingClientStateListener {
            override fun onClientServiceDisconnected() {
                Log.d("TAG", "onClientServiceDisconnected")
            }

            override fun onClientSetupFinished(state: ClientState) {
                Log.d("TAG", "onClientSetupFinished ${state.name}")

            }
        }

        Handler(Looper.getMainLooper()).post {
            // write your code here
            purchaseCompletedListener = object : FutureCompletionListener<Receipt> {
                override fun onComplete(task: TaskResult<Receipt>) {
                    Log.d(TAG, "onComplete Receipt is ${task.isSuccess}")
                    Log.d(TAG, "onComplete Receipt is ${task.success?.amount.toString()}")
                    if (task.isSuccess) {
                        txtReceipt.text = task.success?.isSuccess.toString()
                        txtReceipt2.text = task.success?.status.toString()
                        txtReceipt3.text = task.success?.transactionID.toString()
                        txtReceipt4.text = task.success?.amount.toString()
                        Log.d(TAG, "onComplete Receipt is ${task.isSuccess}")
                        Log.d(TAG, "onComplete Receipt is ${task.success?.amount.toString()}")
                        Log.d(TAG, "onComplete Receipt is ${task.success?.status.toString()}")
                        Log.d(TAG, "onComplete Receipt is ${task.success?.transactionID.toString()}")
                    } else {

                        txtReceipt.text = "Receipt failed: \n" +
                                "${task.failure?.message}"
                    }

                    /*     if (task.isSuccess) {
                             txtReceipt.text = "Receipt: \n" +
                                     "Transaction id: ${task.success?.transactionID}\n" +
                                     "Amount: ${task.success?.amount}\n" +
                                     "Date: ${task.success?.date}\n" +
                                     "Status: ${task.success?.isSuccess}\n"
                             Log.d(TAG, "onComplete Receipt is ${task.isSuccess}")
                         } else {

                             txtReceipt.text = "Receipt failed: \n" +
                                     "${task.failure?.message}"
                         }*/

                }
            }
        }




        client = ZarinPalBillingClient.newBuilder(this)
            .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            .enableShowInvoice()
            .setListener(billingClientStateListener)
            .build()


        findViewById<View>(R.id.btn_payment_request).setOnClickListener {
            client?.launchBillingFlow(getPurchaseAsPaymentRequest3(price), purchaseCompletedListener)

           // Intent(this@MainActivity2,UserActivity::class.java).apply { startActivity(this) }

        }

        findViewById<View>(R.id.btn_authority).setOnClickListener {
          //  client?.launchBillingFlow(getPurchaseAsAuthority(), purchaseCompletedListener)
            client?.launchBillingFlow(getPurchaseAsPaymentRequest3(price2), purchaseCompletedListener)

        }

        findViewById<View>(R.id.btn_sku).setOnClickListener {
          //  client?.launchBillingFlow(getPurchaseAsSku(), purchaseCompletedListener)
            client?.launchBillingFlow(getPurchaseAsPaymentRequest3(price3), purchaseCompletedListener)

        }

        findViewById<View>(R.id.btn_sku2).setOnClickListener {


        }



    }



    private fun getPurchaseAsPaymentRequest3(log: Long): Purchase {
        val merchantId = "6a5ecf11-5142-479f-940b-dc931a2a368c"
        val description = "Payment Request via ZarinPal SDK"
        val callback = "https://pikoboom.ir/" // Your Server address


        return Purchase.newBuilder()
            .asPaymentRequest(merchantId, log, callback, description)
            .build()

    }


    private fun getPurchaseAsAuthority(): Purchase {

        val authority = "" // The authority that resolved from ZarinPal
        return Purchase.newBuilder()
            .asAuthority(authority)
            .build()

    }

    private fun getPurchaseAsSku(): Purchase {
        val sku = "" // sku created from ZarinPal
        return Purchase.newBuilder()
            .asSku(sku)
            .build()
    }


}