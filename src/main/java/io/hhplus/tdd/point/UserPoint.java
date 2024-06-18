package io.hhplus.tdd.point;

public record UserPoint(
        long id,
        long point,
        long updateMillis
) {

    public static UserPoint empty(long id) {
        return new UserPoint(id, 0, System.currentTimeMillis());
    }
    
//
//    public UserPoint add(Long id, Long amount)
//    {
//        return new UserPoint(id, amount, System.currentTimeMillis());
//    }

}
