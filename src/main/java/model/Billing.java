package model;

import lombok.*;

@Getter
@Setter
@Builder
@Data
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
