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

/**
 * A fragment representing a single Report detail screen.
 * This fragment is either contained in a {@link ReportListActivity}
 * in two-pane mode (on tablets) or a {@link ReportDetailActivity}
 * on handsets.
 */
public class ReportDetailFragment extends Fragment {

    private TextView mReporterTextView, mLocationTextView, mTimestampTextView, mConditionTextView, mTypeTextView;

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
    public ReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid report passed to us
        if (getArguments().containsKey(ARG_REPORT_ID)) {
            model.setActiveSourceReport(getArguments().getInt(ARG_REPORT_ID));
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
        View rootView = inflater.inflate(R.layout.report_detail, container, false);

        if (model.hasActiveReport()) {
            mReporterTextView = (TextView) rootView.findViewById(R.id.viewReport_reporterLabel);
            mReporterTextView.setText(model.getReporterName());

            mLocationTextView = (TextView) rootView.findViewById(R.id.viewReport_locationLabel);
            mLocationTextView.setText(Double.toString(model.getLatitude()) + ", "
                    + Double.toString(model.getLongitude()));

            mTimestampTextView = (TextView) rootView.findViewById(R.id.viewReport_timestampLabel);
            mTimestampTextView.setText(model.getDateTime());

            mConditionTextView = (TextView) rootView.findViewById(R.id.viewReport_conditionLabel);
            mConditionTextView.setText(model.getWaterCondition());

            mTypeTextView = (TextView) rootView.findViewById(R.id.viewReport_typeLabel);
            mTypeTextView.setText(model.getWaterType());
        }

        return rootView;
    }
}
