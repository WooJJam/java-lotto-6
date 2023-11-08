package lotto.model;

import lotto.validator.LottoNumberValidator;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        LottoNumberValidator.validate(numbers);
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

}
