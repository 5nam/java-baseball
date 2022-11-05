package baseball;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static baseball.Computer.*;
import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 게임종료_후_재시작() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("246", "135", "1", "597", "589", "2");
                    assertThat(output()).contains("낫싱", "3스트라이크", "1볼 1스트라이크", "3스트라이크", "게임 종료");
                },
                1, 3, 5, 5, 8, 9
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

    // Computer class 구현을 위한 테스트 코드
    @Test
    public void 임의의_수가_3개인지_테스트() {
        Computer computer = getComputer();
        List<Integer> number = computer.createRandomNumber();
        assertThat(number.size()).isEqualTo(3);
    }

    @Test
    public void 임의의_수가_서로_다른_숫자인지_테스트() {
        Computer computer = getComputer();
        List<Integer> before_number = computer.createRandomNumber();
        Set<Integer> after_number = new HashSet<>(before_number);
        assertThat(after_number.size()).isEqualTo(before_number.size());
    }

    @Test
    public void 임의의_3자리_숫자의_범위_테스트() {
        Computer computer = getComputer();
        List<Integer> number = computer.createRandomNumber();
        List<Integer> range = Arrays.asList(1,2,3,4,5,6,7,8,9);

        for(int numberIndex = 0; numberIndex < MAX; numberIndex++) {
            assertThat(range).contains(number.get(numberIndex));
        }
    }

    @Test
    public void 플레이어가_입력한_숫자_스트라이크_테스트() {
        Computer computer = getComputer();
        List<Integer> answer = Arrays.asList(1,2,3);
        List<Integer> userInput = Arrays.asList(1,2,3);

        String result = computer.judgeNumber(answer, userInput);

        assertThat(result).isEqualTo(STRIKE);
    }

    @Test
    public void 플레이어가_입력한_숫자_n볼_n스트라이크_테스트() {
        Computer computer = getComputer();
        List<Integer> answer = Arrays.asList(1,2,3);
        List<Integer> userInput = Arrays.asList(2,4,3);

        String result = computer.judgeNumber(answer, userInput);

        assertThat(result).isEqualTo("1볼 1스트라이크");
    }

    @Test
    public void 플레이어가_입력한_숫자_n볼_테스트() {
        Computer computer = getComputer();
        List<Integer> answer = Arrays.asList(1,2,3);
        List<Integer> userInput = Arrays.asList(2,1,4);

        String result = computer.judgeNumber(answer, userInput);

        assertThat(result).isEqualTo("2볼");
    }

    @Test
    public void 플레이어가_입력한_숫자_n스트라이크_테스트() {
        Computer computer = getComputer();
        List<Integer> answer = Arrays.asList(1,2,3);
        List<Integer> userInput = Arrays.asList(1,5,4);

        String result = computer.judgeNumber(answer, userInput);

        assertThat(result).isEqualTo("1스트라이크");
    }

    @Test
    public void 플레이어가_입력한_숫자_낫싱_테스트() {
        Computer computer = getComputer();
        List<Integer> answer = Arrays.asList(1,2,3);
        List<Integer> userInput = Arrays.asList(4,5,6);

        String result = computer.judgeNumber(answer, userInput);

        assertThat(result).isEqualTo("낫싱");
    }
}
