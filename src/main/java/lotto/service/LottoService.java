package lotto.service;

import lotto.model.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LottoService {

    private Lotto lotto;
    private Bonus bonus;
    private Payment payment;
    private List<List<Integer>> userLottoNumbers;
    private Total total;

    public void buyLottoTickets(String inputAmount) {
        payment = new Payment(inputAmount);
    }

    public void createLottoWinningNumber(String inputLotto) {

        List<Integer> lottoWinningNumber = stringToList(inputLotto);
        lotto = new Lotto(lottoWinningNumber);

    }

    public List<Integer> stringToList(String inputLotto) {
        return Arrays.stream(inputLotto.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    public List<List<Integer>> getUserLottoNumbers() {
        int ticketCount = payment.getTicket();
        User user = new User(ticketCount);
        userLottoNumbers = new ArrayList<>(user.getLottoCount());
        return userLottoNumbers;
    }

    public void createBonusNumber(String inputBonus) {
        bonus = new Bonus(lotto, inputBonus);
    }

    public void calculatePrize() {
        List<Integer> winningLottoNumber = lotto.getNumbers();
        Result result = new Result(winningLottoNumber, userLottoNumbers, bonus.getBonusNumber());
        PrizeResult rank = new PrizeResult(result.getLottoMatch(),result.getHasBonusMatch());

        List<Prize> totalPrizeResult = rank.getRanking();
        getTotalPrizeResult(totalPrizeResult);
    }

    private void getTotalPrizeResult(List<Prize> totalPrizeResult) {
        total = new Total(totalPrizeResult);
        System.out.println("당첨 통계");
        System.out.println("---");
        for (Prize prize : Prize.values()) {
            String formattedPrice = String.format("%,d", prize.getPrice());
            System.out.printf(prize.getMessage(),formattedPrice,prize.getCount());
        }
    }

    public void calculateProfit() {
        int ticketCount = payment.getTicket();
        Profit profit = new Profit(ticketCount);
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String formattedProfitRate = decimalFormat.format(profit.getProfitRate());
        System.out.printf("총 수익률은 %s%%입니다.\n",formattedProfitRate);
    }
}
