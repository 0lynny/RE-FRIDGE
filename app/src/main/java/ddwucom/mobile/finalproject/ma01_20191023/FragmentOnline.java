package ddwucom.mobile.finalproject.ma01_20191023;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class FragmentOnline extends Fragment {

    private Intent ikurly;
    private Intent intentCoupang;
    private Intent intentSSG;
    private Intent intentLotte;
    private Intent intentGS;
    private Intent intentCJ;
    private final String packageKurly = "com.dbs.kurly.m2";
    private final String packageCoupang = "com.coupang.mobile";
    private final String packageSSG = "kr.co.ssg.earlymorning";
    private final String packageLotte = "com.lottesuper.mobile";
    private final String packageGS = "com.gsretail.android.smapp";
    private final String packageCJ = "com.susoft.CJONmart";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_online, container, false);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("온라인 구매");
        actionBar.setDisplayHomeAsUpEnabled(true);



        ImageButton btnKurly = (ImageButton) view.findViewById(R.id.btnkurly);
        btnKurly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ikurly = getActivity().getPackageManager().getLaunchIntentForPackage(packageKurly);
                    startActivity(ikurly);
                }catch (Exception e){
                    String url = "market://details?id=" + packageKurly;
                    Intent downloadKurly = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(downloadKurly);
                }
            }
        });

        ImageButton btnCoupang = (ImageButton) view.findViewById(R.id.btncoupang);
        btnCoupang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    intentCoupang = getActivity().getPackageManager().getLaunchIntentForPackage(packageCoupang);
                    startActivity(intentCoupang);
                }catch (Exception e){
                    String url = "market://details?id=" + packageCoupang;
                    Intent downloadCoupang = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(downloadCoupang);
                }
            }
        });

        ImageButton btnSSG = (ImageButton) view.findViewById(R.id.btnssg);
        btnSSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    intentSSG = getActivity().getPackageManager().getLaunchIntentForPackage(packageSSG);
                    startActivity(intentSSG);
                }catch (Exception e){
                    String url = "market://details?id=" + packageSSG;
                    Intent downloadSSG = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(downloadSSG);
                }
            }
        });

        ImageButton btnLotte = (ImageButton) view.findViewById(R.id.btnlotte);
        btnLotte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    intentLotte = getActivity().getPackageManager().getLaunchIntentForPackage(packageLotte);
                    startActivity(intentLotte);
                }catch (Exception e){
                    String url = "market://details?id=" + packageLotte;
                    Intent downloadLotte = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(downloadLotte);
                }
            }
        });

        ImageButton btnGS = (ImageButton) view.findViewById(R.id.btnGS);
        btnGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    intentGS = getActivity().getPackageManager().getLaunchIntentForPackage(packageGS);
                    startActivity(intentGS);
                }catch (Exception e){
                    String url = "market://details?id=" + packageGS;
                    Intent downloadGS = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(downloadGS);
                }
            }
        });

        ImageButton btnCJ = (ImageButton) view.findViewById(R.id.btnCJ);
        btnCJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    intentSSG = getActivity().getPackageManager().getLaunchIntentForPackage(packageCJ);
                    startActivity(intentCJ);
                }catch (Exception e){
                    String url = "market://details?id=" + packageCJ;
                    Intent downloadCJ = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(downloadCJ);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
