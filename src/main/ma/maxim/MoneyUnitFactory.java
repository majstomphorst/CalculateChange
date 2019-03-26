package ma.maxim;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoneyUnitFactory {

    public static List<MoneyUnit> createMultipleUnitsAndAmounts(Map<Money,Integer> drawer) {
        var list = new ArrayList<MoneyUnit>();
        for (Map.Entry<Money,Integer> bucket : drawer.entrySet() ) {
            for (int i = 0; i < bucket.getValue();  i++)
            {
                list.add(createSingle(bucket.getKey()));
            }
        }
        return list;
    }

    public static List<MoneyUnit> createMultipleOfTheSameCoin(Money value, int amount) {
        var list = new ArrayList<MoneyUnit>();
        for (int i = 0; i < amount;  i++)
        {
            list.add(createSingle(value));
        }
        return list;
    }

    public static MoneyUnit createSingle(Money value) {
        if (value.getCents() <= 200) {
            return new Coin(value);
        } else {
            return new Bill(value);
        }
    }

}
