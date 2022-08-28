package ir.popittv.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.Task;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;

import java.util.List;
import java.util.Objects;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.adapter.AllChannel_Adapter;
import ir.popittv.myapplication.adapter.FunnyAdapter;
import ir.popittv.myapplication.adapter.RvChannel_Frg1;
import ir.popittv.myapplication.databinding.ActivityUserBinding;
import ir.popittv.myapplication.models.ChannelDataModel;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.Service;
import ir.popittv.myapplication.utils.OnClickFrg1;
import ir.popittv.myapplication.utils.OnClickFunny;
import ir.popittv.myapplication.viewmodel.UserViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements OnClickFunny , OnClickFrg1 {

    ActivityUserBinding binding;
    View bottomView;
    View bottomView2;
    TextInputLayout et_phone;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private UserViewModel userViewModel;
    private FunnyAdapter funnyAdapter;
    private FunnyAdapter funnyAdapter2;
    private FunnyAdapter funnyAdapter3;
    private FunnyAdapter funnyAdapter4;
    private AllChannel_Adapter channel_adapter;
    private RvChannel_Frg1 funnyAdapter5;
    private String name_user;
    private String phone_user;
    private String code_user;
    private int id_user;
    private final boolean userLoged = true;
    private boolean b_switchLink;

    //-----------
    private final String MERCHENT_ID="6a5ecf11-5142-479f-940b-dc931a2a368c";
    ZarinPalBillingClient client=null;
    BillingClientStateListener billingClientStateListener;
    private Long price=1000L;
    private Long price2=2000L;
    private Long price3=3000L;
    private int expier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = UserActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);

        phone_user = sharedPreferences.getString("phone_user", null);
        name_user = sharedPreferences.getString("name_user", null);
        id_user = sharedPreferences.getInt("id_user", 0);

        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        funnyAdapter = new FunnyAdapter(this, this);
        funnyAdapter2 = new FunnyAdapter(this, this);
        funnyAdapter3 = new FunnyAdapter(this, this);
        funnyAdapter4 = new FunnyAdapter(this, this);
        funnyAdapter5 = new RvChannel_Frg1(this, this);

        btnClickOnCreate();
        initRailActivity();
        intRvUser();

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
           boolean amunt= Objects.requireNonNull(task.getSuccess()).isSuccess();

           new Handler(Looper.getMainLooper()).post(new Runnable() {
               @Override
               public void run() {
                   if (amunt){

                       Toast.makeText(UserActivity.this, "پرداخت موفق"+expier, Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(UserActivity.this, "khl,tr"+expier, Toast.LENGTH_SHORT).show();
                   }
               }
           });
           Log.i("TAG", "onComplete: "+amunt);



       }
   } ;

        client = ZarinPalBillingClient.newBuilder(this)
                .enableShowInvoice()
                .setListener(billingClientStateListener)
                .build();


        binding.btnPayment.setOnClickListener(v -> {
            if (phone_user!=null) {
                startActivity(new Intent(UserActivity.this, MainActivity2.class));

            }else {
                loginUser();
            }
        });

        int expire2= getIntent().getIntExtra("expire",0);
        if (expire2!=0) {
            Toast.makeText(this, "" + expire2, Toast.LENGTH_SHORT).show();
        }

        userViewModel.request_userLater(id_user, 1);
        userViewModel.request_userLike(id_user,1);
        userViewModel.request_userSee(id_user,1);
        userViewModel.request_userSave(id_user,1);
        userViewModel.request_userSub(id_user);

        userSaveMenuClick(id_user);
        userLaterMenuClick(id_user);
        userSeeMenuClick(id_user);
        userLikeMenuClick(id_user);

        getUserSub();
        getUserSave();
        getUserLater();
        getUserSee();
        getUserLike();



    }

    private Purchase getPurchaseAsPaymentRequest(long price){

            String description = "Payment Request via ZarinPal SDK";
            String callback = "https://pikoboom.ir" ; // Your Server address


            return Purchase.newBuilder()

                    .asPaymentRequest(MERCHENT_ID, price, callback, description)
                    .build();
        }


    private void btnClickOnCreate() {

        binding.avatarUserUserActivity.setOnClickListener(v -> {
            loginUser();
        });
        if (phone_user!=null) {
            binding.phoneNumUserActivity.setText(phone_user);
            binding.userNameUserActivity.setText(name_user);
            binding.avatarUserUserActivity.setBackgroundResource(R.drawable.trophy);
            binding.tvExitUserActivity.setText(R.string.exite);
            binding.tvEnter1RvSubUser.setVisibility(View.GONE);

        } else
        {
            binding.phoneNumUserActivity.setText(R.string.hint_number);
            binding.userNameUserActivity.setText(R.string.hint_user_name);
            binding.tvExitUserActivity.setText(R.string.enter);
            binding.tvEnter1RvSubUser.setVisibility(View.VISIBLE);
            loginUser();

        }
        binding.tvEnter1RvSubUser.setOnClickListener(v -> {
            loginUser();
            binding.tvEnter1RvSubUser.setVisibility(View.GONE);
        });
        binding.tvExitUserActivity.setOnClickListener(v -> {
            sharedPreferences = this.getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor22 = sharedPreferences.edit();
            editor22.putString("phone_user", null);
            editor22.apply();
            recreate();
            Toast.makeText(getApplicationContext(), "شما از حساب خود خارج شدید", Toast.LENGTH_SHORT).show();
        });
    }


    private void getUserSub() {
        userViewModel.getUserSub().observe(this, new Observer<List<ChannelDataModel>>() {
            @Override
            public void onChanged(List<ChannelDataModel> channelDataModels) {
                funnyAdapter5.setData(channelDataModels);
            }
        });
    }




    private void getUserLike(){
        userViewModel.getUserLike().observe(UserActivity.this,funnyDataModels -> {
            funnyAdapter4.setData(funnyDataModels);
        });
    }
    private void userLikeMenuClick(int id_user) {
    }

    //////////////user see/////////////////
    private void getUserSee() {
        userViewModel.getUserSee().observe(UserActivity.this, funnyDataModels -> {
            funnyAdapter3.setData(funnyDataModels);

        });
    }

    private void userSeeMenuClick(int id_user) {
        binding.menuFunnyHistoryUser.setOnClickListener(v -> {

            userViewModel.request_userSee(id_user, 1);
            binding.menuFunnyHistoryUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuRealityHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiHistoryUser.setBackgroundResource(R.drawable.shape_tag1);

        });
        binding.menuRealityHistoryUser.setOnClickListener(v -> {
            userViewModel.request_userSee(id_user, 2);
            binding.menuFunnyHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityHistoryUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuStudyHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
        });

        binding.menuStudyHistoryUser.setOnClickListener(v -> {
            userViewModel.request_userSee(id_user, 3);
            binding.menuFunnyHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyHistoryUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuFarsiHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
        });

        binding.menuFarsiHistoryUser.setOnClickListener(v -> {
            userViewModel.request_userSee(id_user, 4);
            binding.menuFunnyHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyHistoryUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiHistoryUser.setBackgroundResource(R.drawable.shape_tag2);
        });

    }

///////////////////////////////////////////



    /////////////////user later///////////////
    private void getUserLater() {
        userViewModel.getUserLater().observe(UserActivity.this, funnyDataModels -> {

            funnyAdapter2.setData(funnyDataModels);

        });
    }

    private void userLaterMenuClick(int id_user) {
        binding.menuFunnyWatchLaterUser.setOnClickListener(v -> {

            userViewModel.request_userLater(id_user, 1);
            binding.menuFunnyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuRealityWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);

        });
        binding.menuRealityWatchLaterUser.setOnClickListener(v -> {
            userViewModel.request_userLater(id_user, 2);
            binding.menuFunnyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityWatchLaterUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuStudyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
        });

        binding.menuStudyWatchLaterUser.setOnClickListener(v -> {
            userViewModel.request_userLater(id_user, 3);
            binding.menuFunnyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuFarsiWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
        });

        binding.menuFarsiWatchLaterUser.setOnClickListener(v -> {
            userViewModel.request_userLater(id_user, 4);
            binding.menuFunnyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyWatchLaterUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiWatchLaterUser.setBackgroundResource(R.drawable.shape_tag2);
        });

    }

////////////////////user save////////////////////////
    private void getUserSave() {
        userViewModel.getUserSave().observe(this, funnyDataModels -> {


            funnyAdapter.setData(funnyDataModels);


        });
    }

    private void userSaveMenuClick(int id_user) {
        binding.menuFunnyBookmarkUser.setOnClickListener(v -> {

            userViewModel.request_userSave(id_user, 1);
            binding.menuFunnyBookmarkUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuRealityBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);

        });
        binding.menuRealityBookmarkUser.setOnClickListener(v -> {
            userViewModel.request_userSave(id_user, 2);
            binding.menuFunnyBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityBookmarkUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuStudyBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
        });

        binding.menuStudyBookmarkUser.setOnClickListener(v -> {
            userViewModel.request_userSave(id_user, 3);
            binding.menuFunnyBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyBookmarkUser.setBackgroundResource(R.drawable.shape_tag2);
            binding.menuFarsiBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
        });

        binding.menuFarsiBookmarkUser.setOnClickListener(v -> {
            userViewModel.request_userSave(id_user, 4);
            binding.menuFunnyBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuRealityBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuStudyBookmarkUser.setBackgroundResource(R.drawable.shape_tag1);
            binding.menuFarsiBookmarkUser.setBackgroundResource(R.drawable.shape_tag2);
        });
    }

    /////////////////

    private void intRvUser() {


        //RecyclerView User channel
        binding.rvSaveChannelUserActivity.setLayoutManager(new LinearLayoutManager(UserActivity.this, RecyclerView.HORIZONTAL, false));
        binding.rvSaveChannelUserActivity.setAdapter(funnyAdapter5);

        binding.rvLikeVideoUserActivity.setLayoutManager(new LinearLayoutManager(UserActivity.this,LinearLayoutManager.HORIZONTAL,false));
        binding.rvLikeVideoUserActivity.setAdapter(funnyAdapter4);
        //RecyclerView user Save Video
        binding.rvBookMarkVideoUserActivity.setLayoutManager(new LinearLayoutManager(UserActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvBookMarkVideoUserActivity.setAdapter(funnyAdapter);

        //RecyclerView User WatchLater
        binding.rvWatchLaterUserActivity.setLayoutManager(new LinearLayoutManager(UserActivity.this, RecyclerView.HORIZONTAL, false));
        binding.rvWatchLaterUserActivity.setAdapter(funnyAdapter2);

        //RecyclerView User History (see)
        binding.rvHistoryVideoUserActivity.setLayoutManager(new LinearLayoutManager(UserActivity.this, RecyclerView.HORIZONTAL, false));
        binding.rvHistoryVideoUserActivity.setAdapter(funnyAdapter3);

    }

    private void loginUser() {
        String check = sharedPreferences.getString("phone_user", "");
        if (check.equals("")) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
            bottomView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
            bottomSheetDialog.setContentView(bottomView);
            BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from((View) bottomView.getParent());
            sheetBehavior.setPeekHeight(

                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics())
            );


            bottomSheetDialog.show();
            et_phone = bottomView.findViewById(R.id.et_phone_userProfile);
            Button btn_send = bottomView.findViewById(R.id.send_customLogin);
            ProgressBar progressBar = bottomView.findViewById(R.id.progress_dialog);


            btn_send.setOnClickListener(V -> {

                if (et_phone.getEditText().getText().toString().equals("")) {
                    et_phone.setError("شماره را وارد کنید");

                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    phone_user = et_phone.getEditText().getText().toString();
                    editor = sharedPreferences.edit();
                    editor.putString("phone_user", phone_user);
                    editor.apply();
                    Service.getApiClient().userLogin(phone_user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            bottomSheetDialog.dismiss();

                            BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(UserActivity.this);
                            bottomView2 = getLayoutInflater().inflate(R.layout.coustom_dialog_code, null);
                            bottomSheetDialog2.setContentView(bottomView2);
                            bottomSheetDialog2.show();

                            TextInputLayout et_code = bottomView2.findViewById(R.id.et_code_userProfile);
                            Button btn_code = bottomView2.findViewById(R.id.sendCode_customLogin);
                            btn_code.setOnClickListener(v1 -> {

                                if (et_code.getEditText().getText().toString().equals("")) {
                                    et_code.setError("کد را وارد کنید");

                                } else {
                                    code_user = et_code.getEditText().getText().toString();
                                    Service.getApiClient().getUser(phone_user, code_user).enqueue(new Callback<UserDataModel>() {
                                        @Override
                                        public void onResponse(Call<UserDataModel> call1, @NonNull Response<UserDataModel> response1) {
                                            UserDataModel userDataModel = response1.body();
                                            if (response1.isSuccessful()) {
                                                assert userDataModel!=null;
                                                id_user = userDataModel.getUser_id();
                                                name_user = userDataModel.getName();
                                                editor = sharedPreferences.edit();
                                                editor.putString("name_user", name_user);
                                                editor.putInt("id_user", id_user);
                                                editor.apply();
                                                bottomSheetDialog2.dismiss();
                                                binding.phoneNumUserActivity.setText(phone_user);
                                                binding.userNameUserActivity.setText(name_user);
                                                binding.avatarUserUserActivity.setBackgroundResource(R.drawable.trophy);
                                                binding.tvExitUserActivity.setText(R.string.exite);
                                                binding.tvEnter1RvSubUser.setVisibility(View.GONE);

                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<UserDataModel> call1, Throwable t) {
                                            Toast.makeText(UserActivity.this, "wrong", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(UserActivity.this, "send again", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            });
        } else {
            String news = sharedPreferences.getString("phone_user", "");
            Toast.makeText(this, "قبلا وارد شده اید" + news, Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("NonConstantResourceId")
    private void initRailActivity() {
        binding.navRail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Funny:
                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                    break;
                case R.id.Reality:
                    startActivity(new Intent(UserActivity.this, RealityActivity.class));
                    break;
                case R.id.Learning:
                    startActivity(new Intent(UserActivity.this, StudyActivity.class));
                    break;
                case R.id.Farsi:
                    startActivity(new Intent(UserActivity.this, FarsiActivity.class));
                    break;
                case R.id.Games:
                    startActivity(new Intent(UserActivity.this, GameActivity.class));
                    break;
            }
            return true;
        });

        Objects.requireNonNull(binding.navRail.getHeaderView()).findViewById(R.id.fab_add).setOnClickListener(v -> {
            startActivity(new Intent(UserActivity.this, MainActivity.class));
            // binding.navRail.getHeaderView().setBackgroundResource(R.drawable.ic_close);
        });

             /*  binding.navRail.setOnItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Funny:
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    break;
                case R.id.Reality:
                    startActivity(new Intent(MainActivity.this, RealityActivity.class));
                    break;
                case R.id.Learning:
                    startActivity(new Intent(MainActivity.this, StudyActivity.class));
                    break;
                case R.id.Farsi:
                    startActivity(new Intent(MainActivity.this, FarsiActivity.class));
                    break;
                case R.id.Games:
                    startActivity(new Intent(MainActivity.this, GameActivity.class));
                    break;


            }
        });*/
    }

    @Override
    public void onClickSave(int id_vid) {

    }

    @Override
    public void onClickSee(int id_vid) {

    }

    @Override
    public void onClickLike(int id_vid) {

    }

    @Override
    public void onClickLater(int id_vid) {

    }

    @Override
    public void onClickSub(int id_channel) {

    }

    @Override
    public void onClickPlayer(int id_vid_funny, int id_channel, int kind) {
        Intent intent = new Intent(UserActivity.this, PlayerActivity.class);
        intent.putExtra("id_vid_funny",id_vid_funny);
        intent.putExtra("kind",kind);
        intent.putExtra("id_channel",id_channel);
        startActivity(intent);
    }

    @Override
    public void OnclickDetail(int pos) {

    }

    @Override
    public void onMenuClick(int position) {

    }

    @Override
    public void onRow_index(int position) {

    }
}