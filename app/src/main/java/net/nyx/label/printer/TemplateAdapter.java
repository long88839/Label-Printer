// TemplateAdapter.java
package net.nyx.label.printer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TemplateAdapter extends BaseAdapter {
    private static final String TAG = "TemplateAdapter";
    private Context context;
    private int[] items;

    public TemplateAdapter(Context context, int[] items) {
        this.context = context;
        this.items = items;
    }

    // 新增方法：更新数据源
    public void updateData(int[] newData) {
        this.items = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // 每行有两个项，因此返回 items.size() / 2 向上取整
        return (int) Math.ceil(items.length / 2.0);
    }

    @Override
    public Object getItem(int position) {
        return items[position*2];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.template_item, parent, false);
        }

        LinearLayout linearLayout1 = convertView.findViewById(R.id.linearLayout1);
        LinearLayout linearLayout2 = convertView.findViewById(R.id.linearLayout2);

        // 获取当前项的数据
        int itemIndex1 = getIndexOf(Constant.All_TEMPLATE_LAYOUTS, items[position * 2]); // 第一个项在All_TEMPLATE_LAYOUTS的索引
        int itemIamgeId1 = Constant.All_TEMPLATE_IMAGES[itemIndex1];
        String itemSize1 = Constant.All_TEMPLATE_LAYOUT_SIZES[itemIndex1] [0]+ "mmx" + Constant.All_TEMPLATE_LAYOUT_SIZES[itemIndex1] [1]+ "mm";
        Log.d(TAG, "getView: " + itemSize1);

        // 设置数据到布局中
        ImageView imageView1 = linearLayout1.findViewById(R.id.imageView1);
        TextView textView1 = linearLayout1.findViewById(R.id.textView1);
        imageView1.setImageResource(itemIamgeId1);
        textView1.setText(itemSize1);

        // 检查是否还有第二个项
        if (position * 2 + 1 < items.length) {
            linearLayout2.setVisibility(View.VISIBLE);
            // 获取当前项的数据
            int itemIndex2 = getIndexOf(Constant.All_TEMPLATE_LAYOUTS, items[position * 2 + 1]); // 第二个项在All_TEMPLATE_LAYOUTS的索引
            int itemIamgeId2 = Constant.All_TEMPLATE_IMAGES[itemIndex2];
            String itemSize2 = Constant.All_TEMPLATE_LAYOUT_SIZES[itemIndex2] [0]+ "mmx" + Constant.All_TEMPLATE_LAYOUT_SIZES[itemIndex2] [1]+ "mm";

            // 设置数据到布局中
            ImageView imageView2 = linearLayout2.findViewById(R.id.imageView2);
            TextView textView2 = linearLayout2.findViewById(R.id.textView2);
            imageView2.setImageResource(itemIamgeId2);
            textView2.setText(itemSize2);

            // 设置点击事件监听器
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditPrintActivity.class);
                    intent.putExtra("linearLayout_id", itemIndex2);
                    context.startActivity(intent);
//                    Toast.makeText(context, "Layout 2 clicked: " + getItemId(position) *2 + 1, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // 如果没有第二个项，隐藏 linearLayout2
            linearLayout2.setVisibility(View.INVISIBLE);
        }

        // 设置点击事件监听器
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPrintActivity.class);
                intent.putExtra("linearLayout_id", itemIndex1);
                context.startActivity(intent);
//                Toast.makeText(context, "Layout 1 clicked: " + getItemId(position)*2, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    // 获取数组中元素的索引
    public static int getIndexOf(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1; // 如果未找到元素，返回 -1
    }
}
