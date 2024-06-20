package io.hhplus.tdd.point.dto;

import io.hhplus.tdd.point.UserPoint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPointDto {
    private Long id;
    private Long point;
    private Long updateMillis;

    public UserPointDto(UserPoint userPoint)
    {
        this.id = userPoint.id();
        this.point = userPoint.point();
        this.updateMillis = userPoint.updateMillis();
    }
}
