package ruggedoutdoors.cleanwater.model;

/**
 * Created by Austin Dunn on 4/24/2017.
 */

public class SecurityLog {

    private String username;
    private String userType;
    private String action;
    private String outcome;
    private String errorType;

    /**
     * Constructs new Log object
     * @param username username
     * @param userType user type
     * @param action action
     * @param outcome outcome
     * @param errorType error type
     */
    public SecurityLog(String username, String userType, String action, String outcome, String errorType) {
        this.username = username;
        this.userType = userType;
        this.action = action;
        this.outcome = outcome;
        this.errorType = errorType;
    }

    /**
     * gets the username
     * @return username
     */
    public String getUsername() { return username; }

    /**
     * gets the user type
     * @return user type
     */
    public String getUserType() { return userType; }

    /**
     * gets the action
     * @return action
     */
    public String getAction() { return action; }

    /**
     * gets the outcome
     * @return outcome
     */
    public String getOutcome() { return outcome; }

    /**
     * gets the error type
     * @return error type
     */
    public String getErrorType() { return errorType; }

}
