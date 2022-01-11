package ddwucom.mobile.finalproject.ma01_20191023;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHome extends Fragment implements View.OnClickListener {

    ListView lvFoods = null;
    FridgeDBHelper helper;
    Cursor cursor;
    MyCursorAdapter adapter;
    Context ct;

    String sort;
    CheckBox check1;
    CheckBox check2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ct = container.getContext();
        View Fridge = inflater.inflate(R.layout.fragment_home, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("나의 냉장고");
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvFoods = (ListView) Fridge.findViewById(R.id.lvContacts);
        check1 = Fridge.findViewById(R.id.checkRegister);
        check1.setOnClickListener(this);
        check2 = Fridge.findViewById(R.id.checkExp);
        check2.setOnClickListener(this);
        sort = FridgeDBHelper.COL_ID;

        helper = new FridgeDBHelper(getActivity());
        adapter = new MyCursorAdapter(getActivity(), R.layout.listview_layout, null);
        lvFoods.setAdapter(adapter);

//		리스트 뷰 클릭 처리
        lvFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentupdateFood fragment = new FragmentupdateFood();

                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(id));
                fragment.setArguments(bundle);
                transaction.replace(R.id.frameLayout, fragment);
                transaction.commit();
//                Toast.makeText(getActivity(), "리스트뷰 클릭!", Toast.LENGTH_LONG).show();
            }
        });

//        리스트 뷰 롱클릭 처리
        lvFoods.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("항목 삭제")
                        .setMessage("이 항목을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = helper.getWritableDatabase();
                                db.execSQL("delete from contact_table where _id = " + id);
                                cursor = db.rawQuery("select * from " + FridgeDBHelper.TABLE_NAME, null);
                                adapter.changeCursor(cursor);
                                helper.close();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
        return Fridge;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        showList(sort);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }

    public  void showList(String sort){
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + FridgeDBHelper.TABLE_NAME + " order by " + sort, null);

        adapter.changeCursor(cursor);
        helper.close();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkExp:
                check1.setChecked(false);
                sort = FridgeDBHelper.COL_EX_DATE;
//                Toast.makeText(getActivity(), "유통기한 순 ", Toast.LENGTH_LONG).show();
                showList(sort);
                break;

            case R.id.checkRegister:
                check2.setChecked(false);
                sort = FridgeDBHelper.COL_ID;
//              Toast.makeText(getActivity(), "유통기한 순 ", Toast.LENGTH_LONG).show();
                showList(sort);
                break;
        }
    }


}
