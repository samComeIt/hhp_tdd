package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;

import java.util.List;

public interface PointHistoryRepository {
    List<PointHistory> findAllHistoryById(long id);

    PointHistory save(long id, long amount, TransactionType transactionType, long updateMillis);
}
