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
    public UserPoint findPointById(Long id)
    {
        return userPointTable.selectById(id);
    }

    @Override
    public UserPoint save(Long id, Long amount)
    {
        return userPointTable.insertOrUpdate(id, amount);
    }



}
