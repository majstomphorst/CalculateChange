package ma.maxim;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public abstract class MoneyUnit {

    private Date productionDate;
    private Money value;

    public MoneyUnit(Money value) {
        this.value = value;
        this.productionDate = new Date();
    }

    // returns the value in cents.
    public long getCents() {
        return value.getCents();
    }

    public static Money sum(List<MoneyUnit> list)
    {
        var count = Money.ZERO;
        for (MoneyUnit unit: list) {
            count = count.add(unit.value);
        }
        return count;
    }

    public Money getValue()
    {
        return this.value;
    }


}


