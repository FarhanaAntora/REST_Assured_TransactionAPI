import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class TransactionAPI {
    Properties props = new Properties();
    FileInputStream file = new FileInputStream("./src/test/resources/config.properties");


    public TransactionAPI() throws FileNotFoundException {
    }

    public void userLogin() throws IOException, ConfigurationException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"email\":\"salmansrabon@gmail.com\",\n" +
                                "    \"password\":\"1234\"\n" +
                                "}")
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        String token = resObj.get("token");
        Utils.setEnvVariable("token", token);
        System.out.println(token);
    }

    public void invalidEmail() throws ConfigurationException, IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"email\":\"urmi@test.com\",\n" +
                                "    \"password\":\"1234\"\n" +
                                "}")
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(404).extract().response();
    }

    public void invalidPassword() throws ConfigurationException, IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"email\":\"salmansrabon@gmail.com\",\n" +
                                "    \"password\":\"78457\"\n" +
                                "}")
                        .when()
                        .post("/user/login")
                        .then()
                        .assertThat().statusCode(401).extract().response();
    }

    public void showList() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/user/list")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        int id = jsonObj.get("users[0].id");
        Assert.assertEquals(1, id);
    }

    public void showByRole() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/user/search/Agent")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        String name = jsonObj.get("users[4].name");
        Assert.assertEquals("Marium", name);
    }

    public void searchUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/user/search?id=52")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        String name = jsonObj.get("user.name");
        Assert.assertEquals("Mrs. Alan Schmidt", name);
    }

    public Integer ID;
    public String name;
    public String email;
    public String nid;
    public String phone;

    public void generateCustomer() throws IOException, ConfigurationException {
        props.load(file);
        RestAssured.baseURI = "https://randomuser.me";
        Response response =
                given()
                        .contentType("application/json")
                        .when()
                        .get("/api")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        ID = (int) Math.floor(Math.random() * (9999999 - 1000000) + 1);
        phone = resObj.get("results[0].cell");
        name = "Name " + resObj.get("results[0].name.first");
        email = resObj.get("results[0].email");
        nid = resObj.get("results[0].login.uuid");
        Utils.setEnvVariable("name", name);
        Utils.setEnvVariable("email", email);
        Utils.setEnvVariable("nid", nid);
        Utils.setEnvVariable("phone", phone);
        System.out.println(response.asString());
    }


    public void updateCustomer() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"name\": \"June Murphy\",\n" +
                                "    \"email\": \"june51@yahoo.com\",\n" +
                                "    \"password\": \"7845\",\n" +
                                "    \"phone\": \"01526809412\",\n" +
                                "    \"nid\": \"1a189c8a-b3e4-4534-90fc-b3ae76337a9d\",\n" +
                                "    \"role\": \"Customer\"\n" +
                                "}")
                        .when()
                        .put("/user/update/9")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }

    public void showBalance() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/balance/0191998988")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }

    public void deposit() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01753851797\",\n" +
                                "    \"to_account\":\"01527019378\",\n" +
                                "    \"amount\":100\n" +
                                "}")
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        System.out.println(response.asString());
    }

    public void depositInsufficientBalance() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01978591133\",\n" +
                                "    \"to_account\":\"01527019378\",\n" +
                                "    \"amount\":500\n" +
                                "}")
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        String m = jsonObj.get("message");
        Assert.assertEquals("Insufficient balance", m);
    }

    public void checkBalance() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/balance/01329947543")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }

    public void sendMoney() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01522828745\",\n" +
                                "    \"to_account\":\"01523230720\",\n" +
                                "    \"amount\":100\n" +
                                "}")
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(201).extract().response();
    }
    public void sendInsufficientBalance() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01524167869\",\n" +
                                "    \"to_account\":\"01686606909\",\n" +
                                "    \"amount\":200\n" +
                                "}")
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(208).extract().response();
    }

    public void customerBalance() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/balance/01523230720")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }

    public void withdraw() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01526809412\",\n" +
                                "    \"to_account\":\"0191955566\",\n" +
                                "    \"amount\":100\n" +
                                "}")
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(201).extract().response();
    }

    public void withdrawInsufficientMoney() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01329259149\",\n" +
                                "    \"to_account\":\"01974128039\",\n" +
                                "    \"amount\":100\n" +
                                "}")
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(208).extract().response();
    }

    public void displayTransaction() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/statement/01322887401")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }

    public void showTransaction() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/search/TXN55141")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }

    public void transactionList() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/list")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
    }
}


