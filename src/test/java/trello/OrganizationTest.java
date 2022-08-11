package trello;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
public class OrganizationTest {

    protected final static String KEY = "022025b4b327c076fa95079f685e018d";
    protected final static String TOKEN = "105026fec0fb815488115872acdcb02b00b430e6da013491a6f7d27ce23f7054";

    private static Stream<Arguments> createOrganizationData(){

        return Stream.of(
                Arguments.of("This is display name", "this is name", "This is description", "https://akademiaqa.pl/szk/", 200),
                Arguments.of("This is display name", "aa", "This is description", "http://akademiaqa.pl/szk/", 400),
                Arguments.of("This is display name", "THIS IS NAME", "This is description", "http://akademiaqa.pl/szk/", 400),
                Arguments.of("This is display name", "*this is name$", "This is description", "http://akademiaqa.pl/szk/", 400),
                Arguments.of("This is display name", "This is name", "This is description", "xttps://akademiaqa.pl/szk/", 400),
                Arguments.of("This is display name", "This is name", "This is description", "1://akademiaqa.pl/szk/", 400));
    }
    @DisplayName("Create organization with valid data")
    @ParameterizedTest(name = "DisplayName: {0}, name: {1}, desc: {2}, webside: {3}, code {4}")
    @MethodSource("createOrganizationData")
    public void createOrganizationWithInvalidData(String displayName, String name, String desc, String webside, Integer code){

        Organization organization = new Organization();
        organization.setDisplayName(displayName);
        organization.setName(name);
        organization.setDesc(desc);
        organization.setWebside(webside);

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .queryParam("displayName", organization.getDisplayName())
                .queryParam("name", organization.getName())
                .queryParam("desc", organization.getDesc())
                .queryParam("website", organization.getWebside())
                .when()
                .post("https://api.trello.com/1/organizations")
                .then()
                .statusCode(code)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        final String organizationId = json.getString("id");

        Assertions.assertThat(json.getString("displayName")).isEqualTo(displayName);

        given()
                .contentType(ContentType.JSON)
                .queryParam("key", KEY)
                .queryParam("token", TOKEN)
                .when()
                .delete("https://api.trello.com/1/organizations" + "/" + organizationId)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}