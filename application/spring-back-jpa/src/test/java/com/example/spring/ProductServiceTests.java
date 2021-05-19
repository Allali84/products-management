package com.example.spring;

import com.example.spring.config.JpaConfig;
import com.example.spring.config.WebServiceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.TimeZone;

import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = { JpaConfig.class, WebServiceConfig.class},
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductServiceTests {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ResourceLoader resourceLoader;

    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("xsds/products.xsd");

    @BeforeEach
    public void init(){
        mockClient = MockWebServiceClient.createClient(applicationContext);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
    }

    @AfterEach
    public void after() {
        TimeZone.setDefault(TimeZone.getDefault());
    }

    @Test
    public void createProduct_OK() throws IOException  {
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void createProduct_KO() throws IOException {
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/createProductResponseError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
                //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void updateProduct_OK() throws IOException {
        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        request = resourceLoader.getResource("classpath:product/updateProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/updateProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void updateProduct_not_found_KO() throws IOException {

        Resource request = resourceLoader.getResource("classpath:product/updateProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/updateProductResponseNotFoundError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
                //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void updateProduct_mandatory_id_KO() throws IOException {

        Resource request = resourceLoader.getResource("classpath:product/updateProductRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:product/updateProductResponseIdMandatoryError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
                //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void deleteProduct_OK() throws IOException {

        Resource request = resourceLoader.getResource("classpath:product/createProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/createProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        request = resourceLoader.getResource("classpath:product/deleteProductRequest.xml");
        response = resourceLoader.getResource("classpath:product/deleteProductResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void deleteProduct_not_found_KO() throws IOException {

        Resource request = resourceLoader.getResource("classpath:product/deleteProductRequest.xml");
        Resource response = resourceLoader.getResource("classpath:product/deleteProductResponseNotFoundError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
                //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void deleteProduct_mandatory_id_KO() throws IOException {

        Resource request = resourceLoader.getResource("classpath:product/deleteProductRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:product/deleteProductResponseIdMandatoryError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
               // .andExpect(validPayload(xsdSchema));
    }


    public static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
