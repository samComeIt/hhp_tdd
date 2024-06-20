package io.hhplus.tdd.point.dto;

import io.hhplus.tdd.point.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointHistoryDto {
    private Long userId;
    private Long amount;
    private TransactionType type;
    private Long updateMillis;
}
