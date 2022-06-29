package ir.popittv.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
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

public class LoginDialogFragment extends DialogFragment {

    private String phone;
    public static String TAG="LoginDialogFragment";

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

        phone=et_phone.getEditText().getText().toString();

        builder.setPositiveButton(getString(R.string.dialog_login_send_num), (dialog, which) -> {

            Toast.makeText(getContext(), "phone :"+phone, Toast.LENGTH_SHORT).show();
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
