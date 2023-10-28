package com.github.gustavoflor.sga.config;

import com.github.gustavoflor.sga.mapping.GrpcService;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GrpcConfig {

    private final ApplicationContext applicationContext;
    private final GrpcConfigProperties grpcConfigProperties;

    @Bean
    public List<ServerServiceDefinition> serverServiceDefinitions() {
        return applicationContext.getBeansWithAnnotation(GrpcService.class)
            .values()
            .stream()
            .filter(BindableService.class::isInstance)
            .map(it -> (BindableService) it)
            .map(BindableService::bindService)
            .toList();
    }

    @Bean
    public Server server(final List<ServerServiceDefinition> services) {
        return ServerBuilder.forPort(grpcConfigProperties.getPort())
            .addServices(services)
            .build();
    }

}
