package ir.popittv.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

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

public class UserActivity extends AppCompatActivity implements OnClickFunny, OnClickFrg1 {

    private final boolean userLoged = true;
    //-----------
    private final Long month_1 = 2600000000L;
    private final Long month_3 = 7860000000L;
    private final Long month_6 = 15592000000L;
    ActivityUserBinding binding;
    View bottomView;
    View bottomView2;
    TextInputLayout et_phone;
    TextInputLayout et_code;
    String phone_user2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
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
    private String transactionId;
    private String firstDate;
    private Long lastDate;
    private String amount;
    private int expire;
    private Long expireDate = null;
    private long unixCurrentTime;
    private boolean status;
    private boolean b_switchLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = UserActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        phone_user = sharedPreferences.getString("phone_user", null);
        name_user = sharedPreferences.getString("name_user", null);
        id_user = sharedPreferences.getInt("id_user", 0);
        status = sharedPreferences.getBoolean("status", false);

        transactionId = sharedPreferences.getString("transactionId", null);
        firstDate = sharedPreferences.getString("firstDate", null);
        expire = sharedPreferences.getInt("expire", 0);
        unixCurrentTime = sharedPreferences.getLong("unixCurrentTime", System.currentTimeMillis());
        expireDate = sharedPreferences.getLong("expireDate", 0);
        lastDate = sharedPreferences.getLong("lastDate", 0);


        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        allAdapterNew();
        loginUser();
        getPeymentFromServer();
        btnClickOnCreate();
        initRailActivity();
        intRvUser();


        setPaymentTransection(status);

        allUserRequest();

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

    private void allAdapterNew() {
        funnyAdapter = new FunnyAdapter(this, this);
        funnyAdapter2 = new FunnyAdapter(this, this);
        funnyAdapter3 = new FunnyAdapter(this, this);
        funnyAdapter4 = new FunnyAdapter(this, this);
        funnyAdapter5 = new RvChannel_Frg1(this, this);
    }

    private void allUserRequest() {
        if (phone_user!=null) {

            userViewModel.request_userLater(id_user, 1);
            userViewModel.request_userLike(id_user, 1);
            userViewModel.request_userSee(id_user, 1);
            userViewModel.request_userSave(id_user, 1);
            userViewModel.request_userSub(id_user);
        }

    }

    private void getPeymentFromServer() {

        if (phone_user!=null) {
            Service.getApiClient().getPayment(phone_user).enqueue(new Callback<UserDataModel>() {
                @Override
                public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                    if (response.isSuccessful()) {
                        assert response.body()!=null;
                        expireDate = response.body().getExpireDate();
                        transactionId = response.body().getTransactionId();
                        firstDate = response.body().getFirstDate();
                        amount = response.body().getAmount();
                        lastDate = expireDate - System.currentTimeMillis();
                        lastDate = (lastDate / 1000);
                        lastDate = (lastDate / 60);
                        lastDate = (lastDate / 60);
                        lastDate = (lastDate / 24);
                        editor.putLong("lastDate", lastDate);
                        editor.commit();
                        if (lastDate==0) {
                            binding.tvLastDate.setText("برای مشاهده وارد شوید");
                            binding.tvLastDate.setHint("کد دسترسی شما منقضی شده است.");
                            binding.tvAmount.setHint("بدون تراکنش    ");
                            binding.btnPayment.setVisibility(View.VISIBLE);
                            binding.btnPaymentLogin.setVisibility(View.GONE);
                            editor.putLong("lastDate", 0);
                            editor.commit();

                            Service.getApiClient().setPayment(phone_user, "", "", expireDate,  "").enqueue(new Callback<UserDataModel>() {
                                @Override
                                public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {

                                }

                                @Override
                                public void onFailure(Call<UserDataModel> call, Throwable t) {

                                }
                            });

                        } else {
                            editor.putLong("lastDate", lastDate);
                            editor.commit();
                            binding.tvLastDate.setText(lastDate + "روز");
                            binding.btnPayment.setVisibility(View.GONE);
                            binding.btnPaymentLogin.setVisibility(View.GONE);
                            binding.tvTransactionId.setText(transactionId);
                            binding.tvFirstDate.setText(firstDate);
                            binding.tvAmount.setText(amount + "تومان  ");

                            recreate();
                        }

                    }
                }

                @Override
                public void onFailure(Call<UserDataModel> call, Throwable t) {

                }
            });

        }

    }

    private void setPaymentTransection(boolean status) {
        if (status) {  // این تابع بعد از خرید موفق تراکنش اجرا می شود
            binding.btnPayment.setVisibility(View.GONE);
            binding.tvTransactionId.setText(transactionId);
            binding.tvFirstDate.setText(firstDate);

            switch (expire) {
                case 1: {
                    amount = "12,000";
                    binding.tvAmount.setText(amount + " تومان ");
                    expireDate = unixCurrentTime + month_1;
                    editor.putLong("expireDate", expireDate);
                    editor.commit();

                    lastDate = expireDate - System.currentTimeMillis();
                    lastDate = (lastDate / 1000);
                    lastDate = (lastDate / 60);
                    lastDate = (lastDate / 60);
                    lastDate = (lastDate / 24);
                    //lastDate = expireDate;
                    editor.putLong("lastDate", lastDate);
                    editor.commit();
                    binding.tvLastDate.setText(lastDate + "روز");

                    //send info Payment
                    Service.getApiClient().setPayment(phone_user, transactionId, firstDate, expireDate, amount).enqueue(new Callback<UserDataModel>() {
                        @Override
                        public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {

                        }

                        @Override
                        public void onFailure(Call<UserDataModel> call, Throwable t) {

                        }
                    });

                    break;
                }
                case 2: {
                    amount = "30,000";
                    binding.tvAmount.setText(amount + " تومان ");
                    expireDate = unixCurrentTime + month_3;
                    editor.putLong("expireDate", expireDate);
                    editor.commit();

                    lastDate = expireDate - System.currentTimeMillis();
                    lastDate = (lastDate / 1000);
                    lastDate = (lastDate / 60);
                    lastDate = (lastDate / 60);
                    lastDate = (lastDate / 24);
                    //lastDate = expireDate;
                    editor.putLong("lastDate", lastDate);
                    editor.commit();
                    binding.tvLastDate.setText(lastDate + "روز");

                    //send info Payment
                    Service.getApiClient().setPayment(phone_user, transactionId, firstDate, expireDate, amount).enqueue(new Callback<UserDataModel>() {
                        @Override
                        public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {

                        }

                        @Override
                        public void onFailure(Call<UserDataModel> call, Throwable t) {

                        }
                    });

                    break;
                }
                case 3: {
                    amount = "60,000";
                    binding.tvAmount.setText(amount + " تومان ");
                    expireDate = unixCurrentTime + month_6;
                    editor.putLong("expireDate", expireDate);
                    editor.commit();

                    lastDate = expireDate - System.currentTimeMillis();
                    lastDate = (lastDate / 1000);
                    lastDate = (lastDate / 60);
                    lastDate = (lastDate / 60);
                    lastDate = (lastDate / 24);
                    //lastDate = expireDate;
                    editor.putLong("lastDate", lastDate);
                    editor.commit();
                    binding.tvLastDate.setText(lastDate + "روز");

                    //send info Payment
                    Service.getApiClient().setPayment(phone_user, transactionId, firstDate, expireDate, amount).enqueue(new Callback<UserDataModel>() {
                        @Override
                        public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {

                        }

                        @Override
                        public void onFailure(Call<UserDataModel> call, Throwable t) {

                        }
                    });

                    break;
                }
            }

        }
    }

    private void loginUser() {
        String check = sharedPreferences.getString("phone_user", "");

        if (check.equals("")) { // هیچ کاربری وارد برنامه نشده
            binding.btnPaymentLogin.setVisibility(View.VISIBLE);
            binding.btnPayment.setVisibility(View.GONE);
            //  binding.tvExitUserActivity.setText(R.string.enter);
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

            btn_send.setOnClickListener(V -> { //رفتن به مرحله وارد کردن کد پیامک شدن
                if (validatePhone()) { // چک کردن خطاهای احتمالی هنگام وارد کردن شماره

                    btn_send.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    String phone_user2 = et_phone.getEditText().getText().toString();
                    Service.getApiClient().userLogin(phone_user2).enqueue(new Callback<ResponseBody>() { //  ارسال شماره به سرور و دریافت پیامک که در اینجا اگر شماره جدید باشد ستون جدید و اگر نه اپدیت کد پیامکی
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                bottomSheetDialog.dismiss();

                                BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(UserActivity.this);
                                bottomView2 = getLayoutInflater().inflate(R.layout.coustom_dialog_code, null);
                                bottomSheetDialog2.setContentView(bottomView2);
                                bottomSheetDialog2.setCanceledOnTouchOutside(true);
                                BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from((View) bottomView2.getParent());
                                sheetBehavior.setPeekHeight(

                                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics())
                                );
                                bottomSheetDialog2.show();

                                et_code = bottomView2.findViewById(R.id.et_code_userProfile);
                                Button btn_code = bottomView2.findViewById(R.id.sendCode_customLogin);
                                btn_code.setOnClickListener(v1 -> {

                                    if (validateCode()) { //  خطاهای احتمالی هنگام وارد کردن کد به غیر از یکی بودن کد تایپ شده با کد ارسالی
                                        code_user = et_code.getEditText().getText().toString();
                                        Service.getApiClient().getUser(phone_user2, code_user).enqueue(new Callback<UserDataModel>() {
                                            @Override
                                            public void onResponse(Call<UserDataModel> call1, @NonNull Response<UserDataModel> response1) { // در صورتی که کد با کد سرور برابر باشد
                                                if (response1.isSuccessful()) {  // کاربر وارد حساب شد ---------
                                                    UserDataModel userDataModel = response1.body();
                                                    assert userDataModel!=null;
                                                    id_user = userDataModel.getId_user();
                                                    name_user = userDataModel.getName();
                                                    phone_user = userDataModel.getPhone();
                                                    editor.putString("name_user", name_user);
                                                    editor.putInt("id_user", id_user);
                                                    editor.putString("phone_user", phone_user);
                                                    editor.commit();
                                                    bottomSheetDialog2.dismiss();
                                                    binding.phoneNumUserActivity.setText(phone_user);
                                                    binding.userNameUserActivity.setText(name_user);
                                                    binding.btnPaymentLogin.setVisibility(View.GONE);
                                                    binding.btnPayment.setVisibility(View.VISIBLE);
                                                    binding.tvExitUserActivity.setVisibility(View.VISIBLE);
                                                    // binding.tvEnterUserActivity.setVisibility(View.GONE);
                                                    // binding.tvExitUserActivity.setVisibility(View.VISIBLE);
                                                    getPeymentFromServer();
                                                    // binding.avatarUserUserActivity.setBackgroundResource(R.drawable.trophy);
                                                    binding.tvEnter1RvSubUser.setVisibility(View.GONE);
                                                    binding.avatarUserUserActivity.setBackgroundResource(R.drawable.ic_parents_happy);

                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<UserDataModel> call1, Throwable t) { // زمانب که کد وارد شده با کد داخ سرور هماهنگ نباشد

                                                et_code.setError("کدی که وارد کردید با کد ارسالی مطابقت ندارد ... بهتر هستش از یک بزرگتر درخواست راهنمایی کنید ");

                                            }
                                        });
                                    }


                                });

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            et_phone.setError("اینترنت شما قطع و یا ضعیف است .. waiting");

                        }
                    });
                }
            });
        } else { //کاربر از قبل وارد شده
            binding.phoneNumUserActivity.setText(phone_user);
            binding.userNameUserActivity.setText(name_user);
            binding.btnPaymentLogin.setVisibility(View.GONE);
            binding.btnPayment.setVisibility(View.VISIBLE);
            //  binding.tvExitUserActivity.setVisibility(View.VISIBLE);
            //  binding.tvEnterUserActivity.setVisibility(View.GONE);
            getPeymentFromServer();
            binding.tvEnter1RvSubUser.setVisibility(View.GONE);
            binding.avatarUserUserActivity.setBackgroundResource(R.drawable.ic_parents_happy);
        }

    }
    private void btnClickOnCreate() {

        binding.tvExitUserActivity.setOnClickListener(v -> {


                binding.btnPaymentLogin.setVisibility(View.VISIBLE);
                binding.btnPayment.setVisibility(View.GONE);
                binding.tvExitUserActivity.setVisibility(View.GONE);
                editor.putString("phone_user", "");
                editor.putInt("id_user", 0);
                editor.putLong("lastDate",0);
                editor.commit();
                recreate();
                binding.phoneNumUserActivity.setText(R.string.hint_number);
                binding.userNameUserActivity.setText(R.string.hint_user_name);
                binding.avatarUserUserActivity.setBackgroundResource(R.drawable.ic_baseline_account_circle_24);
                binding.tvEnter1RvSubUser.setVisibility(View.VISIBLE);



        });

        binding.btnPaymentLogin.setOnClickListener(v -> {
            loginUser();
        });

        binding.avatarUserUserActivity.setOnClickListener(v -> {
            if (phone_user==null) {
                loginUser();
            }
        });


        binding.tvEnter1RvSubUser.setOnClickListener(v -> { // پیغم نمایش داده شده در لیست ویدیوهای کاربر چنلها
            loginUser();

        });
        binding.btnPayment.setOnClickListener(v -> {
            if (phone_user!=null) {
                binding.btnPaymentLogin.setVisibility(View.GONE);
                startActivity(new Intent(UserActivity.this, PaymentActivity.class));

            }
        });


    }



    private Boolean validatePhone() { //  متد خطاهای شماره تماس
        String valName = et_phone.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)"; // if inot work can use this -> \\A\\w{4,20}\\z   :A that mean start and w means evrything including alphabets and digit
        // with small letters and capital letters  and 4-22 is limits  \\ thats no soace   )

        if (valName.isEmpty()) {
            et_phone.setError("هنوز شماره موبایل وارد نشده است");
            return false;
        } else if (valName.length() < 11) {
            et_phone.setError(" تعداد اعداد شماره تلفن کم است");
            return false;
        }
      /*  else if (!valName.matches(noWhiteSpace)) {
            et_phone.setError("به اشتباه بجای عدد ، حروف وارد کرده اید ");
            return false;
        }*/
        else {
            et_phone.setError(null);
            return true;
        }
    }

    private Boolean validateCode() { //  متد خطاهای شماره تماس
        String valName = et_code.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\s+$)"; // if inot work can use this -> \\A\\w{4,20}\\z   :A that mean start and w means evrything including alphabets and digit
        // with small letters and capital letters  and 4-22 is limits  \\ thats no soace   )

        if (valName.isEmpty()) {
            et_code.setError("هنوز شماره کد ارسال شده وارد نشده است");
            return false;
        } else if (valName.length() < 4) {
            et_code.setError(" کد را 4 رقمی وارد کنید");
            return false;
        }
      /*  else if (!valName.matches(noWhiteSpace)) {
            et_phone.setError("به اشتباه بجای عدد ، حروف وارد کرده اید ");
            return false;
        }*/
        else {
            et_code.setError(null);
            return true;
        }
    }


    private void getUserSub() {
        userViewModel.getUserSub().observe(this, new Observer<List<ChannelDataModel>>() {
            @Override
            public void onChanged(List<ChannelDataModel> channelDataModels) {
                funnyAdapter5.setData(channelDataModels);
            }
        });
    }


    private void getUserLike() {
        userViewModel.getUserLike().observe(UserActivity.this, funnyDataModels -> {
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

        binding.rvLikeVideoUserActivity.setLayoutManager(new LinearLayoutManager(UserActivity.this, LinearLayoutManager.HORIZONTAL, false));
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
        intent.putExtra("id_vid_funny", id_vid_funny);
        intent.putExtra("kind", kind);
        intent.putExtra("id_channel", id_channel);
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