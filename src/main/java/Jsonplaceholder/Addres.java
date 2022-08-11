package Jsonplaceholder;

import lombok.Data;

@Data
public class Addres {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
}
