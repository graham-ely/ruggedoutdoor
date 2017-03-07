package ruggedoutdoors.cleanwater.model;

/**
 * Created by karanachtani on 2/27/17.
 */

public class User {

    private String firstName, lastName, username, password, email, phone;
    private String birthday;
    private String address;
    private UserType userType;

    /**
     * constructor
     * @param fn first name
     * @param ln last name
     * @param un username
     * @param password password
     * @param email email address
     * @param phone phone number
     * @param bday birthday
     * @param address address
     * @param type UserType of the User
     */
    public User (String fn, String ln, String un, String password, String email, String phone, String bday, String address, UserType type) {
        firstName = fn;
        lastName = ln;
        username = un;
        this.password = password;
        this.email = email;
        this.phone = phone;
        birthday = bday;
        this.address = address;
        userType = type;
    }

    /**
     * gets first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets phone number
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * sets phone number
     * @param phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * gets bday
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * sets birthday
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * gets address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * sets address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * gets UserType of user
     * @return userType of user
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * sets UserTyper of user
     * @param userType of user
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
