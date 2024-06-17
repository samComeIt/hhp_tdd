package io.hhplus.tdd;

import io.hhplus.tdd.point.PointController;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import io.hhplus.tdd.point.service.PointService;
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
    void testGetPointById()
    {

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
    @DisplayName("특정 유저의 포인트를 충전")
    void testAddPoint()
    {

    }

    @Test
    @DisplayName("특정 유저의 포인트를 사용")
    void testUsePoint()
    {

    }
}
