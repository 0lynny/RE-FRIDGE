package ddwucom.mobile.finalproject.ma01_20191023;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyFoodAdapter extends BaseAdapter {

    public static final String TAG = "MyFoodAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<FoodDTO> list;
//    private NaverNetworkManager networkManager = null;


    public MyFoodAdapter(Context context, int resource, ArrayList<FoodDTO> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;

//        networkManager = new NaverNetworkManager(context);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public FoodDTO getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView with position : " + position);
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvGroup = view.findViewById(R.id.tvGroup);
            viewHolder.tvDesc = view.findViewById(R.id.tvDesc);
            viewHolder.tvSize = view.findViewById(R.id.tvsize);
            viewHolder.tvnut1 = view.findViewById(R.id.tvnut1);
            viewHolder.tvnut2 = view.findViewById(R.id.tvnut2);
            viewHolder.tvnut3 = view.findViewById(R.id.tvnut3);
            viewHolder.tvnut4 = view.findViewById(R.id.tvnut4);
            viewHolder.tvnut5 = view.findViewById(R.id.tvnut5);
            viewHolder.tvnut6 = view.findViewById(R.id.tvnut6);
            viewHolder.tvnut7 = view.findViewById(R.id.tvnut7);
            viewHolder.tvnut8 = view.findViewById(R.id.tvnut8);
            viewHolder.tvnut9 = view.findViewById(R.id.tvnut9);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        FoodDTO dto = list.get(position);

        viewHolder.tvGroup.setText(dto.getGROUP_NAME());
        viewHolder.tvDesc.setText(dto.getDESC_KOR());
        viewHolder.tvSize.setText(dto.getSERVING_SIZE());
        viewHolder.tvnut1.setText(dto.getNUTR_CONT1());
        viewHolder.tvnut2.setText(dto.getNUTR_CONT2());
        viewHolder.tvnut3.setText(dto.getNUTR_CONT3());
        viewHolder.tvnut4.setText(dto.getNUTR_CONT4());
        viewHolder.tvnut5.setText(dto.getNUTR_CONT5());
        viewHolder.tvnut6.setText(dto.getNUTR_CONT6());
        viewHolder.tvnut7.setText(dto.getNUTR_CONT7());
        viewHolder.tvnut8.setText(dto.getNUTR_CONT8());
        viewHolder.tvnut9.setText(dto.getNUTR_CONT9());
        return view;
    }


    public void setList(ArrayList<FoodDTO> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //    ※ findViewById() 호출 감소를 위해 필수로 사용할 것
    static class ViewHolder {
        public TextView tvGroup = null;
        public TextView tvDesc = null;
        public TextView tvSize = null;
        public TextView tvnut1 = null;
        public TextView tvnut2 = null;
        public TextView tvnut3 = null;
        public TextView tvnut4 = null;
        public TextView tvnut5 = null;
        public TextView tvnut6 = null;
        public TextView tvnut7 = null;
        public TextView tvnut8 = null;
        public TextView tvnut9 = null;

    }


}
