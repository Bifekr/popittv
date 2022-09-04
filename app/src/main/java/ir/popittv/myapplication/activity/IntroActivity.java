package ir.popittv.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;

import ir.popittv.myapplication.R;
import ir.popittv.myapplication.viewmodel.MainViewModel;

public class IntroActivity extends AppCompatActivity {
    private static final int splash_time_out = 12000;
    ImageView image_intro;
    TextView  line4,line5;
    LottieAnimationView lottie_intro ,tv_a,tv_appName,logo_intro;
    private MainViewModel mainViewModel;
    View line1,line2,line3;
    TextView tv_tag,tv_tag2;
    Animation topAnim,middleAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.requestChannel_kind(2);
        mainViewModel.requestFunny_subMenu(0, 1);
        getChannel_kind();
        getFunny_subMenu();

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim_splash);
        middleAnim = AnimationUtils.loadAnimation(this,R.anim.center_anim_splash);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim_splash);


        image_intro = findViewById(R.id.image_intro);
        logo_intro = findViewById(R.id.logo_intro);
        tv_appName = findViewById(R.id.tv_appName_intro);
        lottie_intro = findViewById(R.id.lottie_intro);

        image_intro.animate().translationY(1600).setDuration(1000).setStartDelay(2800);
        logo_intro.animate().translationX(1400).setDuration(900).setStartDelay(1800);
        tv_appName.animate().translationX(-1400).setDuration(900).setStartDelay(1800);
        lottie_intro.animate().translationX(15000).setDuration(13000).setStartDelay(4);
                                //مقصد حرکت         //sorat harekat     //zaman ejra shoro harekat ba sorat taeen shodeh
        handlerSplash();                            // adad bishtar ahestetar
        initiatedView();
        setAnim();
    }



    private void handlerSplash() {

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }, splash_time_out);
    }

    private void setAnim() {
        line1.setAnimation(topAnim);
        line2.setAnimation(topAnim);
        line3.setAnimation(topAnim);
        line4.setAnimation(topAnim);
        line5.setAnimation(topAnim);

        tv_a.setAnimation(middleAnim);
        tv_tag.setAnimation(bottomAnim);
        tv_tag2.setAnimation(bottomAnim);
    }
    private void initiatedView() {

        line1=findViewById(R.id.line1_topSplash);
        line2=findViewById(R.id.line2_topSplash);
        line3=findViewById(R.id.line3_topSplash);
        line4=findViewById(R.id.line4_topSplash);
        line5=findViewById(R.id.line5_topSplash);

        tv_a=findViewById(R.id.tv_a_splash);
        tv_tag=findViewById(R.id.tv_tag_splash);
        tv_tag2=findViewById(R.id.tv_tag2_splash);

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