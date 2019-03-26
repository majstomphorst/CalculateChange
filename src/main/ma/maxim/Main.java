package ma.maxim;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.management.InvalidApplicationException;


/**
 * Main class for calculate change
 */
public class Main {

    /**
     * main entry point for the program
     *
     * @throws InvalidApplicationException when an error occurs
     */
    public static void main(String[] args) throws InvalidApplicationException {
        var fifty =     new Money(50);
        var twenty =    new Money(20);
        var ten =       new Money(10);
        var five =      new Money(5);
        var two =       new Money(2);
        var one =       new Money(1);
        var fiftyCent = new Money(0.50);
        var twentyCent =new Money(0.20);
        var tenCent =   new Money(0.10);
        var fiveCent =  new Money(0.05);
        var twoCent =   new Money(0.02);
        var oneCent =   new Money(0.01);

        Map<Money, List<MoneyUnit>> initialContent = new TreeMap<Money, List<MoneyUnit>>(new ReverseComparer());
        initialContent.put( fifty,     MoneyUnitFactory.createMultipleOfTheSameCoin( fifty,0));
        initialContent.put( twenty,    MoneyUnitFactory.createMultipleOfTheSameCoin( twenty,1));
        initialContent.put( ten,       MoneyUnitFactory.createMultipleOfTheSameCoin( ten,0));
        initialContent.put( five,      MoneyUnitFactory.createMultipleOfTheSameCoin( five,1));
        initialContent.put( two,       MoneyUnitFactory.createMultipleOfTheSameCoin( two,1));
        initialContent.put( one,       MoneyUnitFactory.createMultipleOfTheSameCoin( one,4));
        initialContent.put( fiftyCent, MoneyUnitFactory.createMultipleOfTheSameCoin( fiftyCent,2));
        initialContent.put( twentyCent,MoneyUnitFactory.createMultipleOfTheSameCoin( twentyCent,10));
        initialContent.put( tenCent,   MoneyUnitFactory.createMultipleOfTheSameCoin( tenCent,3));
        initialContent.put( fiveCent,  MoneyUnitFactory.createMultipleOfTheSameCoin( fiveCent,7));
        initialContent.put( twoCent,   MoneyUnitFactory.createMultipleOfTheSameCoin( twoCent,1));
        initialContent.put( oneCent,   MoneyUnitFactory.createMultipleOfTheSameCoin( oneCent,5));

        CashRegister register = new CashRegister(initialContent);

        var result = register.makeChange(new Money(4.51), MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(10),1));
        showAmount("MakeChange for 4,51 from 10 euro", result); // 5, .20, .20, .05, .02, .01, .01

        result = register.makeChange(new Money(1.30), MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(5),1));
        showAmount("MakeChange for 1,30 from 5 euro", result); // 2, 1, .5, .2

        result = register.makeChange(new Money(0.97), MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(20),1));
        showAmount("MakeChange for 0,97 from 20 euro", result); // 10, 5, 1, 1, 1, .5, .2, .2, .1, .01, .01, .01

        var profit = register.processEndOfDay();

        showProfit(profit);

        if (!profit.equals(new Money(6.78))) {
            throw new InvalidApplicationException("Expected different amount");
        }

        var map = new TreeMap<Money, Integer>(new ReverseComparer());
        map.put(new Money(20),1);
        map.put(new Money(0.50),1);
        var moneyUnits = MoneyUnitFactory.createMultipleUnitsAndAmounts(map);
        result = register.makeChange(new Money(1.30), moneyUnits); // MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(20.50),1)
        showAmount("MakeChange of 1,30 from 20 euro", result); // could not make change

        result = register.makeChange(new Money(1.30),MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(2.5),1));
        showAmount("MakeChange of 1,30 from 2.50 euro", result);  // .20, .20, .20, .20, .20, .10, .10

        profit = register.processEndOfDay();

        showProfit(profit);

        if (!profit.equals(new Money(1.30))) {
            throw new InvalidApplicationException("Expected different amount on second day");
        }
    }

    /**
     * Show the current change amount
     * @param message the message
     * @param change the change
     */
    private static void showAmount(String message, Map<Money, List<MoneyUnit>> change) {

        if (change != null) {
            System.out.print(message);
            System.out.println(": ");

            for(List<MoneyUnit> bucket : change.values()) {
                System.out.printf("%-5.50s",bucket.size());
                for(MoneyUnit billOrCoin : bucket) {
                    System.out.printf("%-5.50s", billOrCoin);
                    System.out.printf("\t");
                }
                System.out.println();
            }
        } else {
            System.out.println("Could not make change");
        }

    }

    /**
     * Show the profit of the day
     * @param profit the profit
     */
    private static void showProfit(Money profit) {
        System.out.print("Profit: ");
        System.out.println(profit.toString());
    }
}