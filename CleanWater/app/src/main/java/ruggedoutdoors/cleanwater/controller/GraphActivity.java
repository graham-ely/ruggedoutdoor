package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.ArrayList;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.PurityReport;
import ruggedoutdoors.cleanwater.model.UserManagementFacade;

/**
 * Created by gde on 4/5/17.
 */

public class GraphActivity extends AppCompatActivity {
    private GraphView mGraph;
    private GraphView mGraph2;
    private Model model = Model.getInstance();
    private ArrayList<PurityReport> purityReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        model.loadUserData(new File(getFilesDir(), UserManagementFacade.DEFAULT_TEXT_FILE_NAME));

        purityReports = (ArrayList) model.getPurityReportArray();

        mGraph = (GraphView) findViewById(R.id.graph);
        mGraph2 = (GraphView) findViewById(R.id.graph2);

        //Graph 1
        mGraph.setTitle("History Graph (Virus PPM)");

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        for( PurityReport report : purityReports ) {
            series.appendData(new DataPoint( report.getDateTime().getMonth(), report.getVirusPPM()), true, 100);
        }

        mGraph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        mGraph.getGridLabelRenderer().setVerticalAxisTitle("Virus PPM");

        mGraph.addSeries(series);

        //Graph 2
        mGraph2.setTitle("History Graph (Contaminant PPM)");

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>();

        for( PurityReport report : purityReports ) {
            series.appendData(new DataPoint( report.getDateTime().getMonth(), report.getContaminantPPM()), true, 100);
        }

        mGraph2.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        mGraph2.getGridLabelRenderer().setVerticalAxisTitle("Contaminant PPM");

        mGraph2.addSeries(series2);
    }

}
