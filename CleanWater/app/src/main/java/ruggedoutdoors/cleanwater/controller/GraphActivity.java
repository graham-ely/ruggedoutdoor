package ruggedoutdoors.cleanwater.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.List;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.PurityReport;
import ruggedoutdoors.cleanwater.model.Report;

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

    private Model model = new Model(this);
    private List<Report> purityReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        model.open();
        purityReports = model.getPurityReportArray();
        model.close();

        mGraph = (GraphView) findViewById(R.id.graph);
        mGraph2 = (GraphView) findViewById(R.id.graph2);
        yearInput = (TextView) findViewById(R.id.graph_year_input);
        latInput = (TextView) findViewById(R.id.graph_lat_input);
        longInput = (TextView) findViewById(R.id.graph_long_input);
        yearGo = (Button) findViewById(R.id.graph_year_go);
        latGo = (Button) findViewById(R.id.graph_lat_go);
        longGo = (Button) findViewById(R.id.graph_long_go);

        yearGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraphs();
            }
        });

        latGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraphs();
            }
        });

        longGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGraphs();
            }
        });

        //Graph 1
        mGraph.setTitle("History Graph (Virus PPM)");

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();
        Calendar cal = Calendar.getInstance();


        for( Report report : purityReports ) {
            cal.setTime(report.getDateTime());
            int month = cal.get(Calendar.MONTH);
            series.appendData(new DataPoint( month, ((PurityReport) report).getVirusPPM()), true, 100);
            series2.appendData(new DataPoint( month, ((PurityReport) report).getContaminantPPM()), true, 100);
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
        model.open();
        List<Report> purityReports = model.getPurityReportArray();
        model.close();
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
                if (year != (int) year1) {
                    purityReports.remove(i);
                    i--;
                }
            }
        }

        if (lat != null) {
            for (int i = 0; i < purityReports.size(); i++) {
                if (Double.compare(purityReports.get(i).getLatitude(),lat) != 0) {
                    purityReports.remove(i);
                    i--;
                }
            }
        }


        if (lon != null) {
            for (int i = 0; i < purityReports.size(); i++) {
                if (Double.compare(purityReports.get(i).getLongitude(), lon) != 0) {
                    purityReports.remove(i);
                    i--;
                }
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

        for( Report report : purityReports ) {
            cal.setTime(report.getDateTime());
            int month = cal.get(Calendar.MONTH);
            series.appendData(new DataPoint( month, ((PurityReport) report).getVirusPPM()), true, 100);
            series2.appendData(new DataPoint( month, ((PurityReport) report).getContaminantPPM()), true, 100);
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
