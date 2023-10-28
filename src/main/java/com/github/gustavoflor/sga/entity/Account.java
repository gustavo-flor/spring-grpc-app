package com.github.gustavoflor.sga.entity;

import com.github.gustavoflor.sga.protobuf.AccountPayload;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "accounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private String id;

    @Indexed(unique = true)
    private String cpf;

    private BigDecimal balance;

    public static Account of(final String cpf) {
        return Account.builder()
            .cpf(cpf)
            .balance(BigDecimal.ZERO)
            .build();
    }

    public AccountPayload toProtobuf() {
        return AccountPayload.newBuilder()
            .setId(id)
            .setCpf(cpf)
            .setBalance(balance.doubleValue())
            .build();
    }

}
