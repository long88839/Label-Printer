package net.nyx.label.printer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ViewPager2 viewPager2;
    private ImageSliderAdapter adapter;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int[] imageResources = {
            R.drawable.post0,
            R.drawable.post1,
            R.drawable.post2,
            R.drawable.post3
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String language = getResources().getConfiguration().locale.getLanguage();
        if (language.equals("zh")) {
            // 使用 ContextCompat.getDrawable 将资源 ID 转换为 Drawable 对象
            view.findViewById(R.id.home_search_image).setBackground(
                    ContextCompat.getDrawable(requireContext(), R.drawable.home_search_zh)
            );
        } else {
            view.findViewById(R.id.home_search_image).setBackground(
                    ContextCompat.getDrawable(requireContext(), R.drawable.home_search_en)
            );
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        // 定义需要初始化的视图 ID 数组
        int[] viewIds = {
                R.id.home_edit_image,
                R.id.home_pdf_image,
                R.id.home_price_image,
                R.id.home_receipt_image,
                R.id.home_photo_image,
                R.id.home_search_image
        };

        // 定义对应的视图变量数组
        View[] views = new View[viewIds.length];

        // 循环初始化视图并设置点击事件
        for (int i = 0; i < viewIds.length; i++) {
            views[i] = view.findViewById(viewIds[i]);
            if (views[i] != null) { // 非空检查
                views[i].setOnClickListener(this);
            } else {
                // 打印日志或抛出异常，提示资源 ID 可能错误
                Log.e("initView", "View with ID " + viewIds[i] + " not found.");
            }
        }

        // 初始化 ViewPager2
        viewPager2 = view.findViewById(R.id.home_post_viewpager);
        adapter = new ImageSliderAdapter();
        viewPager2.setAdapter(adapter);

        // 设置自动轮播
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager2.getCurrentItem();
                if (currentItem < imageResources.length - 1) {
                    viewPager2.setCurrentItem(currentItem + 1);
                } else {
                    viewPager2.setCurrentItem(0);
                }
                handler.postDelayed(this, 4000); // 每3秒切换一次
            }
        };
        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 移除回调，避免内存泄漏
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_edit_image:
                Intent intentEdit = new Intent(requireContext(), HomeEditActivity.class);
                startActivity(intentEdit);
                break;
            case R.id.home_pdf_image:
                Intent intentPDF = new Intent(requireContext(), HomePDFActivity.class);
                startActivity(intentPDF);
                break;
            case R.id.home_price_image:
                Intent intentPrice = new Intent(requireContext(), HomePriceActivity.class);
                startActivity(intentPrice);
                break;
            case R.id.home_receipt_image:
                Intent intentReceipt = new Intent(requireContext(), HomeReceiptActivity.class);
                startActivity(intentReceipt);
                break;
            case R.id.home_photo_image:
                Intent intentPhoto = new Intent(requireContext(), HomePhotoActivity.class);
                startActivity(intentPhoto);
                break;
            case R.id.home_search_image:
                Toast.makeText(requireContext(), "Developing...  ", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
