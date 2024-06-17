package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;

import java.util.List;

public interface PointHistoryRepository {
    List<PointHistory> findAllHistoryById(Long id);

    PointHistory save(Long id, Long amount, TransactionType transactionType, Long updateMillis);
}
