package com.example.spring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
@ComponentScan(basePackages = "com.example")
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean(name = "merchants")
    public DefaultWsdl11Definition merchantsWsdl11Definition(XsdSchema merchantsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("MerchantsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/gs-producing-web-service");
        wsdl11Definition.setSchema(merchantsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema merchantsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/merchants.xsd"));
    }

    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/gs-producing-web-service");
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/products.xsd"));
    }

    @Bean(name = "merchantProducts")
    public DefaultWsdl11Definition merchantProductsWsdl11Definition(XsdSchema merchantProductsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("merchantProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/gs-producing-web-service");
        wsdl11Definition.setSchema(merchantProductsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema merchantProductsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsds/merchantProduct.xsd"));
    }
}
