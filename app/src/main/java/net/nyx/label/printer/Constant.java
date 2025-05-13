package net.nyx.label.printer;

import android.content.res.Resources;
import android.util.Log;

public class Constant {
    static final String TAG = "Constant";

    // 所有模板的 layout id
    public static final int[] All_TEMPLATE_LAYOUTS = {
            //通用
            R.layout.template_general_0,
            R.layout.template_general_1,
            //鞋包
            R.layout.template_shoes_bags_0,
            //物流
            R.layout.template_logistics_0,
            R.layout.template_logistics_1,
            //取件码
            R.layout.template_pickup_code_0,
            //衣服
            R.layout.template_clothing_0,
            R.layout.template_clothing_1,
            //母婴
            R.layout.template_baby_0,
            R.layout.template_baby_1,
            R.layout.template_baby_2,
            R.layout.template_baby_3,
    };

    // 所有模板的 layout id 对应的宽高（毫米转像素）
    public static final int[][] All_TEMPLATE_LAYOUT_SIZES = {
            //通用
            {35,20},
            {35,20},
            //鞋包
            {40,30},
            //物流
            {35,30},
            {35,30},
            //取件码
            {35,30},
            //衣服
            {35,30},
            {35,30},
            //母婴
            {40,30},
            {40,30},
            {40,30},
            {55,37},
    };

    // 所有模板的 layout id 对应的图片
    public static final int[] All_TEMPLATE_IMAGES = {
            //通用
            R.drawable.template_general_0,
            R.drawable.template_general_1,
            //鞋包
            R.drawable.template_shoes_bags_0,
            //物流
            R.drawable.template_logistics_0,
            R.drawable.template_logistics_1,
            //取件码
            R.drawable.template_pickup_code_0,
            //衣服
            R.drawable.template_clothing_0,
            R.drawable.template_clothing_1,
            //母婴
            R.drawable.template_baby_0,
            R.drawable.template_baby_1,
            R.drawable.template_baby_2,
            R.drawable.template_baby_3,
    };

    // 服饰模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_CLOTHING_LAYOUTS = {
            R.layout.template_clothing_0,
            R.layout.template_clothing_1,
    };

    // 鞋包模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_SHOE_BAGS_LAYOUTS = {
            R.layout.template_shoes_bags_0
    };

    // 食品模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_FOOD_LAYOUTS = {

    };

    // 珠宝模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_JEWELRY_LAYOUTS = {

    };

    // 电器模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_APPLIANCES_LAYOUTS = {

    };

    // 母婴模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_BABY_LAYOUTS = {
            R.layout.template_baby_0,
            R.layout.template_baby_1,
            R.layout.template_baby_2,
            R.layout.template_baby_3,
    };

    // 零售模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_RETAIL_LAYOUTS = {

    };

    // 学生模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_STUDENT_LAYOUTS = {

    };

    // 物流模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_LOGISTICS_LAYOUTS = {
            R.layout.template_logistics_0,
            R.layout.template_logistics_1
    };

    // 取件码模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_PICKUP_CODE_LAYOUTS = {
            R.layout.template_pickup_code_0
    };

    // 通用模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_GENERAL_LAYOUTS = {
            R.layout.template_general_0,
            R.layout.template_general_1,
    };

    // 其他模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_OTHERS_LAYOUTS = {

    };

    // 价签模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_PRICE_LAYOUTS = {
            R.layout.template_shoes_bags_0,
            R.layout.template_clothing_1
    };

    // 小票模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_RECEIPT_LAYOUTS = {

    };

}
