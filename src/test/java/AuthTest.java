import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthTest {

    private static final String TOKEN = "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";

    @Test
    public void basicAuth(){

    given()
            .auth()
            .preemptive()
            .basic("@gmail.com", "")
            .when()
            .get("https://api.github.com/user")
            .then()
            .statusCode(200);
    }

    @Test
    public void bearerToken(){
    given()
            .header("Authorization", "Bearer " + TOKEN)
            .when()
            .get("https://api.github.com/user")
            .then()
            .statusCode(200);
    }

    @Test
    public void oAuth2() {

        given()
                .auth()
                .oauth2(TOKEN)
                .when()
                .get("https://api.github.com/user")
                .then()
                .statusCode(200);
    }
}
