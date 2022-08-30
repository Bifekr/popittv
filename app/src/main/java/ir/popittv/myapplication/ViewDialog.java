package ir.popittv.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ir.popittv.myapplication.activity.MainActivity;
import ir.popittv.myapplication.activity.PlayerActivity;
import ir.popittv.myapplication.activity.UserActivity;

public class ViewDialog {

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialogbox_otp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.txt_file_path);
        text.setText(msg);

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_okay);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
              activity.startActivity(new Intent(activity, UserActivity.class));
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
