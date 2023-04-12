package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.retrofit.ApiBanHang;
import com.example.myapplication.retrofit.RetrofitClient;
import com.example.myapplication.utils.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initView();
        ActionToolBar();
        getdataChart();
    }

    private void getdataChart() {
        ArrayList<PieEntry> listdata = new ArrayList<>();
        compositeDisposable.add(apiBanHang.getThongKe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            long tongdoanhthu = 0;
                            if (thongKeModel.isSuccess()) {
                                for (int i = 0 ; i < thongKeModel.getResult().size(); i++) {
                                    String tensanpham = thongKeModel.getResult().get(i).getTensanpham();
                                    int tong = thongKeModel.getResult().get(i).getTong();
                                    tongdoanhthu += (Integer.parseInt(thongKeModel.getResult().get(i).getGia())) * tong;
                                    listdata.add(new PieEntry(tong, tensanpham));
                                }
                                PieDataSet pieDataSet = new PieDataSet(listdata, "Thống kê");
                                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                pieDataSet.setValueTextColor(Color.BLACK);
                                pieDataSet.setValueTextSize(12f);
                                pieDataSet.setValueLineColor(Color.BLACK);

                                PieData pieData = new PieData(pieDataSet);

                                pieChart.setData(pieData);
                                pieChart.animateXY(2000,2000);
                                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                pieChart.setCenterText("Tổng doanh thu: " + decimalFormat.format(tongdoanhthu) + ("₫"));
                                pieChart.setCenterTextColor(Color.BLACK);
                                pieChart.setCenterTextSize(18f);
                                pieChart.getBackground();
                                pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("logg", throwable.getMessage());
                        }
                ));
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}