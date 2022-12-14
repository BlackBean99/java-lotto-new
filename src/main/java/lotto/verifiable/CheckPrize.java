package lotto.verifiable;

import lotto.Prize;
import java.util.*;
public class CheckPrize implements Verifiable<Prize>{
    @Override
    public Integer checkWithValue(Prize input) {
        List<Integer> numbersWithBonus = input.getNumberWithBonusNumber();
        List<Integer> purchaseNumber = input.getNumber();
        int cnt = 0;
        for (Integer number : numbersWithBonus) {
            if(Collections.frequency(purchaseNumber, number)>0){
                cnt++;
            }
        }
        return cnt;
    }

    public Map<PrizeMoney, Integer> checkPrizes(List<List<Integer>> numbers, List<Integer> prizeNumber, Integer bonusNumber){
        Verifiable<Prize> check = new CheckPrize();
        Map<PrizeMoney, Integer> gradeCount = new EnumMap<>(PrizeMoney.class);
        initialMap(gradeCount);

        List<Integer> bonus = new ArrayList<>();
        bonus.add(bonusNumber);

        for (List<Integer> number : numbers) {
            Integer prizeCount = check.checkWithValue(new Prize(number, prizeNumber));
            Integer bonusCount = check.checkWithValue(new Prize(number, bonus));
            recordGrade(prizeCount, gradeCount, bonusCount);
        }
        return gradeCount;
    }

    private Map<PrizeMoney, Integer> recordGrade(Integer prizeCount, Map<PrizeMoney, Integer> gradeCount, Integer bonusCount) {
        if(prizeCount.equals(6)) {
            gradeCount.put(PrizeMoney.FIRST, gradeCount.get(PrizeMoney.FIRST)+1);
        } else if(prizeCount.equals(5) && bonusCount.equals(1)){
            gradeCount.put(PrizeMoney.SECOND, gradeCount.get(PrizeMoney.SECOND)+1);
        } else if (prizeCount.equals(5)) {
            gradeCount.put(PrizeMoney.THIRD, gradeCount.get(PrizeMoney.THIRD) +1);
        } else if (prizeCount.equals(4)) {
            gradeCount.put(PrizeMoney.FOUR, gradeCount.get(PrizeMoney.FOUR) +1);
        } else if (prizeCount.equals(3)) {
            gradeCount.put(PrizeMoney.FIVE, gradeCount.get(PrizeMoney.FIVE) +1);
        }
        return gradeCount;
    }

    private void initialMap(Map<PrizeMoney, Integer> gradeCount) {
        PrizeMoney[] rankings = PrizeMoney.values();
        for (PrizeMoney ranking : rankings) {
            gradeCount.put(ranking, 0);
        }
    }

    public static void printGrade(Map<PrizeMoney, Integer> gradeIntegerMap) {
        System.out.println("?????? ??????");
        System.out.println("---");
        System.out.println("3??? ?????? (5,000???) - " + gradeIntegerMap.get(PrizeMoney.FIVE) + "???");
        System.out.println("4??? ?????? (50,000???) - " + gradeIntegerMap.get(PrizeMoney.FOUR) + "???");
        System.out.println("5??? ?????? (1,500,000???) - " + gradeIntegerMap.get(PrizeMoney.THIRD) + "???");
        System.out.println("5??? ??????, ????????? ??? ?????? (30,000,000???) - " + gradeIntegerMap.get(PrizeMoney.SECOND) + "???");
        System.out.println("6??? ?????? (2,000,000,000???) - " + gradeIntegerMap.get(PrizeMoney.FIRST) + "???");
    }
}