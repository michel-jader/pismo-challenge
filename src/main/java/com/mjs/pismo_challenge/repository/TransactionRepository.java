package com.mjs.pismo_challenge.repository;

import com.mjs.pismo_challenge.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value =
            " select t from `Transaction` t " +
            " where " +
            " t.account.accountId = :accountId and" +
            " t.balance < 0 and " +
            " t.operationType.operationTypeId in ( :operationTypeIds ) " +
            " order by  t.transactionId asc ")
    List<Transaction> findAllToDischarge(
            @Param("accountId") Long accountId,
            @Param("operationTypeIds") List<Long> operationTypeIds);


}
