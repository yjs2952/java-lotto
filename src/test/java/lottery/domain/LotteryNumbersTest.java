package lottery.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;


public class LotteryNumbersTest {
    Lottery winning = new Lottery(new StaticPicker("11,12,13,14,15,16"));

    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,3,4,5,6",
            "10,11,12,13,14,15",
            "40,41,42,43,44,45"
    })
    void validNumbers(String input) {
        final Picker picker = new StaticPicker(input);
        assertThatThrownBy(() -> new Lottery(picker))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,3,4,5",
            "1,2,3,4,5,6,7",
            "1,2,3,4,5,5",
            "1,2,3,4,5,6,6",
    })
    void invalidNumbers(String input) {
        final Picker picker = new StaticPicker(input);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Lottery(picker));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 11,22,23,24,25,26",
            "2 | 11,12,23,24,25,26",
            "3 | 11,12,13,24,25,26",
            "4 | 11,12,13,14,25,26",
            "5 | 11,12,13,14,15,26",
            "6 | 11,12,13,14,15,16",
    })
    void countMatched(int count, String input) {
        final Picker picker = new StaticPicker(input);
        final Lottery lottery = new Lottery(picker);
        assertThat(lottery.countMatched(winning)).isEqualTo(count);
    }
}
