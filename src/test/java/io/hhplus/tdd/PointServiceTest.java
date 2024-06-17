package io.hhplus.tdd;

import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.dto.PointHistoryDto;
import io.hhplus.tdd.point.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PointServiceTest {

    @Autowired
    private PointService pointService;

    /**
     * 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다
     * 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다.
     */

    @Test
    @DisplayName("잔고가 부족하는지 확인")
    void testCurPoint()
    {
        Long id = 100L;
        UserPoint userPoint = pointService.getPoint(id);

        assertEquals(userPoint.point(), 0);
        assertFalse( userPoint.point() > 0);
    }

    @Test
    @DisplayName("히스토리 추가")
    void testAddHistory()
    {
        Long id = 100L;
        Long point = 200L;

        PointHistoryDto pointHistoryDto = new PointHistoryDto();
        pointHistoryDto.setUserId(id);
        pointHistoryDto.setAmount(point);
        pointHistoryDto.setType(TransactionType.CHARGE);
        pointHistoryDto.setUpdateMillis(System.currentTimeMillis());

        pointService.addHistory(pointHistoryDto);

        int cnt = pointService.findAllHistoryById(id).size();
        assertEquals(1, cnt);
    }

    @Test
    @DisplayName("동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리")
    void testUse() throws InterruptedException{

    }


}
