package ma.maxim;

import java.util.*;

public class MoneyDrawer extends TreeMap<Money,List<MoneyUnit>>/*implements Map<Money, List<MoneyUnit>>*/ {

    public MoneyDrawer(Comparator<Money> comp) {
        super(comp);
    }


    public void putEntry(Money key, MoneyUnit value)
    {
        if (!this.containsKey(key)) {
            this.put(key,new ArrayList<MoneyUnit>());
        }
        this.get(key).add(value);
    }

    public MoneyUnit removeEntry(Money key)
    {
        if (!this.containsKey(key)) {
            return null;
        }
        List<MoneyUnit> list = this.get(key);

        var entry = list.remove(0);

        if (list.isEmpty()) {
            this.remove(key);
        }

        return entry;
    }


}
