package ma.maxim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Create a cachRegister drawer that keeps track of all notes and coins in it's drawer
 */
public class CashRegister {

    private MoneyDrawer Drawer;
    private Money DrawerValue;

    /**
     * Constructor of CashRegister
     *
     * @param initialContent the initial content of the drawer
     */
    public CashRegister(Map<Money, List<MoneyUnit>> initialContent) {
        // var kopie = new TreeMap<Money, Integer>(Drawer.comparator());
        // kopie.putAll(Drawer);
        Drawer = new MoneyDrawer(new ReverseComparer());
        Drawer.putAll(initialContent);
        // count the drawer
        DrawerValue = countDrawer();
    }

    /**
     * Calculate the change of the the amount to pay and return a list of returned notes
     * and coins and their amount
     * For example
     *   MakeChange(4.51, 10) => { { 5.0, 1 }, { 0.2, 2 }, { 0.05, 1 }, { 0.01, 4 } }
     *   meaning: one "note of 5 euro", two "coins of 20 cent", one "coin of 5 cent" and four "coins of 0.01 cent".
     *
     * @param toPay amount to pay
     * @param paid the amount paid
     * @return a list of all notes and coins that make up the change or
     *         <code>NULL</code> if failed to make change from the cash register
     */
    public Map<Money, List<MoneyUnit>> makeChange(Money toPay, List<MoneyUnit> paid) {
        var returnAmounts = new TreeMap<Money, Long>(new ReverseComparer());
        var paidValue = MoneyUnit.sum(paid);
        var toReturn = paidValue.subtract(toPay);


        if (needRounding(toReturn)) {
            var tmp = toReturn.times(20);
            tmp = tmp.round();
            toReturn = tmp.divide(20);
        }

        for(Map.Entry<Money, List<MoneyUnit>> item : Drawer.entrySet()) {

            var billDrawer = item.getKey();
            var amountDrawer = item.getValue().size();

            // how many times does the coin from drawer fit in what needs to be returned
            // and what is left after
            var amountBills = toReturn.divideToIntegralValue(billDrawer);

            // check if enough bills ∂in drawer
            if (amountBills >= amountDrawer) {
                // if nog enough bills lower to what we have
                amountBills = amountDrawer;
            }


            if (amountBills > 0) {
                // put money to return in returnDrawer
                returnAmounts.put(billDrawer, amountBills);

                // update toReturn
                toReturn = toReturn.subtract(billDrawer.times(amountBills));
            }
        }

        // if you cant return the correct amount fo moneyd
        if (toReturn.equals(new Money(0)) != true) { return null; }


        // put money received in Drawerd
        for (MoneyUnit bill : paid) {
            Drawer.putEntry(bill.getValue(),bill);
        }

        // take out money given back to consumer
        var returnDrawer = new MoneyDrawer(new ReverseComparer());
        for(Map.Entry<Money, Long> item : returnAmounts.entrySet()) {
            Money billDrawer = item.getKey();
            var amountBills = item.getValue();

            while (amountBills > 0) {
                amountBills--;
                var bilOrCoin = Drawer.removeEntry(billDrawer);
                returnDrawer.putEntry(billDrawer,bilOrCoin);
            }
        }
        return returnDrawer;
    }

    private Boolean needRounding(Money change)
    {
        var oneCent = new Money(0.01);
        var twoCent = new Money(0.02);

        var valueOneCent = Money.ZERO;
        var valueTwoCents = Money.ZERO;

        if (Drawer.containsKey(oneCent)) {
            var amount = Drawer.get(new Money(0.01)).size();
            // what is the value of the 0.01 and 0.02 in the drawer
            valueOneCent = new Money(0.01).times(amount);
        }

        if (Drawer.containsKey(twoCent)) {
            var amount = Drawer.get(new Money(0.02)).size(); //Drawer.get(new Money(0.01));
            valueTwoCents = new Money(0.02).times(amount);
        }


        var valueSmallCoins = valueOneCent.add(valueTwoCents);

        // if you do not have enough value in the small coins to return the correct value
        // valueSmallCoins = new Money(0.04);
        if (valueSmallCoins.compareTo(change.modulo(new Money(0.05))) < 0) {
            return true;
        }

        // do i need 0.01 coins to return the correct amount of money
        var cent = change.modulo(new Money(0.02));
        if (cent.compareTo(new Money(0.01)) == 0)
        {
            // do i have 0.01coins
            if (valueOneCent.compareTo(new Money(0)) <= 0) {
                return true;
            }
        }
        return false;
    }



    /**
     * Calculate the total cash added to the register since creation of since last call to processEndOfDay.
     * @return the total amount added
     */
    public Money processEndOfDay() {
        var result = countDrawer().subtract(DrawerValue);
        DrawerValue = countDrawer();
        return result;
    }
    /**
     *
     *
     * @return Money the amount of money in the Drawer
     */
    private Money countDrawer() {
        Money count = Money.ZERO;
        for(Map.Entry<Money, List<MoneyUnit>> moneyBucket : Drawer.entrySet()) {
            count = count.add(moneyBucket.getKey().times(moneyBucket.getValue().size()));
        }
        return count;
    }

}
