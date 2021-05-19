package com.example.spring.boot;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.TimeZone;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MerchantProductServiceTests {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ResourceLoader resourceLoader;

    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("xsds/merchantProduct.xsd");

    @Before
    public void init() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
    }

    @AfterEach
    public void after() {
        TimeZone.setDefault(TimeZone.getDefault());
    }

    @Test
    public void associate_merchant_product_OK() throws IOException {
        // Create Merchant
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        // Create Product
        request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/createProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        // Create Product
        request = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductRequest.xml");
        response = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductResponse.xml");
        
        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void associate_merchant_not_found_product_KO() throws IOException {

        // Create Product
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        // Create Product
        request = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductRequest.xml");
        response = resourceLoader.getResource("classpath:merchantProductService/merchantResponseNotFoundError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
        //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void associate_merchant_product_not_found_KO() throws IOException {

        // Create Merchant
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        // Create Product
        request = resourceLoader.getResource("classpath:merchantProductService/associateMerchantProductRequest.xml");
        response = resourceLoader.getResource("classpath:merchantProductService/productResponseNotFoundError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
        //.andExpect(validPayload(xsdSchema));
    }

    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
