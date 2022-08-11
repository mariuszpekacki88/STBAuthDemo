import Jsonplaceholder.Post;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderSerializacjaTest {
    @Test
    public void CreatePost(){

        // POJO     pleyn od java object
        // serjalizacja - to zamiana pojo na jsona (musza być getery i setery) plus robimy to co poniżej czyli przekazujemy tekst do seterów
        Post post = new Post();
        post.setUserId(1);
        post.setTitle("this is title");
        post.setBody("this is body");

        // POJO -> Json
        Response response = given()
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString("title")).isEqualTo(post.getTitle());
        Assertions.assertThat(json.getString("body")).isEqualTo(post.getBody());

    }

    @Test    //deserializacja zamiana  jsona na obiekt ze zwykłej javovej klasy
    public void getdeserializacja() {

        Post post = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .as(Post.class);

        Assertions.assertThat(post.getTitle()).isEqualTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
    }

}
