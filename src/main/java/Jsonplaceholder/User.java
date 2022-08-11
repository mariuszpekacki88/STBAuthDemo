package Jsonplaceholder;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Addres address;
    private String phone;
    private String website;
    private Company company;
}
