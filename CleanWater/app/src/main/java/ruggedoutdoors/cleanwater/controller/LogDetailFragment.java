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
 * This fragment is either contained in a {@link LogListActivity}
 * in two-pane mode (on tablets) or a {@link LogDetailActivity}
 * on handsets.
 */
public class LogDetailFragment extends Fragment {

    private TextView mUsernameTextView, mUserTypeTextView, mActionTextView, mOutcomeTextView, mErrorTypeTextView;

    /**
     * The fragment arguments representing the  ID's that this fragment
     * represents.  Used to pass keys into other activities through Bundle/Intent
     */
    public static final String ARG_LOG_ID = "log_id";

    private Model model = new Model();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LogDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid report passed to us
        if (getArguments().containsKey(ARG_LOG_ID)) {
            model.open();
            model.setActiveLog(getArguments().getInt(ARG_LOG_ID));
            model.close();
            //Log.d("CourseDetailFragment", "Passing over course: " + mCourse);
            //Log.d("CourseDetailFragment", "Got students: " + mCourse.getStudents().size());

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(model.getLogNumber() + "");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.log_detail, container, false);

        if (model.hasActiveLog()) {
            mUsernameTextView = (TextView) rootView.findViewById(R.id.viewLog_usernameLabel);
            mUsernameTextView.setText(model.getActiveUsername());

            mUserTypeTextView = (TextView) rootView.findViewById(R.id.viewLog_typeLabel);
            mUserTypeTextView.setText(model.getActiveUserType());

            mActionTextView = (TextView) rootView.findViewById(R.id.viewLog_actionLabel);
            mActionTextView.setText(model.getActiveAction());

            mOutcomeTextView = (TextView) rootView.findViewById(R.id.viewLog_outcomeLabel);
            mOutcomeTextView.setText(model.getActiveOutcome());

            mErrorTypeTextView = (TextView) rootView.findViewById(R.id.viewLog_errorLabel);
            mErrorTypeTextView.setText(model.getActiveErrorType());
        }

        return rootView;
    }
}