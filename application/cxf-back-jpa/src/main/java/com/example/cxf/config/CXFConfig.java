package com.example.cxf.config;

import com.example.cxf.service.MerchantService;
import com.example.cxf.service.impl.MerchantProductServiceImpl;
import com.example.cxf.service.impl.MerchantServiceImpl;
import com.example.cxf.service.impl.ProductServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CXFConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> disServlet()  {
        return new ServletRegistrationBean<>(new CXFServlet(), "/services/*");
    }

    @Bean(name= Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint merchantEndpoint(MerchantServiceImpl merchantService) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), merchantService);
        endpoint.getFeatures().add(new LoggingFeature());
        endpoint.publish("/merchantService");
        return endpoint;
    }

    @Bean
    public Endpoint productEndpoint(ProductServiceImpl productService) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), productService);
        endpoint.getFeatures().add(new LoggingFeature());
        endpoint.publish("/productService");
        return endpoint;
    }

    @Bean
    public Endpoint merchantProductEndpoint(MerchantProductServiceImpl merchantProductService) {
        EndpointImpl endpoint = new EndpointImpl(springBus(), merchantProductService);
        endpoint.getFeatures().add(new LoggingFeature());
        endpoint.publish("/merchantProductService");
        return endpoint;
    }
}