package ddwucom.mobile.finalproject.ma01_20191023;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FridgeDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "contact_db";
    public final static String TABLE_NAME = "contact_table";
    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";

    public final static String COL_EX_DATE = "expireDate";
    public final static String COL_IN_DATE = "intakeDate";
    public final static String COL_MEMO = "memo";
    public final static String COL_IMAGE = "image";

    public FridgeDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_ID + " integer primary key autoincrement,"
                + COL_TITLE + " TEXT, " +  COL_EX_DATE + " TEXT, "
                + COL_IN_DATE + " TEXT, " + COL_MEMO + " TEXT, " + COL_IMAGE + " TEXT);");

//		샘플 데이터
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '우유', '2022-01-04', '2022-01-14', '재구매', '/storage/emulated/0/Android/data/ddwucom.mobile.finalproject.ma01_20191023/files/Pictures/JPEG_20211226_194053_1645589671861443858.jpg');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '콩나물', '2021-12-31', '2022-01-14', '국거리용', '/storage/emulated/0/Android/data/ddwucom.mobile.finalproject.ma01_20191023/files/Pictures/JPEG_20211226_194149_5243688648450111423.jpg');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '춘천닭갈비떡볶이', '2021-12-25', '2022-12-25', '마켓컬리 구매', '/storage/emulated/0/Android/data/ddwucom.mobile.finalproject.ma01_20191023/files/Pictures/JPEG_20211226_194256_6391853958010665262.jpg');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }

}

