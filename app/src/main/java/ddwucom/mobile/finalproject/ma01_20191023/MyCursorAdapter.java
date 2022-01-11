package ddwucom.mobile.finalproject.ma01_20191023;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

public class MyCursorAdapter extends  CursorAdapter{
    LayoutInflater inflater;
    int layout;

    public MyCursorAdapter(Context context, int layout, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder();
        view.setTag(holder);
        return view;
    }
    

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        long calcDay = Dday(cursor.getString(cursor.getColumnIndex(FridgeDBHelper.COL_EX_DATE)));
        System.out.println(calcDay);

        if(holder.tvTitle == null){
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvExpedite = view.findViewById(R.id.tvExdate);
            holder.tvDay = view.findViewById(R.id.tvDay);
            holder.tvStatus = view.findViewById(R.id.tvStatus);
            holder.tvToday = view.findViewById(R.id.tvToday);
            holder.image = view.findViewById(R.id.imageView2);
        }

        holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex(FridgeDBHelper.COL_TITLE)));
        holder.tvExpedite.setText(cursor.getString(cursor.getColumnIndex(FridgeDBHelper.COL_EX_DATE)));
        File imgFile = new File(cursor.getString(cursor.getColumnIndex(FridgeDBHelper.COL_IMAGE)));

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(cursor.getString(cursor.getColumnIndex(FridgeDBHelper.COL_IMAGE)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap bmRotated = rotateBitmap(myBitmap, orientation);

            holder.image.setImageBitmap(bmRotated);

        }

//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(FridgeDBHelper.COL_IMAGE)), bmOptions);
//        holder.image.setImageBitmap(bitmap);
        if(calcDay == 0){
            holder.tvDay.setText("");
            holder.tvStatus.setText("");
            holder.tvToday.setText("오늘 만료됨");
        }
        else if(calcDay > 0) {
            holder.tvDay.setText(String.valueOf(calcDay));
            holder.tvStatus.setText("일 남음");
            holder.tvToday.setText("");
        }
        else {
            holder.tvDay.setText(String.valueOf(Math.abs(calcDay)));
            holder.tvStatus.setText("일 지남");
            holder.tvToday.setText("");
        }

    }

    static class ViewHolder {

        public ViewHolder() {
            tvTitle = null;
            tvExpedite = null;
            tvDay = null;
            tvStatus = null;
            tvToday = null;
            image = null;
        }

        TextView tvTitle;
        TextView tvExpedite;
        TextView tvDay;
        TextView tvStatus;
        TextView tvToday;
        ImageView image;
    }

    public static long Dday(String mday) {
        if (mday == null )
            return 0;
        mday = mday.trim();
        int first = mday.indexOf("-");
        int last = mday.lastIndexOf("-");
        int year = Integer.parseInt(mday.substring(0 , first ));
        int month = Integer.parseInt(mday.substring(first + 1 , last ));
        int day = Integer.parseInt(mday.substring( last + 1 , mday.length()));

        GregorianCalendar cal = new GregorianCalendar();
        long currentTime = cal.getTimeInMillis() / (1000*60*60*24);
        cal.set(year,month - 1 , day);
        long birthTime = cal.getTimeInMillis() / (1000*60*60*24);
        int interval = (int)( birthTime - currentTime );

        return interval;
    }



    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


}