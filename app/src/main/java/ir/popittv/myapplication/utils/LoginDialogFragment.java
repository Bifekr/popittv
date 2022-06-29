package ir.popittv.myapplication.utils;

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

import ir.popittv.myapplication.R;

public class LoginDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater=requireActivity().getLayoutInflater();

        return new AlertDialog.Builder(requireContext())

                //.setView(inflater.inflate(R.layout.custom_dialog,))
                .setMessage(getString(R.string.dialog_login_message))
                .setPositiveButton(getString(R.string.dialog_login_send_num),(dialog, which) -> {
                    Toast.makeText(getContext(), "positiveButton", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(getString(R.string.dialog_login_negative),(dialog, which) -> {
                    Toast.makeText(getContext(), "negativeButton", Toast.LENGTH_SHORT).show();
                })
                .create();
    }

    public static String TAG = "LoginDialogFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
