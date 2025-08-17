package model;

import config.EmailConfig;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String firstName;
    private String lastName;
    private String companyName;
    private String country;
    private String address;
    private int zipCode;
    private String city;
    private String email;
    private String password;
    private String phoneNumber;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User defaultUser() {
        String email = EmailConfig.getEmailAddress();
        return User.builder()
                .email(email)
                .build();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFullAddress() {
        return address + " " + city + " " + country;
    }
}
