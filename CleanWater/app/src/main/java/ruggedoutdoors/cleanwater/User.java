package ruggedoutdoors.cleanwater;

import android.location.Address;

import java.util.Date;

/**
 * Created by karanachtani on 2/27/17.
 */

public class User {

    private String firstName, lastName, username, password, email, phone;
    private Date birthday;
    private Address address;
    private UserType userType;

    public User (String fn, String ln, String un, String password, String email, String phone, Date bday, Address address, UserType type) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
