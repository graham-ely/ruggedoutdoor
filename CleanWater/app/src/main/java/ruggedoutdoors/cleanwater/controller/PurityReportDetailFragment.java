package ruggedoutdoors.cleanwater.controller;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.Report;
import ruggedoutdoors.cleanwater.model.Reports;

/**
 * A fragment representing a single Report detail screen.
 * This fragment is either contained in a {@link PurityReportListActivity}
 * in two-pane mode (on tablets) or a {@link PurityReportDetailActivity}
 * on handsets.
 */
public class PurityReportDetailFragment extends Fragment {

    private TextView mReporterTextView, mLocationTextView, mTimestampTextView, mConditionTextView, mVirusPPMView, mContaminantPPMView;

    /**
     * The fragment arguments representing the  ID's that this fragment
     * represents.  Used to pass keys into other activities through Bundle/Intent
     */
    public static final String ARG_REPORT_ID = "report_id";

    /**
     * The report that this detail view is for.
     */
    private Model model = Model.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PurityReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid report passed to us
        if (model.hasActiveReport()) {

            //Log.d("CourseDetailFragment", "Passing over course: " + mCourse);
            //Log.d("CourseDetailFragment", "Got students: " + mCourse.getStudents().size());

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(model.getReportNumber() + "");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.purityreport_detail, container, false);

        if (model.hasActiveReport()) {
            mReporterTextView = (TextView) rootView.findViewById(R.id.viewPurityReport_reporterLabel);
            mReporterTextView.setText(model.getReporterName());

            mLocationTextView = (TextView) rootView.findViewById(R.id.viewPurityReport_locationLabel);
            mLocationTextView.setText(Double.toString(model.getLatitude()) + ", "
                    + Double.toString(model.getLongitude()));

            mTimestampTextView = (TextView) rootView.findViewById(R.id.viewPurityReport_timestampLabel);
            mTimestampTextView.setText(model.getDateTime());

            mConditionTextView = (TextView) rootView.findViewById(R.id.viewPurityReport_conditionLabel);
            mConditionTextView.setText(model.getWaterCondition());

            mVirusPPMView = (TextView) rootView.findViewById(R.id.viewPurityReport_virusPPMLabel);
            mVirusPPMView.setText(String.format("%.2f", model.getVirusPPM()));

            mContaminantPPMView = (TextView) rootView.findViewById(R.id.viewPurityReport_contaminantPPMLabel);
            mContaminantPPMView.setText(String.format("%.2f", model.getContaminantPPM()));
        }

        return rootView;
    }
}
