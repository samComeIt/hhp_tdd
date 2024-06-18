package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.UserPoint;

public interface UserPointRepository {
    UserPoint findPointById(long id);
    UserPoint save(long id, long amount);

    boolean notLessOrEqualToZero(long amount);

}
