package dataprovider;

import model.User;
import org.testng.annotations.DataProvider;

public class UserDataProvider {

  @DataProvider(name = "userData")
  public Object[][] userData() {
    return new Object[][]{{
        User.builder()
            .firstName("Duy")
            .lastName("Phan")
            .country("Vietnam")
            .address("253 Hoang Van Thu")
            .city("Ho Chi Minh")
            .phoneNumber("1234567890")
            .build()
    }};
  }
}

