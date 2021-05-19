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
public class ProductServiceTests {

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
    public void createProduct_OK(){
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void createProduct_KO(){
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/createProductResponseError.xml");

        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void updateProduct_OK(){
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        request = resourceLoader.getResource("classpath:product/updateProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/updateProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void updateProduct_not_found_KO(){
        Resource request = resourceLoader.getResource("classpath:product/updateProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/updateProductResponseNotFoundError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void updateProduct_mandatory_id_KO(){
        Resource request = resourceLoader.getResource("classpath:product/updateProductRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:product/updateProductResponseIdMandatoryError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void deleteProduct_OK(){
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        request = resourceLoader.getResource("classpath:product/deleteProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/deleteProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void deleteProduct_not_found_KO(){
        Resource request = resourceLoader.getResource("classpath:product/deleteProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/deleteProductResponseNotFoundError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void deleteProduct_mandatory_id_KO(){
        Resource request = resourceLoader.getResource("classpath:product/deleteProductRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:product/deleteProductResponseIdMandatoryError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
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
