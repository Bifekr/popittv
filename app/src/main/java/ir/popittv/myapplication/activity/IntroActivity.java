package ir.popittv.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class IntroActivity extends AppCompatActivity {
    private static final int splash_time_out = 5000;
    ImageView iv_background, iv_logo;
    TextView tv_appName;
    LottieAnimationView lottieAnimationView;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.requestChannel_kind(2);
        mainViewModel.requestFunny_subMenu(0, 1);
        iv_background = findViewById(R.id.image_intro);
        iv_logo = findViewById(R.id.logo_intro);
        tv_appName = findViewById(R.id.tv_appName_intro);
        lottieAnimationView = findViewById(R.id.lottie_intro);
        getChannel_kind();
        getFunny_subMenu();
        iv_background.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        iv_logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        tv_appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        handlerSplash();

    }

    public void callNextScreen(View view) {
        startActivity(new Intent(IntroActivity.this, MainActivity.class));
    }

    private void handlerSplash() {

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }, splash_time_out);
    }

    private void getChannel_kind() {
        mainViewModel.getChannel_kind().observe(this, channelDataModels -> {
      /*      if (channelDataModels!=null) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                Toast.makeText(this,"not success",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this,"not success",Toast.LENGTH_LONG).show();
            }*/

        });

    }

    private void getFunny_subMenu() {

        mainViewModel.getFunny_subMenu().observe(this, funnyDataModels -> {
            if (funnyDataModels!=null) {

            }
        });
    }
}