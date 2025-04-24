package nextstep.session.domain;

import java.util.Currency;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.session.exception.MoneyIllegalArgumentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {
    @Test
    @DisplayName("금액만 입력하면 기본 통화(KRW)로 Money 객체가 생성된다")
    void createMoneyWithDefaultCurrency() {
        Money money = new Money(1000);
        assertThat(money.getCurrency()).isEqualTo(Currency.getInstance("KRW"));
    }

    @Test
    @DisplayName("금액과 통화를 입력하면 해당 통화로 Money 객체가 생성된다")
    void createMoneyWithCustomCurrency() {
        Money money = new Money(2000, Currency.getInstance("USD"));
        assertThat(money.getCurrency()).isEqualTo(Currency.getInstance("USD"));
    }

    @Test
    @DisplayName("음수 금액 입력 시 MoneyIllegalArgumentException이 발생한다")
    void negativeAmountThrowsException() {
        assertThatThrownBy(() -> new Money(-1))
            .isInstanceOf(MoneyIllegalArgumentException.class);
    }

    @Test
    @DisplayName("isValid는 금액이 일치하면 true, 다르면 false를 반환한다")
    void isValidTest() {
        Money money = new Money(500);
        assertThat(money.isValid(500)).isTrue();
        assertThat(money.isValid(100)).isFalse();
    }
}
