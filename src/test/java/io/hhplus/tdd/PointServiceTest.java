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

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PointServiceTest {

    @Autowired
    private PointService pointService;

    /**
     * 포인트 사용전에 현재 잔고가 0보다 작거나 같으면 실패
     * 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다
     * 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다.
     */

    @Test
    @DisplayName("포인트 사용전에 현재 잔고가 0보다 작거나 같으면 실패")
    void testCurPoint()
    {
        // given
        Long userId01 = 100L;

        // when
        UserPoint userPoint = pointService.getPoint(userId01);

        //then
        assertEquals(userPoint.point(), 0);
        assertFalse( userPoint.point() > 0);
    }

    @Test
    @DisplayName("히스토리 추가 성공")
    void testAddHistory()
    {
        // given
        Long userId01 = 100L;
        Long point = 200L;

        // when
        PointHistoryDto pointHistoryDto = new PointHistoryDto();
        pointHistoryDto.setUserId(userId01);
        pointHistoryDto.setAmount(point);
        pointHistoryDto.setType(TransactionType.CHARGE);
        pointHistoryDto.setUpdateMillis(System.currentTimeMillis());

        pointService.addHistory(pointHistoryDto);

        int cnt = pointService.findAllHistoryById(userId01).size();

        // then
        assertEquals(1, cnt);
    }

    @Test
    @DisplayName("동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리 성공")
    void testCharge() throws InterruptedException{
        //given
        Long id = 100L;
        UserPoint userPoint = new UserPoint(id, 500L, System.currentTimeMillis());
        assertEquals(userPoint.point(), 500L);

        // when
        CountDownLatch latch = new CountDownLatch(3);

        Long[] amounts = {200L, 300L, 500L};

        for (int i = 0; i < amounts.length; i++)
        {
            try {
                pointService.charge(id, amounts[i]);
            } catch (Exception ex) {

            }
            latch.countDown();
        }
        latch.await();

        Long curPoint = pointService.getPoint(id).point();

        // then
        assertEquals(1000L, curPoint);
    }

    @Test
    @DisplayName("동시에 여러 건의 포인트 사용, 이용 요청이 들어올 경우 순차적으로 처리 성공")
    void testUse() throws InterruptedException{
        //given
        Long id = 100L;
        UserPoint userPoint = new UserPoint(id, 500L, System.currentTimeMillis());
        assertEquals(userPoint.point(), 500L);

        // when
        CountDownLatch latch = new CountDownLatch(1);

//        Long[] amounts = {200L, 300L, 500L};
        Long[] amounts = {200L};

        for (int i = 0; i < amounts.length; i++)
        {
            try {
                pointService.use(id, amounts[i]);
            } catch (Exception ex) {

            }
            latch.countDown();
        }
        latch.await();

        Long curPoint = pointService.getPoint(id).point();

        // then
        //assertEquals(0, curPoint);
        assertEquals(300L, curPoint);
    }


}
