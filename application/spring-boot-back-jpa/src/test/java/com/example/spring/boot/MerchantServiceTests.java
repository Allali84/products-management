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
public class MerchantServiceTests {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ResourceLoader resourceLoader;

    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("xsds/merchants.xsd");

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
    public void createMerchant_OK() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");
        
        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void createMerchant_KO() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        response = resourceLoader.getResource("classpath:merchant/createMerchantResponseError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
        //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void updateMerchant_OK() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        request = resourceLoader.getResource("classpath:merchant/updateMerchantRequest.xml");
        response = resourceLoader.getResource("classpath:merchant/updateMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void updateMerchant_not_found_KO() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/updateMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/updateMerchantResponseNotFoundError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
        //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void updateMerchant_mandatory_id_KO() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/updateMerchantRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/updateMerchantResponseIdMandatoryError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
        //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void deleteMerchant_OK() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/createMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/createMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));

        request = resourceLoader.getResource("classpath:merchant/deleteMerchantRequest.xml");
        response = resourceLoader.getResource("classpath:merchant/deleteMerchantResponse.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(noFault())
                .andExpect(payload(new StringSource(asString(response))))
                .andExpect(validPayload(xsdSchema));
    }

    @Test
    public void deleteMerchant_not_found_KO() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/deleteMerchantRequest.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/deleteMerchantResponseNotFoundError.xml");

        mockClient
                .sendRequest(withPayload(new StringSource(asString(request))))
                .andExpect(serverOrReceiverFault())
                .andExpect(payload(new StringSource(asString(response))));
        //.andExpect(validPayload(xsdSchema));
    }

    @Test
    public void deleteMerchant_mandatory_id_KO() throws IOException {
        Resource request = resourceLoader.getResource("classpath:merchant/deleteMerchantRequestWithoutId.xml");
        Resource response = resourceLoader.getResource("classpath:merchant/deleteMerchantResponseIdMandatoryError.xml");

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
