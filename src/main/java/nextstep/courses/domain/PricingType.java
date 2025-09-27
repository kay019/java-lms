package nextstep.courses.domain;

import nextstep.courses.exception.CustomException;
import nextstep.payments.domain.Payment;

public class PricingType {

    private final int sessionAmount;
    private final CourseType courseType;



    public PricingType(CourseType courseType, int sessionAmount) {
        this.courseType = courseType;
        this.sessionAmount = sessionAmount;
        validate(sessionAmount);
    }

    private void validate(int sessionAmount) {
        if (isPremium() && sessionAmount <= 0) {
            throw CustomException.NOT_ALLOWED_PREMIUM_AMOUNT;
        }
        if (!isPremium() && sessionAmount > 0) {
            throw CustomException.NOT_ALLOWED_FREE_AMOUNT;
        }
    }

    public boolean isPremium() {
        return courseType.equals(CourseType.PREMIUM);
    }

    public void validateAmount(Payment payment) {
        if (isPremium() && !payment.matchingAmount(sessionAmount)) {
            throw CustomException.NOT_MATCHING_SESSION_AMOUNT;
        }
    }

    public int getSessionAmount() {
        return sessionAmount;
    }

    public CourseType getCourseType() {
        return courseType;
    }
}
