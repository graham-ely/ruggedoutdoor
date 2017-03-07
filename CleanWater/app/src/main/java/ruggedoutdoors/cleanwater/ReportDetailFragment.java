package ruggedoutdoors.cleanwater;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ruggedoutdoors.cleanwater.dummy.DummyContent;

/**
 * A fragment representing a single Report detail screen.
 * This fragment is either contained in a {@link ReportListActivity}
 * in two-pane mode (on tablets) or a {@link ReportDetailActivity}
 * on handsets.
 */
public class ReportDetailFragment extends Fragment {
    /**
     * The fragment arguments representing the  ID's that this fragment
     * represents.  Used to pass keys into other activities through Bundle/Intent
     */
    public static final String ARG_REPORT_ID = "report_id";

    /**
     * The course that this detail view is for.
     */
    private Report mReport;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid course passed to us
        if (getArguments().containsKey(ARG_REPORT_ID)) {
            mReport = Reports.getReport(Integer.parseInt(ARG_REPORT_ID));
            //Log.d("CourseDetailFragment", "Passing over course: " + mCourse);
            //Log.d("CourseDetailFragment", "Got students: " + mCourse.getStudents().size());

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mReport.getDateTime().toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mReport != null) {
            ((TextView) rootView.findViewById(R.id.report_detail)).setText(mReport.getDateTime().toString());
        }

        return rootView;
    }
}