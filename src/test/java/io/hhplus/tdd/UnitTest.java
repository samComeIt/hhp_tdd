package io.hhplus.tdd;

//import static org.assertj.core.api.Assertions.*;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class UnitTest {
    private UserPoint userPoint;

    @Test
    @DisplayName("ID 0인 경우")
    void testId()
    {
        Long userId = 0L;
        assertEquals(0, userId);
    }

    @Test
    @DisplayName("0포인트 사용/추가 경우")
    void testPoint()
    {
        boolean result = false;

        Long amount01 = 0L;
        if (amount01 == 0) result = true;

        assertTrue(result);
        assertFalse(result);
    }

    @Test
    @DisplayName("현재 포인트 조회")
    void testCurPoint()
    {
        Long id = 1L;
        UserPoint userPoint = new UserPoint(id, 500L, System.currentTimeMillis());

        Long amount = 500L;
        userPoint.add(id, amount);

        assertEquals(500L, userPoint.point());
    }

    @Test
    @DisplayName("포인트 추가")
    void testAddPoint()
    {
        Long id = 2L;
        UserPoint userPoint = new UserPoint(id, 300L, System.currentTimeMillis());

        userPoint.add(id, 500L);
        assertEquals(500L, userPoint.point());
        assertFalse(500L == userPoint.point());
    }

    @Test
    @DisplayName("포인트 사용")
    void testUsePoint()
    {
        Long id = 2L;
        UserPoint userPoint = new UserPoint(id, 300L, System.currentTimeMillis());
    }

    @Test
    @DisplayName("포인트 추가 내역 조회")
    void testGetAddPointHistoryList()
    {
        List<PointHistory> table = new ArrayList<>();
        table.add(new PointHistory(1L, 100L, 200L, TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(1L, 100L, 200L, TransactionType.USE, System.currentTimeMillis()));

        int cnt = table.stream().filter(pointHistory -> pointHistory.type() == TransactionType.CHARGE).toList().size();

        assertEquals(1, cnt);
        assertFalse(2 == cnt);
    }
}
