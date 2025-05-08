package net.nyx.label.printer;

import android.content.res.Resources;
import android.util.Log;

public class Constant {
    static final String TAG = "Constant";

    // 所有模板的 layout id
    public static final int[] All_TEMPLATE_LAYOUTS = {
            R.layout.template_general_0,
            R.layout.template_general_1,
            R.layout.template_shoes_bags_0,
            R.layout.template_logistics_0,
            R.layout.template_pickup_code_0,
            R.layout.template_clothing_0,
            R.layout.template_clothing_1,
            R.layout.template_logistics_1
    };

    // 所有模板的 layout id 对应的宽高
//    public static final int[][] All_TEMPLATE_LAYOUT_SIZES = {
//            {320, 160}, // 对应 template_general_0
//            {320, 160}, // 对应 ltemplate_general_1
//            {320, 240}, // 对应 template_shoes_bags_0
//            {320, 400}, // 对应 template_logistics_0
//            {320, 240}, // 对应 template_pickup_code_0
//            {350, 240}, // 对应 template_clothing_0
//            {350, 240}, // 对应 template_clothing_1
//            {350, 320}  // 对应 template_logistics_1
//    };


    // 所有模板的 layout id 对应的宽高（毫米转像素）
    public static final int[][] All_TEMPLATE_LAYOUT_SIZES = {
            {mmToPx(35), mmToPx(20)},   // template_general_0 (40x20mm)
            {mmToPx(35), mmToPx(20)},   // template_general_1
            {mmToPx(40), mmToPx(30)},   // template_shoes_bags_0
            {mmToPx(35), mmToPx(30)},   // template_logistics_0
            {mmToPx(35), mmToPx(30)},   // template_pickup_code_0
            {mmToPx(35), mmToPx(30)},   // template_clothing_0
            {mmToPx(35), mmToPx(30)},   // template_clothing_1
            {mmToPx(35), mmToPx(30)},    // template_logistics_1
            {mmToPx(60), mmToPx(80)}    // template_logistics_1
    };

    private static int mmToPx(float mm) {
        return (int) (mm * Resources.getSystem().getDisplayMetrics().xdpi / 25.4f);
    }


    // 所有模板的 layout id 对应的宽高
    public static final String[] All_TEMPLATE_LAYOUT_SIZES_STRING = {
            "35mm×20mm",  // template_general_0 (与 {mmToPx(35), mmToPx(20)} 对应)
            "35mm×20mm",  // template_general_1
            "40mm×30mm",  // template_shoes_bags_0
            "35mm×30mm",  // template_logistics_0
            "35mm×30mm",  // template_pickup_code_0
            "35mm×30mm",  // template_clothing_0
            "35mm×30mm",  // template_clothing_1
            "35mm×30mm"   // template_logistics_1
    };


    // 所有模板的 layout id 对应的图片
    public static final int[] All_TEMPLATE_IMAGES = {
            R.drawable.template_general_0,
            R.drawable.template_general_1,
            R.drawable.template_shoes_bags_0,
            R.drawable.template_logistics_0,
            R.drawable.template_pickup_code_0,
            R.drawable.template_clothing_0,
            R.drawable.template_clothing_1,
            R.drawable.template_logistics_1
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

    // 价签模板的 layout id 对应的layout id
    public static final int[] TEMPLATE_RECEIPT_LAYOUTS = {

    };

}
