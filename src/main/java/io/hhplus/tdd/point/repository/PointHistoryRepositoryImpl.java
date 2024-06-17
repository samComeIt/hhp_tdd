package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointHistoryRepositoryImpl implements PointHistoryRepository {

    private final PointHistoryTable pointHistoryTable;

    public PointHistoryRepositoryImpl(PointHistoryTable pointHistoryTable)
    {
        this.pointHistoryTable = pointHistoryTable;
    }

    @Override
    public PointHistory save(Long id, Long amount, TransactionType transactionType, Long updateMillis)
    {
        return pointHistoryTable.insert(id, amount, transactionType, updateMillis);
    }

    @Override
    public List<PointHistory> findAllHistoryById(Long id)
    {
        return pointHistoryTable.selectAllByUserId(id);
    }
}


