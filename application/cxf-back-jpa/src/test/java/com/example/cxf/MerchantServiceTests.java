package com.example.cxf;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MerchantServiceTests {

    @LocalServerPort
    private int port;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
    }

    @AfterEach
    public void after() {
        TimeZone.setDefault(TimeZone.getDefault());
    }

    @Test
    public void createMerchant_OK(){
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void createMerchant_KO(){
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        response = resourceLoader.getResource("classpath:merchant/createMerchantResponseError.xml");

        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void updateMerchant_OK(){
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        request = resourceLoader.getResource("classpath:merchant/updateMerchantRequest.xml");
        response = resourceLoader.getResource("classpath:merchant/updateMerchantResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void updateMerchant_not_found_KO(){
        Resource request = resourceLoader.getResource("classpath:merchant/updateMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/updateMerchantResponseNotFoundError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void updateMerchant_mandatory_id_KO(){
        Resource request = resourceLoader.getResource("classpath:merchant/updateMerchantRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/updateMerchantResponseIdMandatoryError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void deleteMerchant_OK(){
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        request = resourceLoader.getResource("classpath:merchant/deleteMerchantRequest.xml");
        response = resourceLoader.getResource("classpath:merchant/deleteMerchantResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void deleteMerchant_not_found_KO(){
        Resource request = resourceLoader.getResource("classpath:merchant/deleteMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/deleteMerchantResponseNotFoundError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void deleteMerchant_mandatory_id_KO(){
        Resource request = resourceLoader.getResource("classpath:merchant/deleteMerchantRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/deleteMerchantResponseIdMandatoryError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
