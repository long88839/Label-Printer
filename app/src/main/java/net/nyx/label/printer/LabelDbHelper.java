package net.nyx.label.printer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LabelDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "layout_templates.db";
    private static final int DATABASE_VERSION = 1;

    public LabelDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建主表
        db.execSQL("CREATE  TABLE layout_template (" +
                "template_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "template_name TEXT NOT NULL," +
                "created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        // 创建TextView配置表
        db.execSQL("CREATE TABLE text_view_config (" +
                "view_id INTEGER," +
                "template_id INTEGER," +
                "text_content TEXT," +
                "text_size INTEGER," +
                "line_spacing_multiplier INTEGER," +
                "letter_spacing INTEGER," +
                "gravity INTEGER," +
                "style INTEGER," +
                "FOREIGN KEY(template_id) REFERENCES layout_template(template_id) ON DELETE CASCADE" +
                ")");

        // 创建ImageView配置表
        db.execSQL("CREATE TABLE image_view_config (" +
                "view_id INTEGER," +
                "template_id INTEGER," +
                "image_content TEXT," +
                "view_order INTEGER," +
                "FOREIGN KEY(template_id) REFERENCES layout_template(template_id) ON DELETE CASCADE" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 版本升级逻辑（如ALTER TABLE）
    }
}