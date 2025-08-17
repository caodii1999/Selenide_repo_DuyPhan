package dataprovider;

import model.User;
import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "userData")
    public Object[][] userData() {
        String email = User.defaultUser().getEmail();
        return new Object[][]{{
                User.builder()
                        .firstName("Duy")
                        .lastName("Phan")
                        .country("Vietnam")
                        .address("253 Hoang Van Thu")
                        .city("Ho Chi Minh")
                        .phoneNumber("1234567890")
                        .email(email)
                        .build()
        }};
    }

    @DataProvider(name = "invalidUserData")
    public Object[][] invalidUserData() {
        String email = User.defaultUser().getEmail();
        return new Object[][]{
                {User.builder()
                        .firstName(" ")
                        .lastName("Phan")
                        .country("Vietnam")
                        .address("253 Hoang Van Thu")
                        .city("Ho Chi Minh")
                        .phoneNumber("1234567890")
                        .email(email)
                        .build()},
                {User.builder()
                        .firstName("Duy")
                        .lastName(" ")
                        .country("Vietnam")
                        .address("253 Hoang Van Thu")
                        .city("Ho Chi Minh")
                        .phoneNumber("1234567890")
                        .email(email)
                        .build()},
                {User.builder()
                        .firstName("Duy")
                        .lastName("Phan")
                        .country("Vietnam")
                        .address(" ")
                        .city("Ho Chi Minh")
                        .phoneNumber("1234567890")
                        .email(email)
                        .build()},
                {User.builder()
                        .firstName("Duy")
                        .lastName("Phan")
                        .country("Vietnam")
                        .address("253 Hoang Van Thu")
                        .city(" ")
                        .phoneNumber("1234567890")
                        .email(email)
                        .build()},
                {User.builder()
                        .firstName("Duy")
                        .lastName("Phan")
                        .country("Vietnam")
                        .address("253 Hoang Van Thu")
                        .city("Ho Chi Minh")
                        .phoneNumber(" ")
                        .email(email)
                        .build()},
                {User.builder()
                        .firstName("Duy")
                        .lastName("Phan")
                        .country("Vietnam")
                        .address("253 Hoang Van Thu")
                        .city("Ho Chi Minh")
                        .phoneNumber("1234567890")
                        .email(" ")
                        .build()}
        };
    }
}

