package io.hhplus.tdd.point.repository;

import io.hhplus.tdd.point.UserPoint;

public interface UserPointRepository {
    UserPoint findPointById(Long id);
    UserPoint save(Long id, Long amount);

}
