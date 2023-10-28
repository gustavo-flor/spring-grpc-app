package com.github.gustavoflor.sga.runner;

import io.grpc.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class GrpcRunner implements ApplicationRunner {

    private final Server server;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        start();
        addShutdownHook();
        awaitTermination();
    }

    private void start() throws IOException {
        server.start();
        log.info("Started gRPC server at port {}", server.getPort());
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }

    private void awaitTermination() throws InterruptedException {
        server.awaitTermination();
    }

}
