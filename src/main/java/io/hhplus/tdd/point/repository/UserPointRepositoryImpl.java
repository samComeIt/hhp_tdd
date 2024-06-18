package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Repository;

@Repository
public class UserPointRepositoryImpl implements UserPointRepository{

    private final UserPointTable userPointTable;

    public UserPointRepositoryImpl(UserPointTable userPointTable)
    {
        this.userPointTable = userPointTable;
    }

    @Override
    public UserPoint findPointById(long id)
    {
        return userPointTable.selectById(id);
    }

    @Override
    public UserPoint save(long id, long amount)
    {
        return userPointTable.insertOrUpdate(id, amount);
    }

    @Override
    public boolean notLessOrEqualToZero(long amount)
    {
        if (amount <= 0) return false;

        return true;
    }


}
