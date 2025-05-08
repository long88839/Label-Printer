package net.nyx.label.printer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeReceiptActivity extends AppCompatActivity {
    Fragment fragment2;
    FragmentManager manager;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_receipt);
        initView();
    }

    private void initView() {
        fragment2 = new TemplateFragment();
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout, fragment2);
        transaction.show(fragment2);
        transaction.commit();
    }
}