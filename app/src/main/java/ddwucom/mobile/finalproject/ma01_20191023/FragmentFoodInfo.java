package ddwucom.mobile.finalproject.ma01_20191023;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FragmentFoodInfo extends Fragment implements View.OnClickListener{

    public static final String TAG = "APIActivity";

    View view;
    EditText etTarget;
    ListView lvList;
    ImageButton search;
    String apiAddress;

    String query;

    MyFoodAdapter adapter;
    ArrayList<FoodDTO> resultList;
    FoodXMLParser parser;
    NetworkManager networkManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_foodinfo, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("식품영양정보 찾기");
        actionBar.setDisplayHomeAsUpEnabled(true);

        etTarget = view.findViewById(R.id.etTarget);
        lvList = view.findViewById(R.id.lvList);
        search = view.findViewById(R.id.btnSearch);

        search.setOnClickListener(this);


        resultList = new ArrayList();
        adapter = new MyFoodAdapter(getActivity(), R.layout.listview_food, resultList);
        lvList.setAdapter(adapter);

        apiAddress = getResources().getString(R.string.api_url);
        parser = new FoodXMLParser();

        networkManager = new NetworkManager(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 임시 파일 삭제

    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSearch:
                query = etTarget.getText().toString();  // UTF-8 인코딩 필요
                // OpenAPI 주소와 query 조합 후 서버에서 데이터를 가져옴
                // 가져온 데이터는 파싱 수행 후 어댑터에 설정
                try {
                    new NetworkAsyncTask().execute(apiAddress + URLEncoder.encode(query, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                break;
        }
    }

    class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(getActivity(), "Wait", "검색중입니다...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = null;
            // networking

            // parsing - 수행시간이 많이 걸릴 경우 이곳(스레드 내부)에서 수행하는 것을 고려
            // parsing 을 이곳에서 수행할 경우 AsyncTask의 반환타입을 적절히 변경
            result = networkManager.downloadContents(address);

//            Log.d(TAG, result);
            if(result == null)
                return "Error!";

            resultList = parser.parse(result);

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            // parsing - 수행시간이 짧을 경우 이 부분에서 수행하는 것을 고려
            TextView txt = view.findViewById(R.id.tvSearchResult);
            if(result == null){
                txt.setText("검색결과가 없습니다.");
            }
            else{
                txt.setText("검색결과는 다음과 같습니다.");
            }
            adapter.setList(resultList);    // Adapter 에 결과 List 를 설정 후 notify
            progressDlg.dismiss();
        }

    }
}
