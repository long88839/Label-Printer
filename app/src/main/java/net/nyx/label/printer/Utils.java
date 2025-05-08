package net.nyx.label.printer;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.Random;

/**
 * Created by yyzz on 2019/1/4.
 */
public class Utils {

    private static final String TAG = "Utils";

    private static Application sApplication;
    private static Utils sUtils;

    public static Utils getInstance() {
        if (sUtils == null)
            sUtils = new Utils();
        return sUtils;
    }

    public static Utils init(final Application app) {
        getInstance();
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
        } else {
            if (app != null && app.getClass() != sApplication.getClass()) {
                sApplication = app;
            }
        }
        return sUtils;
    }

    public static Application getApp() {
        if (sApplication != null)
            return sApplication;
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 生成指定长度的随机字符串
     * 该方法使用了一个包含字母和数字的字符串作为随机选择的字符集，
     * 通过随机选择来构建一个指定长度的字符串，常用于生成随机密码、激活码等
     *
     * @param len 指定生成字符串的长度
     * @return 返回生成的随机字符串
     */
    public static String getRandomStr(int len) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 判断给定的字符串是否完全由数字组成
     *
     * @param str 待检查的字符串
     * @return 如果字符串非空且全部由数字组成，则返回true；否则返回false
     */
    public static boolean isNumeric(String str) {
        // 检查字符串是否为null或空，因为null或空字符串不符合数字字符串的定义
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 遍历字符串中的每个字符，检查是否所有字符都是数字
        for (char c : str.toCharArray()) {
            // 如果当前字符不是数字，则该字符串不是数字字符串，返回false
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        // 所有字符都是数字，返回true
        return true;
    }

    /**
     * 生成条形码图片
     *
     * @param data 条形码中包含的数据
     * @return 生成的条形码Bitmap对象，如果生成失败则返回null
     */
    public static Bitmap generateBarcode(String data, int width, int height) {
        // 创建MultiFormatWriter实例，用于生成条形码
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            // 使用MultiFormatWriter生成BitMatrix对象，即条形码的矩阵表示
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.CODE_128, width * 5 / 6, height / 3);
            // 创建BarcodeEncoder实例，用于将BitMatrix转换为Bitmap
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            // 将BitMatrix转换为Bitmap，并返回
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            // 如果生成条形码过程中发生异常，则打印异常信息并返回null
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成二维码图片
     *
     * @param data 二维码中包含的数据
     * @return 生成的二维码Bitmap对象，如果生成失败则返回null
     */
    public static Bitmap generateQRCode(String data) {
        // 创建MultiFormatWriter实例，用于生成二维码
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            // 使用MultiFormatWriter生成BitMatrix对象，即二维码的矩阵表示
            BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 400, 400);
            // 创建BarcodeEncoder实例，用于将BitMatrix转换为Bitmap
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            // 将BitMatrix转换为Bitmap，并返回
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            // 如果生成二维码过程中发生异常，则打印异常信息并返回null
            e.printStackTrace();
            return null;
        }
    }

    //将layout转为bitmap
    public Bitmap layoutToBitmap(LinearLayout layout, int width, int height) {
        // 获取 editor_container 的视图
        View view = layout.getChildAt(0);

        // 测量视图的尺寸
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, width, height);

        // 创建一个 Bitmap 并将视图绘制到 Bitmap 上
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    //设置文本的对齐方式
    public void setTextViewAlignment(TextView textView, int alignment) {
        textView.setTextAlignment(alignment);
        //刷新文本来刷新对齐方式
        textView.setText(textView.getText());
    }
}
