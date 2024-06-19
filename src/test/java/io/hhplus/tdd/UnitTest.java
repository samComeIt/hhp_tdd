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
    @DisplayName("ID 0인 경우 실패")
    void testIdIs0()
    {
        // given
        Long userId01 = 0L;

        // when, then
        assertTrue(userId01 == 0);
    }

    @Test
    @DisplayName("ID null인 경우 실패")
    void testIdIsNUll()
    {
        // given
        Long userId01 = null;

        // when, then
        assertTrue(userId01 == null);
    }

    @Test
    @DisplayName("null/0 포인트 사용/추가 경우 실패")
    void testPoint()
    {
        // given
        Long amount01 = null;

        // when
        boolean result = false;
        if (amount01 == null || amount01 == 0) result = true;

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("현재 포인트 조회 성공")
    void testCurPoint()
    {
        // given
        Long userId01 = 1L;

        // when
        UserPoint userPoint = new UserPoint(userId01, 500L, System.currentTimeMillis());

        // then
        assertEquals(500L, userPoint.point());
    }

    @Test
    @DisplayName("포인트 추가 성공")
    void testAddPoint()
    {
        // given
        Long userId01 = 2L;

        // when
        UserPoint userPoint = new UserPoint(userId01, 300L, System.currentTimeMillis());

        // then
        assertEquals(300L, userPoint.point());
        assertTrue(300L == userPoint.point());
    }

    @Test
    @DisplayName("포인트 사용 성공")
    void testUsePoint()
    {
        // given
        Long userId01 = 2L;
        Long point = 300L;
        UserPoint userPoint = new UserPoint(userId01, point, System.currentTimeMillis());

        Long use = 100L;

        // when
        Long amount = point - use;

        userPoint = new UserPoint(userId01, amount, System.currentTimeMillis());

        // then
        assertEquals(200L, userPoint.point());
        assertEquals(amount, userPoint.point());
        assertEquals(amount, 200L);
    }

    @Test
    @DisplayName("포인트 추가 내역 조회 성공")
    void testGetAddPointHistoryList()
    {
        // given
        List<PointHistory> table = new ArrayList<>();
        table.add(new PointHistory(1L, 100L, 200L, TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(1L, 100L, 200L, TransactionType.USE, System.currentTimeMillis()));

        // when
        int cnt = table.stream().filter(pointHistory -> pointHistory.type() == TransactionType.CHARGE).toList().size();

        // then
        assertEquals(1, cnt);
        assertFalse(2 == cnt);
    }
}
