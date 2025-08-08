package dataprovider;

import config.EnvConfig;
import factory.UserFactory;
import model.User;
import org.testng.annotations.DataProvider;

public class UserDataProvider {

  EnvConfig config = new EnvConfig();
  User defaultUser = UserFactory.createDefaultUser(config);


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
            .email(defaultUser.getEmail())
            .build()
    }};
  }
}

