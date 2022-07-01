package ir.popittv.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.activitys.PlayerActivity;
import ir.popittv.myapplication.models.UserDataModel;
import ir.popittv.myapplication.request.Service;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialogFragment extends DialogFragment {

    private String phone;
    public static String TAG="LoginDialogFragment";

    private OnClickLoginDialog onClickLoginDialog;

    public LoginDialogFragment(OnClickLoginDialog onClickLoginDialog) {
        this.onClickLoginDialog = onClickLoginDialog;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);
        builder.setTitle("login");
        builder.setMessage(getString(R.string.dialog_login_message));



        TextInputLayout et_phone=view.findViewById(R.id.et_phone_userProfile);



        builder.setPositiveButton(getString(R.string.dialog_login_send_num), (dialog, which) -> {
            phone=et_phone.getEditText().getText().toString();
            onClickLoginDialog.phoneClic(phone);

            Toast.makeText(getContext(), "phone :"+phone, Toast.LENGTH_SHORT).show();

            Call<ResponseBody> call= Service.getApiClient().userLogin(phone);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Log.i("tagy","responce");
                    }else {
                        Log.i("tagy","not ffhfhfhf");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });



        })
                .setNegativeButton(getString(R.string.dialog_login_negative), (dialog, which) -> {
                    Toast.makeText(getContext(), "negativeButton", Toast.LENGTH_SHORT).show();
                });




        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
