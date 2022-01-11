package ddwucom.mobile.finalproject.ma01_20191023;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class FragmentInsertFood extends Fragment implements View.OnClickListener {

    private static final int REQUEST_TAKE_PHOTO = 200;
    private static final int REQUEST_TAKE_THUMBNAIL = 100;

    String mCurrentPhotoPath;

    EditText etTitle;
    EditText etExDate;
    EditText etInDate;
    EditText etMemo;
    ImageView mImageView;

    Cursor cursor;
    FridgeDBHelper helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_insertfridge, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("푸드 등록");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnAdd = (Button)view.findViewById(R.id.btnAdd);
        etTitle = view.findViewById(R.id.etTitle);
        etExDate = view.findViewById(R.id.etExDate);
        etInDate = view.findViewById(R.id.etInDate);
        etMemo = view.findViewById(R.id.etMemo);
        mImageView = (ImageView)view.findViewById(R.id.imageView);

        btnAdd.setOnClickListener(this);
        helper = new FridgeDBHelper(container.getContext());

        Log.i("image", getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        Log.i("image", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());

        mImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    외부 카메라 호출
//                    Toast.makeText(getActivity(), "이미지 추가 ", Toast.LENGTH_LONG).show();
                    dispatchTakePictureIntent();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /*원본 사진 파일 저장*/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // 이미지파일생성
            File imageFile = null;
            try{
                imageFile = createImageFile();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            if (imageFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getActivity(), "ddwucom.mobile.finalproject.ma01_20191023.fileprovider",imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    /*사진의 크기를 ImageView에서 표시할 수 있는 크기로 변경*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap bmRotated = rotateBitmap(bitmap, orientation);

        mImageView.setImageBitmap(bmRotated);
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

    /*현재 시간 정보를 사용하여 파일 정보 생성*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if(requestCode == REQUEST_TAKE_THUMBNAIL && resultCode == RESULT_OK) {
            Bundle extra = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extra.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            setPic();
        }
    }

    @Override
    public void onClick(View view) {
        String pattern = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
        switch (view.getId()) {
            case R.id.btnAdd:
                if(etTitle.getText().toString().equals("") || etTitle.getText().toString().equals("") ||
                        etExDate.getText().toString().equals("") || etInDate.getText().toString().equals("") || etMemo.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "모든 내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                else if(!Pattern.matches(pattern, etExDate.getText().toString())){
                    Toast.makeText(getActivity(), "날짜형식에 맞게 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else if(!Pattern.matches(pattern, etInDate.getText().toString())){
                    Toast.makeText(getActivity(), "날짜형식에 맞게 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else{
                    //DB에 촬영한 사진의 파일 경로 및 내용 저장\
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("insert into contact_table values (null, '" + etTitle.getText().toString() + "', '" + etExDate.getText().toString() + "', '"
                            + etInDate.getText().toString() + "', '" + etMemo.getText().toString() + "', '" + mCurrentPhotoPath + "');");
                    helper.close();
                    Toast.makeText(getActivity(), "푸드 등록 완료", Toast.LENGTH_LONG).show();
                    etTitle.setText("");
                    etExDate.setText("");
                    etInDate.setText("");
                    etMemo.setText("");
                    Log.d("image", mCurrentPhotoPath);
                }
                break;

        }

    }
}
