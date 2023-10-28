package com.github.gustavoflor.sga.repository;

import com.github.gustavoflor.sga.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByCpf(String cpf);

    void deleteByCpf(String cpf);

}
