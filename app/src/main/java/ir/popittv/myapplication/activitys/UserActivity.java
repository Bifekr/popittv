package ir.popittv.myapplication.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.databinding.ActivityUserBinding;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.Service;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    ActivityUserBinding binding;
    View bottomView;
    View bottomView2;

    TextInputLayout et_phone;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String name_user;
    private String phone_user;
    private String code_user;
    private int id_user;
    private boolean userLoged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(binding.getRoot());
        sharedPreferences = UserActivity.this.getSharedPreferences("user_info", MODE_PRIVATE);
        initRailActivity();


        binding.avatarUserUserActivity.setOnClickListener(v -> {

            loginUser();


        });

        String news = sharedPreferences.getString("phone_user", null);
        String nammm = sharedPreferences.getString("name_user", null);
        if (news!=null) {
            binding.phoneNumUserActivity.setText(news);
            binding.userNameUserActivity.setText(nammm);
            binding.avatarUserUserActivity.setBackgroundResource(R.drawable.trophy);
            binding.tvExitUserActivity.setText(R.string.exite);

        } else {
            binding.phoneNumUserActivity.setText(R.string.hint_number);
            binding.userNameUserActivity.setText(R.string.hint_user_name);
            binding.tvExitUserActivity.setText(R.string.enter);

        }

        binding.tvExitUserActivity.setOnClickListener(v -> {

            sharedPreferences = this.getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor22 = sharedPreferences.edit();
            editor22.putString("phone_user", null);
            editor22.apply();
            Toast.makeText(getApplicationContext(), "شما از حساب خود خارج شدید", Toast.LENGTH_SHORT).show();


        });


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
                                        public void onResponse(Call<UserDataModel> call1, Response<UserDataModel> response1) {
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
}