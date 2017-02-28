package ruggedoutdoors.cleanwater;

import android.location.Address;

import java.util.Date;

/**
 * Created by karanachtani on 2/27/17.
 */

public class User {

    private String firstName, lastName, username, password, email;
    private Date birthday;
    private Address address;
    private UserType userType;

    public User (String fn, String ln, String un, String pass, String email, Date bday, Address address, UserType type) {
        firstName = fn;
        lastName = ln;
        username = un;
        password = pass;
        this.email = email;
        birthday = bday;
        this.address = address;
        userType = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
