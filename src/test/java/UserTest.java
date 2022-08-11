import Jsonplaceholder.Addres;
import Jsonplaceholder.Company;
import Jsonplaceholder.Geo;
import Jsonplaceholder.User;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTest {

    @Test
    public void createNewUser ()
    {
        User user = new User();
        user.setName("Mariusz Pekacki");
        user.setUsername("mariuszpekacki");
        user.setEmail("mariusz@wp.pl");
        user.setPhone("34535345");
        user.setWebsite("https://asdasdasdad.pl");

        Geo geo = new Geo();
        geo.setLat("9822222");
        geo.setLng("234234234");

        Addres addres = new Addres();
        addres.setStreet("celulozy");
        addres.setSuite("sdf 343434");
        addres.setCity("Warsaw");
        addres.setZipcode("98-998");
        addres.setGeo(geo);

        user.setAddress(addres);

        Company company = new Company();
        company.setName("LuxMed");
        company.setCatchPhrase("werewr werwerwer werwerwer");
        company.setBs("sssssssssss");

        user.setCompany(company);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertThat(json.getString("name")).isEqualTo(user.getName());

    }
}

