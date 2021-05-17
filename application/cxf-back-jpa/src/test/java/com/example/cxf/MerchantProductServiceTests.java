package com.example.cxf;

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

import static io.restassured.RestAssured.given;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MerchantProductServiceTests {

    @LocalServerPort
    private int port;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void associate_merchant_product_OK(){
        // Create Merchant
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

        // Create Product
        request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/createProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/productService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));

        // Create Product
        request = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductRequest.xml");
        response = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductResponse.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantProductService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void associate_merchant_not_found_product_KO(){

        // Create Product
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

        // Create Product
        request = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductRequest.xml");
        response = resourceLoader.getResource("classpath:merchantProductService/merchantResponseNotFoundError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantProductService")
                .then()
                .assertThat()
                .body(equalTo(asString(response)));
    }

    @Test
    public void associate_merchant_product_not_found_KO(){

        // Create Merchant
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

        // Create Product
        request = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductRequest.xml");
        response = resourceLoader.getResource("classpath:merchantProductService/productResponseNotFoundError.xml");
        given()
                .contentType("text/xml;charset=UTF-8")
                .body(asString(request))
                .when()
                .post("http://localhost:" + port +"/services/merchantProductService")
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
