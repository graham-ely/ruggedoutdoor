package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.PurityReport;
import ruggedoutdoors.cleanwater.model.PurityReportManagementFacade;
import ruggedoutdoors.cleanwater.model.UserManagementFacade;

/**
 * Created by gde on 4/5/17.
 */

public class GraphActivity extends AppCompatActivity {
    private GraphView mGraph;
    private GraphView mGraph2;
    private TextView yearInput;
    private TextView latInput;
    private TextView longInput;
    private Button yearGo;
    private Button latGo;
    private Button longGo;

    private Model model = Model.getInstance();
    private ArrayList<PurityReport> purityReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        model.loadPurityReportData(new File(getFilesDir(), PurityReportManagementFacade.PURITY_REPORT_TEXT_FILE_NAME));

        purityReports = (ArrayList) model.getPurityReportArray();

        mGraph = (GraphView) findViewById(R.id.graph);
        mGraph2 = (GraphView) findViewById(R.id.graph2);
        yearInput = (TextView) findViewById(R.id.graph_year_input);
        latInput = (TextView) findViewById(R.id.graph_lat_input);
        longInput = (TextView) findViewById(R.id.graph_long_input);
        yearGo = (Button) findViewById(R.id.graph_year_go);
        latGo = (Button) findViewById(R.id.graph_lat_go);
        longGo = (Button) findViewById(R.id.graph_long_go);

        yearGo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updateGraphs();
                return false;
            }
        });


        //Graph 1
        mGraph.setTitle("History Graph (Virus PPM)");

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        Calendar cal = Calendar.getInstance();


        for( PurityReport report : purityReports ) {
            cal.setTime(report.getDateTime());
            int month = cal.get(Calendar.MONTH);
            series.appendData(new DataPoint( month, report.getVirusPPM()), true, 100);
            series2.appendData(new DataPoint( month, report.getContaminantPPM()), true, 100);
        }

        mGraph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        mGraph.getGridLabelRenderer().setVerticalAxisTitle("Virus PPM");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);


        mGraph.addSeries(series);


        //Graph 2
        mGraph2.setTitle("History Graph (Contaminant PPM)");


        mGraph2.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        mGraph2.getGridLabelRenderer().setVerticalAxisTitle("Contaminant PPM");
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);

        mGraph2.addSeries(series2);
    }

    public void updateGraphs() {
        model.loadPurityReportData(new File(getFilesDir(), PurityReportManagementFacade.PURITY_REPORT_TEXT_FILE_NAME));
        List<PurityReport> purityReports = model.getPurityReportArray();
        mGraph.removeAllSeries();
        mGraph2.removeAllSeries();

        Calendar cal = Calendar.getInstance();

        Integer year = yearInput.getText().toString().isEmpty() ? null : Integer.parseInt(yearInput.getText().toString());
        Double lat = latInput.getText().toString().isEmpty() ? null : Double.parseDouble(latInput.getText().toString());
        Double lon = longInput.getText().toString().isEmpty() ? null : Double.parseDouble(longInput.getText().toString());

        if (year != null) {
            for (int i = 0; i < purityReports.size(); i++) {
                cal.setTime(purityReports.get(i).getDateTime());
                int year1 = cal.get(Calendar.YEAR);
                if (year != year) {
                    purityReports.remove(i);
                    i--;
                }
            }
        }

        if (lat != null) {
            for (int i = 0; i < purityReports.size(); i++) {
                if (purityReports.get(i).getLatitude() != lat) {
                    purityReports.remove(i);
                    i--;
                }
            }
        }

        if (lon != null) {
            for (int i = 0; i < purityReports.size(); i++) {
                if (purityReports.get(i).getLongitude() != lon) {
                    purityReports.remove(i);
                    i--;
                }
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

        for( PurityReport report : purityReports ) {
            cal.setTime(report.getDateTime());
            int month = cal.get(Calendar.MONTH);
            series.appendData(new DataPoint( month, report.getVirusPPM()), true, 100);
            series2.appendData(new DataPoint( month, report.getContaminantPPM()), true, 100);
        }

        mGraph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        mGraph.getGridLabelRenderer().setVerticalAxisTitle("Virus PPM");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);


        mGraph.addSeries(series);


        //Graph 2
        mGraph2.setTitle("History Graph (Contaminant PPM)");


        mGraph2.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        mGraph2.getGridLabelRenderer().setVerticalAxisTitle("Contaminant PPM");
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);
        series2.setThickness(8);

        mGraph2.addSeries(series2);
    }

}
