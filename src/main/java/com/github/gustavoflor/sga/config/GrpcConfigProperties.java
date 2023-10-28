package com.github.gustavoflor.sga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "grpc.server")
@Getter
@Setter
public class GrpcConfigProperties {

    private int port = 9090;

}
