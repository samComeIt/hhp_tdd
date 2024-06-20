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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
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

    @Test
    @DisplayName("E2E 특정 유저의 충전/이용 내역 조회 성공")
    public void testGetUserById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/point/{id}", 100L)
                        .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100L));
    }

    /**
     * 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     * 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     * 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     * 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @Test
    @DisplayName("특정 유저의 포인트를 조회 성공")
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
    @DisplayName("특정 유저의 충전/이용 내역 조회 성공")
    void testGetPointHistoryById()
    {
        // given
        Long userId01 = 100L;
        Long userId02 = 200L;
        List<PointHistory> table = new ArrayList<>();
        table.add(new PointHistory(1L, userId01, 200L, TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(2L, userId01, 300L, TransactionType.CHARGE, System.currentTimeMillis()));
        table.add(new PointHistory(3L, userId02, 300L, TransactionType.CHARGE, System.currentTimeMillis()));

        // when
        List<PointHistory> result = table.stream().filter(pointHistory -> pointHistory.userId() == userId01).toList();

        // then
        assertFalse(result.size() == 3);
        assertEquals(result.size(), 2);
    }

    @Test
    @DisplayName("특정 유저의 포인트를 충전 성공")
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
        assertFalse(result == 0);
        assertEquals(200L, result);
    }

    @Test
    @DisplayName("특정 유저의 포인트를 사용 성공")
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
