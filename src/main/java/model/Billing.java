package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Billing {

  private String fullName;
  private String address;
  private String city;
  private String country;
  private String phoneNumber;
  private String email;

}
