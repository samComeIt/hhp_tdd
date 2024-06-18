package io.hhplus.tdd.point.service;

import io.hhplus.tdd.point.PointController;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import io.hhplus.tdd.point.dto.PointHistoryDto;
import io.hhplus.tdd.point.dto.UserPointDto;
import io.hhplus.tdd.point.repository.PointHistoryRepository;
import io.hhplus.tdd.point.repository.UserPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Autowired
    UserPointRepository userPointRepository;

    public PointService(UserPointRepository userPointRepository, PointHistoryRepository pointHistoryRepository)
    {
        this.userPointRepository = userPointRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }

    /**
     * 포인트 조회
     * @param id
     * @return
     */
    public UserPoint getPoint(Long id)
    {
        return userPointRepository.findPointById(id);
    }


    public UserPoint charge(Long id, Long amount)
    {
        // 현재 amount 0보다 작거나 같은지 확인
        if (!userPointRepository.notLessOrEqualToZero(amount)) throw new RuntimeException("포인트가 0보다 작거나 같으면 안됩니다.");

        UserPointDto userPointDto = new UserPointDto(userPointRepository.findPointById(id));
        long curPoint = userPointDto.getPoint();
        long newPoint = curPoint + amount;

        // 히스토리 추가
        PointHistoryDto pointHistoryDto = new PointHistoryDto();
        pointHistoryDto.setUserId(id);
        pointHistoryDto.setAmount(amount);
        pointHistoryDto.setType(TransactionType.CHARGE);
        pointHistoryDto.setUpdateMillis(System.currentTimeMillis());

        // 히스토리 추가
        addHistory(pointHistoryDto);

        return userPointRepository.save(id, newPoint);
    }

    /**
     * 새로운 포인트 0보다 크거나 같은지 확인
     * @param point
     * @return
     */
    public boolean isPointEnough(Long point)
    {
        if (point < 0) return false;
        return true;
    }
    /**
     * 특정 유저의 포인트 사용
     * @param id
     * @param amount
     * @return
     */
    public UserPoint use(Long id, Long amount)
    {
        // 현재 amount 0보다 작거나 같은지 확인
        if (!userPointRepository.notLessOrEqualToZero(amount)) throw new RuntimeException("포인트가 0보다 작거나 같으면 안됩니다.");

        // 현재 포인트 조회
        UserPointDto userPointDto = new UserPointDto(userPointRepository.findPointById(id));
        long curPoint = userPointDto.getPoint();
        long newPoint = curPoint - amount;

        // 현재 포인트에서 차감 가능 여부 확인
        if (!isPointEnough(newPoint)) throw new RuntimeException("포인트가 부족 합니다.");

        // 히스토리 추가
        PointHistoryDto pointHistoryDto = new PointHistoryDto();
        pointHistoryDto.setUserId(id);
        pointHistoryDto.setAmount(amount);
        pointHistoryDto.setType(TransactionType.USE);
        pointHistoryDto.setUpdateMillis(System.currentTimeMillis());

        // 히스토리 추가
        addHistory(pointHistoryDto);

        // 포인트 업데이트
        return userPointRepository.save(id, newPoint);
    }

    /**
     * 특정 유저의 전체 포인트 내역 조회
     * @param id
     * @return
     */
    public List<PointHistory> findAllHistoryById(Long id)
    {
        return pointHistoryRepository.findAllHistoryById(id);
    }

    /**
     * 특정 유저 충전/사용 내역 등록
     * @param pointHistoryDto
     * @return
     */
    public PointHistory addHistory(PointHistoryDto pointHistoryDto)
    {
        return pointHistoryRepository.save(
                pointHistoryDto.getUserId(),
                pointHistoryDto.getAmount(),
                pointHistoryDto.getType(),
                pointHistoryDto.getUpdateMillis());
    }


}
