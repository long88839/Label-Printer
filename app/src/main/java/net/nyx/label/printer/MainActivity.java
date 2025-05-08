package net.nyx.label.printer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Fragment fragment1, fragment2, fragment3;
    LinearLayout layout1, layout2, layout3;
    FragmentManager manager;
    FragmentTransaction transaction;

    ImageView bottomImageView1;
    ImageView bottomImageView2;
    ImageView bottomImageView3;
    private static final String TAG = "MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAppLanguage();
        //检测是否为nyx设备
        checkNyxPlatform();
        Log.d(TAG, "onCreate:mm --"+mmToPx(40));
        initView();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        initial();
        fragmentHide();
        transaction.show(fragment1);
        transaction.commit();
    }

    private static int mmToPx(float mm) {
        return (int) (mm * Resources.getSystem().getDisplayMetrics().xdpi / 25.4f);
    }

    private void initAppLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("app_language", "");
        if (!language.isEmpty()) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
    }

    private void checkNyxPlatform() {
        String platformTag = getSystemProperty("ro.nyx.platform.tag");
        Log.d(TAG, "ro.nyx.platform.tag: " + platformTag);

        if (platformTag == null || platformTag.isEmpty()) {
            // 回退到 ro.build.product 判断
            String buildProduct = getSystemProperty("ro.build.product");
            Log.d(TAG, "ro.build.product: " + buildProduct);

            if (buildProduct != null && (buildProduct.equals("1008") || buildProduct.equals("Handheld_POS"))) {
                return; // 特殊设备放行
            }

            finish(); // 其他情况结束 Activity
        }
    }

    private String getSystemProperty(String key) {
        try {
            Class<?> systemProperties = Class.forName("android.os.SystemProperties");
            java.lang.reflect.Method method = systemProperties.getMethod("get", String.class);
            return (String) method.invoke(null, key);
        } catch (Exception e) {
            Log.e(TAG, "Failed to read system property via reflection", e);
            return null;
        }
    }

    private void initView() {
        layout1 = findViewById(R.id.bottom_LinearLayout1);
        layout2 = findViewById(R.id.bottom_LinearLayout2);
        layout3 = findViewById(R.id.bottom_LinearLayout3);
        bottomImageView1 = findViewById(R.id.bottom_ImageView1);
        bottomImageView2 = findViewById(R.id.bottom_ImageView2);
        bottomImageView3 = findViewById(R.id.bottom_ImageView3);
        fragment1 = new HomeFragment();
        fragment2 = new TemplateFragment();
        fragment3 = new MoreFragment();
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
    }

    private void fragmentHide() {
        transaction.hide(fragment1);
        transaction.hide(fragment2);
        transaction.hide(fragment3);
    }

// 修改 initial() 方法
private void initial() {
    fragment1 = findExistingFragment(HomeFragment.class);
    fragment2 = findExistingFragment(TemplateFragment.class);
    fragment3 = findExistingFragment(MoreFragment.class);

    if (fragment1 == null) {
        fragment1 = new HomeFragment();
        transaction.add(R.id.framelayout, fragment1, HomeFragment.class.getSimpleName());
    }
    if (fragment2 == null) {
        fragment2 = new TemplateFragment();
        transaction.add(R.id.framelayout, fragment2, TemplateFragment.class.getSimpleName());
    }
    if (fragment3 == null) {
        fragment3 = new MoreFragment();
        transaction.add(R.id.framelayout, fragment3, MoreFragment.class.getSimpleName());
    }
}

private <T extends Fragment> T findExistingFragment(Class<T> fragmentClass) {
    return (T) manager.findFragmentByTag(fragmentClass.getSimpleName());
}


    private void bottomImageViewInit() {
        bottomImageView1.setImageResource(R.drawable.home);
        bottomImageView2.setImageResource(R.drawable.template);
        bottomImageView3.setImageResource(R.drawable.mine);
    }

    //switch语句，选择不同的layout
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_LinearLayout1:
                transaction = manager.beginTransaction();//前面transaction已经被commit，需要一个新的transaction
                fragmentHide();
                transaction.show(fragment1);
                transaction.commit();
                bottomImageViewInit();
                bottomImageView1.setImageResource(R.drawable.home1);
                break;
            case R.id.bottom_LinearLayout2:
                transaction = manager.beginTransaction();
                fragmentHide();
                transaction.show(fragment2);
                transaction.commit();
                bottomImageViewInit();
                bottomImageView2.setImageResource(R.drawable.template1);
                break;
            case R.id.bottom_LinearLayout3:
                transaction = manager.beginTransaction();
                fragmentHide();
                transaction.show(fragment3);
                transaction.commit();
                bottomImageViewInit();
                bottomImageView3.setImageResource(R.drawable.mine1);
                break;
        }
    }
}
