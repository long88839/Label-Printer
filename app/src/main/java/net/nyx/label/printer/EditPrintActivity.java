package net.nyx.label.printer;

import androidx.appcompat.app.AppCompatActivity;

import net.nyx.printerservice.print.IPrinterService;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditPrintActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PrinterMainActivity";

    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler();
    private IPrinterService printerService;
    int layoutViewID;
    int sizeWidth;
    int sizeHeight;
    Utils utils = Utils.getInstance();

    private LinearLayout editorContainer;
    private Button editButton;
    private Button print;

    private TextView[] textViews = new TextView[21];
    private ImageView Barcode_view;
    private ImageView QRCode_view;

    private LinearLayout components_layout_text;
    private LinearLayout components_layout_barcode;
    private LinearLayout components_layout_QRCode;
    private LinearLayout components_layout_tip;
    private TextView components_tv1;
    private TextView components_btv;
    private TextView components_btv1;
    private CheckBox components_checkbox;
    private SeekBar font_size_seekbar;
    private SeekBar line_spacing_seekbar;
    private SeekBar margin_seekbar;
    private LinearLayout components_left_btn;
    private LinearLayout components_center_btn;
    private LinearLayout components_right_btn;
    private CheckBox components_cb1;

    private Dialog componentsDialog;
    private EditText dialogEditText;
    private Button dialogBackButton;
    private Button dialogClearButton;
    private Button dialogConfirmButton;

    private int editViewId = -2;
    private int oldEditViewId = -2;

    private TextView components_bold_btn;
    private TextView components_italic_btn;
    private TextView components_underline_btn;

    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.edit_print);
        bindService();
        initView();
        initDialog();
        // 获取传递的imageViewType值
        initComponentsView();
        initLayout();
    }

    private ServiceConnection connService = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            printerService = null;
            // 尝试重新bind
            handler.postDelayed(() -> bindService(), 5000);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            printerService = IPrinterService.Stub.asInterface(service);
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("net.nyx.printerservice");
        intent.setAction("net.nyx.printerservice.IPrinterService");
        bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(connService);
    }

    // 初始化控件
    private void initView() {
        editorContainer = findViewById(R.id.editor_container);
        editButton = findViewById(R.id.update_button);
        editButton.setOnClickListener(this);
        print = findViewById(R.id.print);
        print.setOnClickListener(this);

    }

    // 初始化组件对话框
    private void initComponentsView() {
        components_layout_barcode = findViewById(R.id.components_layout_barcode);
        components_layout_QRCode = findViewById(R.id.components_layout_QRCode);
        components_layout_text = findViewById(R.id.components_layout_text);
        components_layout_tip = findViewById(R.id.components_layout_tip);
        components_tv1 = findViewById(R.id.components_tv1);
        components_tv1.setOnClickListener(this);
        components_btv = findViewById(R.id.components_btv);
        components_btv.setOnClickListener(this);
        components_btv1 = findViewById(R.id.components_btv1);
        components_btv1.setOnClickListener(this);
        components_checkbox = findViewById(R.id.components_cb);
        components_checkbox.setOnClickListener(this);
        font_size_seekbar = findViewById(R.id.font_size_seekbar);
        line_spacing_seekbar = findViewById(R.id.line_spacing_seekbar);
        margin_seekbar = findViewById(R.id.margin_seekbar);

        components_left_btn = findViewById(R.id.components_left_btn);
        components_left_btn.setOnClickListener(this);
        components_center_btn = findViewById(R.id.components_center_btn);
        components_center_btn.setOnClickListener(this);
        components_right_btn = findViewById(R.id.components_right_btn);
        components_right_btn.setOnClickListener(this);
        components_cb1 = findViewById(R.id.components_cb1);
        components_cb1.setOnClickListener(this);

        components_bold_btn = findViewById(R.id.components_bold_btn);
        components_bold_btn.setOnClickListener(this);

        components_italic_btn = findViewById(R.id.components_italic_btn);
        components_italic_btn.setOnClickListener(this);

        components_underline_btn = findViewById(R.id.components_underline_btn);
        components_underline_btn.setOnClickListener(this);
        font_size_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViews[editViewId].setTextSize(TypedValue.COMPLEX_UNIT_PX, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        line_spacing_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViews[editViewId].setLineSpacing(progress, 1.0f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        margin_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float letterSpacing = progress / 100.0f; // 将进度值转换为字符间距，范围为 0.0 到 1.0
                textViews[editViewId].setLetterSpacing(letterSpacing);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // 初始化对话框
    private void initDialog() {
        componentsDialog = new Dialog(this);
        componentsDialog.setContentView(R.layout.edit_print_dialog);
        componentsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialogEditText = componentsDialog.findViewById(R.id.dialog_editText);
        dialogBackButton = componentsDialog.findViewById(R.id.dialog_back_button);
        dialogClearButton = componentsDialog.findViewById(R.id.dialog_clear_button);
        dialogConfirmButton = componentsDialog.findViewById(R.id.dialog_confirm_button);

        dialogBackButton.setOnClickListener(this);
        dialogClearButton.setOnClickListener(this);
        dialogConfirmButton.setOnClickListener(this);
    }

    // 初始化布局
    private void initLayout() {
        // 获取传递的imageViewType值
        layoutViewID = getIntent().getIntExtra("linearLayout_id", 0);
        sizeWidth = mmToPx(Constant.All_TEMPLATE_LAYOUT_SIZES[layoutViewID][0]);
        sizeHeight = mmToPx(Constant.All_TEMPLATE_LAYOUT_SIZES[layoutViewID][1]);
        // 根据imageViewType值动态包含布局
        includeLayout(Constant.All_TEMPLATE_LAYOUTS[layoutViewID]);
    }

    private static int mmToPx(float mm) {
        return (int) (mm * Resources.getSystem().getDisplayMetrics().xdpi / 25.4f);
    }

    // 动态包含布局
    private void includeLayout(int layoutResId) {
        editorContainer.getLayoutParams().width = sizeWidth;
        editorContainer.getLayoutParams().height = sizeHeight;
        editorContainer.requestLayout(); // 强制刷新布局
        editorContainer.removeAllViews(); // 清空editor_container中的所有视图
        LayoutInflater inflater = LayoutInflater.from(this);
        View includedLayout = inflater.inflate(layoutResId, editorContainer, true);

        // 存储 TextView 的 ID 数组
        int[] textViewIds = {
                R.id.tv0, R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4,
                R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9,
                R.id.tv10, R.id.tv11, R.id.tv12, R.id.tv13, R.id.tv14,
                R.id.tv15, R.id.tv16, R.id.tv17, R.id.tv18, R.id.tv19,
                R.id.tv20
        };
        for (int i = 0; i < textViewIds.length; i++) {
            textViews[i] = includedLayout.findViewById(textViewIds[i]);
            textViews[i].setOnClickListener(this);
        }
        // 条形码和二维码初始化
        Barcode_view = includedLayout.findViewById(R.id.Barcode_view); // 获取imageView1
        Barcode_view.setOnClickListener(this);
        QRCode_view = includedLayout.findViewById(R.id.QRCode_view);
        QRCode_view.setOnClickListener(this);
        QRCode_view.setImageBitmap(utils.generateQRCode(components_btv1.getText().toString()));
        Barcode_view.setImageBitmap(utils.generateBarcode(components_btv.getText().toString(),sizeWidth,sizeHeight));
    }

    // 点击事件处理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.print:
                printLabel();
                break;
            case R.id.Barcode_view:
                dialogEditText.setText(textViews[0].getText());
                editViewId = 0;
                componentsDialog.show();
                break;
            case R.id.QRCode_view:
                dialogEditText.setText(components_btv1.getText());
                editViewId = -1;
                componentsDialog.show();
                break;
            case R.id.components_cb:
                if (components_checkbox.isChecked()) {
                    textViews[0].setVisibility(View.VISIBLE);
                } else {
                    textViews[0].setVisibility(View.GONE);
                }
                break;
            case R.id.tv1:
                dialogEditText.setText(textViews[1].getText());
                editViewId = 1;
                componentsDialog.show();
                break;
            case R.id.tv2:
                dialogEditText.setText(textViews[2].getText());
                editViewId = 2;
                componentsDialog.show();
                break;
            case R.id.tv3:
                dialogEditText.setText(textViews[3].getText());
                editViewId = 3;
                componentsDialog.show();
                break;
            case R.id.tv4:
                dialogEditText.setText(textViews[4].getText());
                editViewId = 4;
                componentsDialog.show();
                break;
            case R.id.tv5:
                dialogEditText.setText(textViews[5].getText());
                editViewId = 5;
                componentsDialog.show();
                break;
            case R.id.tv6:
                dialogEditText.setText(textViews[6].getText());
                editViewId = 6;
                componentsDialog.show();
                break;
            case R.id.tv7:
                dialogEditText.setText(textViews[7].getText());
                editViewId = 7;
                componentsDialog.show();
                break;
            case R.id.tv8:
                dialogEditText.setText(textViews[8].getText());
                editViewId = 8;
                componentsDialog.show();
                break;
            case R.id.tv9:
                dialogEditText.setText(textViews[9].getText());
                editViewId = 9;
                componentsDialog.show();
                break;
            case R.id.tv10:
                dialogEditText.setText(textViews[10].getText());
                editViewId = 10;
                componentsDialog.show();
                break;
            case R.id.tv11:
                dialogEditText.setText(textViews[11].getText());
                editViewId = 11;
                componentsDialog.show();
                break;
            case R.id.tv12:
                dialogEditText.setText(textViews[12].getText());
                editViewId = 12;
                componentsDialog.show();
                break;
            case R.id.tv13:
                dialogEditText.setText(textViews[13].getText());
                editViewId = 13;
                componentsDialog.show();
                break;
            case R.id.tv14:
                dialogEditText.setText(textViews[14].getText());
                editViewId = 14;
                componentsDialog.show();
                break;
            case R.id.tv15:
                dialogEditText.setText(textViews[15].getText());
                editViewId = 15;
                componentsDialog.show();
                break;
            case R.id.tv16:
                dialogEditText.setText(textViews[16].getText());
                editViewId = 16;
                componentsDialog.show();
                break;
            case R.id.tv17:
                dialogEditText.setText(textViews[17].getText());
                editViewId = 17;
                componentsDialog.show();
                break;
            case R.id.tv18:
                dialogEditText.setText(textViews[18].getText());
                editViewId = 18;
                componentsDialog.show();
                break;
            case R.id.tv19:
                dialogEditText.setText(textViews[19].getText());
                editViewId = 19;
                componentsDialog.show();
                break;
            case R.id.tv20:
                dialogEditText.setText(textViews[20].getText());
                editViewId = 20;
                componentsDialog.show();
                break;
            case R.id.components_btv:
            case R.id.components_tv1:
            case R.id.components_btv1:
                componentsDialog.show();
                break;
            case R.id.dialog_back_button:
                componentsDialog.dismiss();
                break;
            case R.id.dialog_clear_button:
                dialogEditText.setText("");
                break;
            case R.id.dialog_confirm_button:
                editView();
                componentsDialog.dismiss();
                break;
            case R.id.components_left_btn:
                utils.setTextViewAlignment(textViews[editViewId], View.TEXT_ALIGNMENT_TEXT_START);
                break;
            case R.id.components_center_btn:
                utils.setTextViewAlignment(textViews[editViewId], View.TEXT_ALIGNMENT_CENTER);
                break;
            case R.id.components_right_btn:
                utils.setTextViewAlignment(textViews[editViewId], View.TEXT_ALIGNMENT_TEXT_END);
                break;
            case R.id.components_cb1:
                editView();
                break;
            case R.id.components_bold_btn:
                // 字体加粗
                Typeface typeface = textViews[editViewId].getTypeface();
                int style = typeface != null ? typeface.getStyle() : Typeface.NORMAL;
                if(style == 1){
                    isBold = true;
                }else if(style == 2){
                    isItalic = true;
                } else if(style == 3){
                    isBold = true;
                    isItalic = true;
                }
                isBold = !isBold;
                toggleTextStyle(textViews[editViewId], Typeface.BOLD, isBold);
                break;
            case R.id.components_italic_btn:
                Typeface typeface1 = textViews[editViewId].getTypeface();
                int style1 = typeface1 != null ? typeface1.getStyle() : Typeface.NORMAL;
                if(style1 == 1){
                    isBold = true;
                }else if(style1 == 2){
                    isItalic = true;
                } else if(style1 == 3){
                    isBold = true;
                    isItalic = true;
                }
                isItalic = !isItalic;
                toggleTextStyle(textViews[editViewId], Typeface.ITALIC, isItalic);
                break;
            case R.id.components_underline_btn:
                toggleTextStyle(textViews[editViewId], Paint.UNDERLINE_TEXT_FLAG, isUnderline);
                isUnderline = !isUnderline;
                break;
        }
    }

    //图片、文本的内容输入
    private void editView() {
        components_layout_tip.setVisibility(View.GONE);
        //用于判断是否切换编辑控件，是否切换了控件，如果切换了，则需要重新初始化日期CheckBox
        if(oldEditViewId != editViewId){
            components_cb1.setChecked(false);
        }
        oldEditViewId = editViewId;
        //判断点击的是图片还是文本
        if(editViewId > 0){
            //文本
            components_layout_barcode.setVisibility(View.GONE);
            components_layout_text.setVisibility(View.VISIBLE);
            components_layout_QRCode.setVisibility(View.GONE);
            components_underline_btn.setPaintFlags(components_underline_btn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            font_size_seekbar.setProgress((int) textViews[editViewId].getTextSize());
            line_spacing_seekbar.setProgress((int) textViews[editViewId].getLineSpacingExtra());
            margin_seekbar.setProgress(textViews[editViewId].getPaddingTop());
            if(components_cb1.isChecked()){
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                components_tv1.setText(date);
                textViews[editViewId].setText(date);
            }else {
                components_tv1.setText(dialogEditText.getText().toString());
                textViews[editViewId].setText(dialogEditText.getText().toString());
            }
        }else if (editViewId == 0){
            //条形码
            components_layout_text.setVisibility(View.GONE);
            components_layout_barcode.setVisibility(View.VISIBLE);
            components_layout_QRCode.setVisibility(View.GONE);
            String inputText = dialogEditText.getText().toString();
            if (utils.isNumeric(inputText)) {
                components_btv.setText(inputText);
                Barcode_view.setImageBitmap(utils.generateBarcode(inputText, sizeWidth,sizeHeight));
            } else {
                Toast.makeText(this, "条形码数据非法输入", Toast.LENGTH_SHORT).show();
                components_btv.setText("123456789");
            }
            textViews[0].setText(components_btv.getText());
        }else if (editViewId == -1){
            //二维码
            components_layout_text.setVisibility(View.GONE);
            components_layout_barcode.setVisibility(View.GONE);
            components_layout_QRCode.setVisibility(View.VISIBLE);
            String inputText = dialogEditText.getText().toString();
            components_btv1.setText(inputText);
            QRCode_view.setImageBitmap(utils.generateQRCode(inputText));
        }
    }

    //打印标签
    private void printLabel() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        int alignment = prefs.getInt("print_alignment", 0);
        Log.d(TAG, "printLabel: "+alignment);
        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {
                int ret = 0;
                try {
                    // 将 editor_container 转换为 Bitmap
                    Bitmap bitmap = utils.layoutToBitmap(editorContainer, sizeWidth, sizeHeight);
                    if (!printerService.hasLabelLearning()) {
                        // label learning
                        ret = printerService.labelDetectAuto();
                    }
                    ret = printerService.labelLocateAuto();
                    if (ret == 0) {
                        printerService.printBitmap(bitmap, 0, alignment); // 0 表示黑白位图，0 表示左对齐
                        ret = printerService.labelPrintEnd();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //设置文本的字体样式
    @SuppressLint("WrongConstant")
    private void toggleTextStyle(TextView textView, int style, boolean isEnabled) {
        if (style == Paint.UNDERLINE_TEXT_FLAG) {
            // 处理下划线样式
            if (isEnabled) {
                textView.setPaintFlags(textView.getPaintFlags() & ~Paint.UNDERLINE_TEXT_FLAG);
            } else {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }
        }else {
            if(isBold == true && isItalic == true){
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD_ITALIC);
            }else if(isBold == true && isItalic == false){
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            }else if(isBold == false && isItalic == true){
                textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
            }else {
                textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            }
        }
    }
}