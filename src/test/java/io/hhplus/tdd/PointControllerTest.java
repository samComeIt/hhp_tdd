package io.hhplus.tdd;

import io.hhplus.tdd.point.PointController;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.dto.UserPointDto;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import io.hhplus.tdd.point.service.PointService;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PointController pointController;

    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private PointService pointService;


    /**
     * 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     * 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     * 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     * 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @Test
    @DisplayName("특정 유저의 포인트를 조회")
    void testGetPointById() throws Exception
    {
        // given
        long id = 1000L;

        // when
        long curPoint = userPointRepository.findPointById(id).point();

        // then
        assertEquals(0, curPoint);
    }

    @Test
    @DisplayName("특정 유저의 충전/이용 내역 조회")
    void testGetPointHistoryById()
    {
        List<PointHistory> table = new ArrayList<>();
        table.add(new PointHistory(1L, 100L, 200L, TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(2L, 100L, 300L, TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(3L, 200L, 300L, TransactionType.CHARGE, System.currentTimeMillis()));

    }

    @Test
    @DisplayName("특정 유저의 충전/이용 내역 조회 2")
    void testGetPointHistoryById02()
    {

    }

    @Test
    @DisplayName("특정 유저의 포인트를 충전")
    void testAddPoint()
    {
        // given
        long id = 1000L;
        long amount = 200L;

        // when
        long curPoint = userPointRepository.findPointById(id).point();
        long newPoint = curPoint + amount;

        userPointRepository.save(id, newPoint);

        long result = userPointRepository.findPointById(id).point();
        // then
        assertEquals(0, result);
        assertEquals(200L, result);
    }

    @Test
    @DisplayName("특정 유저의 포인트를 사용")
    void testUsePoint()
    {
        // given
        long id = 1000L;
        long originalPoint = 500L;

        long amount = 100L;

        UserPoint userPoint = new UserPoint(id, originalPoint, System.currentTimeMillis());
        assertEquals(originalPoint, userPoint.point());

        // when
        //check point is 0 or less
        boolean pass = userPointRepository.notLessOrEqualToZero(amount);
        assertTrue(pass);

//        long curPoint = userPointRepository.findPointById(id).point();
//        long newPoint = curPoint - amount;

        UserPointDto userPointDto = new UserPointDto(userPoint);
        long curPoint = userPointDto.getPoint();
        long newPoint = curPoint - amount;

        assertEquals(400L, newPoint);

        // 포인트 차감전, 현재 보유 포인트 동일한지 확인
        assertEquals(originalPoint, userPointDto.getPoint());

        boolean pass02 = userPointRepository.notLessOrEqualToZero(newPoint);
        assertTrue(pass02);

        userPointRepository.save(id, newPoint);
        long result = userPointRepository.findPointById(id).point();

        // then
        assertTrue(0 != result);
        assertEquals(400L, result);
    }


}
