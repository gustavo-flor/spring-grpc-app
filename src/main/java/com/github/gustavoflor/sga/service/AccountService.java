package com.github.gustavoflor.sga.service;

import com.github.gustavoflor.sga.entity.Account;
import com.github.gustavoflor.sga.mapping.GrpcService;
import com.github.gustavoflor.sga.protobuf.*;
import com.github.gustavoflor.sga.protobuf.Void;
import com.github.gustavoflor.sga.repository.AccountRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

import static java.text.MessageFormat.format;

@GrpcService
@RequiredArgsConstructor
public class AccountService extends AccountServiceGrpc.AccountServiceImplBase {

    private final AccountRepository accountRepository;

    @Override
    public void create(final CreateAccountRequest request, final StreamObserver<AccountPayload> responseObserver) {
        final var cpf = request.getCpf();
        final var account = Account.of(cpf);
        accountRepository.save(account);
        responseObserver.onNext(account.toProtobuf());
        responseObserver.onCompleted();
    }

    @Override
    public void findByCpf(final FindByCpfRequest request, final StreamObserver<AccountPayload> responseObserver) {
        final var cpf = request.getCpf();
        accountRepository.findByCpf(cpf).ifPresentOrElse(account -> {
            responseObserver.onNext(account.toProtobuf());
            responseObserver.onCompleted();
        }, () -> {
            final var exception = Status.NOT_FOUND
                .withDescription(format("Account not found with CPF: {0}", cpf))
                .asRuntimeException();
            responseObserver.onError(exception);
        });
    }

    @Override
    public void deleteByCpf(final DeleteByCpfRequest request, final StreamObserver<Void> responseObserver) {
        final var cpf = request.getCpf();
        accountRepository.deleteByCpf(cpf);
        responseObserver.onNext(Void.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
