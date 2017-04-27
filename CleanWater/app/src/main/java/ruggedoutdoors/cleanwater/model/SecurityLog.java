package ruggedoutdoors.cleanwater.model;

import java.security.InvalidParameterException;

/**
 * Created by Austin Dunn on 4/24/2017.
 */

public class SecurityLog implements Comparable{

    private int logNumber;
    private static int logsTotal = 0;
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
        this.logNumber = logsTotal++;
        this.username = username;
        this.userType = userType;
        this.action = action;
        this.outcome = outcome;
        this.errorType = errorType;
    }

    /* Compares the log to another object
     *
     * @param o            object to be compared
     * @return boolean     true if reports match, false otherwise
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof SecurityLog)) {
            throw new InvalidParameterException("Can't pass in non reports.");
        }

        SecurityLog that = (SecurityLog) o;
        if (this.logNumber < that.logNumber) {
            return -1;
        } else if (this.logNumber > that.logNumber) {
            return 1;
        } else {
            return 0;
        }
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

    /**
     * Returns the total number of logs in the system
     *
     * @return logsTotal    total number of logs in the system
     */
    public int getLogsTotal() {
        return logsTotal;
    }

    /**
     * Returns the log number
     *
     * @return logNumber     the log number
     */
    public int getLogNumber() {
        return logNumber;
    }

}
