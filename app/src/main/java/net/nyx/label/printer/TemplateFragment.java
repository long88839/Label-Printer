package net.nyx.label.printer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class TemplateFragment extends Fragment {
    private String TAG = "TemplateFragment";
    private ListView listView;
    private TemplateAdapter adapter;
    int[] textViewIds;
    private HorizontalScrollView horizontal_sv;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.template_listview, container, false);

        // 初始化 ListView 和 Adapter
        listView = view.findViewById(R.id.listView);
        updateListView(Constant.All_TEMPLATE_LAYOUTS); // 默认显示所有模板

        horizontal_sv = view.findViewById(R.id.horizontal_sv);
        // 获取当前宿主 Activity
        Activity activity = getActivity();
        if (activity != null) {
            // 判断具体的 Activity 类型
            if (activity instanceof MainActivity || activity instanceof HomeEditActivity) {
                horizontal_sv.setVisibility(View.VISIBLE);
            } else if (activity instanceof HomePriceActivity) {
                horizontal_sv.setVisibility(View.GONE);
                updateListView(Constant.TEMPLATE_PRICE_LAYOUTS);
            } else if (activity instanceof HomeReceiptActivity) {
                horizontal_sv.setVisibility(View.GONE);
                updateListView(Constant.TEMPLATE_RECEIPT_LAYOUTS);
            }
        } else {
            Log.e(TAG, "Activity is null");
        }

        // 定义所有 TextView 的 ID 列表
        textViewIds = new int[]{
                R.id.tv_all, R.id.tv_clothing, R.id.tv_shoes_bags, R.id.tv_food,
                R.id.tv_jewelry, R.id.tv_appliances, R.id.tv_baby, R.id.tv_retail,
                R.id.tv_student, R.id.tv_logistics, R.id.tv_pickup_code, R.id.tv_general,
                R.id.tv_others
        };

        // 动态绑定点击事件
        for (int id : textViewIds) {
            TextView textView = view.findViewById(id);
            if (textView != null) { // 空值检查
                textView.setOnClickListener(v -> onCategoryClick(textView));
            } else {
                Log.e("Fragment", "TextView with ID " + id + " is null");
            }
        }

        return view;
    }

    // 点击事件处理方法
    public void onCategoryClick(TextView clickedView) {
        // 将所有 TextView 的文本颜色恢复为默认状态
        int defaultColor = ContextCompat.getColor(requireContext(), android.R.color.black);
        int selectedColor = ContextCompat.getColor(requireContext(), android.R.color.holo_orange_dark);

        for (int id : textViewIds) {
            TextView textView = getView().findViewById(id);
            if (textView != null) {
                textView.setTextColor(defaultColor); // 恢复默认颜色
            }
        }

        // 设置当前点击的 TextView 文本颜色为橙红色
        clickedView.setTextColor(selectedColor);

        // 根据点击的 TextView 更新 ListView 数据源
        switch (clickedView.getId()) {
            case R.id.tv_all:
                updateListView(Constant.All_TEMPLATE_LAYOUTS);
                break;
            case R.id.tv_clothing:
                updateListView(Constant.TEMPLATE_CLOTHING_LAYOUTS);
                break;
            case R.id.tv_shoes_bags:
                updateListView(Constant.TEMPLATE_SHOE_BAGS_LAYOUTS);
                break;
            case R.id.tv_food:
                updateListView(Constant.TEMPLATE_FOOD_LAYOUTS);
                break;
            case R.id.tv_jewelry:
                updateListView(Constant.TEMPLATE_JEWELRY_LAYOUTS);
                break;
            case R.id.tv_appliances:
                updateListView(Constant.TEMPLATE_APPLIANCES_LAYOUTS);
                break;
            case R.id.tv_baby:
                updateListView(Constant.TEMPLATE_BABY_LAYOUTS);
                break;
            case R.id.tv_retail:
                updateListView(Constant.TEMPLATE_RETAIL_LAYOUTS);
                break;
            case R.id.tv_student:
                updateListView(Constant.TEMPLATE_STUDENT_LAYOUTS);
                break;
            case R.id.tv_logistics:
                updateListView(Constant.TEMPLATE_LOGISTICS_LAYOUTS);
                break;
            case R.id.tv_pickup_code:
                updateListView(Constant.TEMPLATE_PICKUP_CODE_LAYOUTS);
                break;
            case R.id.tv_general:
                updateListView(Constant.TEMPLATE_GENERAL_LAYOUTS);
                break;
            case R.id.tv_others:
                updateListView(Constant.TEMPLATE_OTHERS_LAYOUTS);
                break;
        }
    }

    // 更新 ListView 数据源的方法
    private void updateListView(int[] templateLayouts) {
        if (adapter == null) {
            adapter = new TemplateAdapter(getContext(), templateLayouts);
            listView.setAdapter(adapter);
        } else {
            adapter.updateData(templateLayouts);
        }
    }
}