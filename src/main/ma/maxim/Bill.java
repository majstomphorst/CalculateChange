package ma.maxim;

import java.util.Date;

/**
 * this a billwith the value of that bill
 */
public class Bill extends MoneyUnit {

    // the value of bil should be in euros
    private static volatile int count;
    protected int serialNumber;

    /**
     *
     * @param euros
     */
    public Bill(Money value) {
        super(value);
        serialNumber = ++count;
    }

    /**
     * Print a representative of the bill
     */
    @Override
    public String toString() {

        return  String.format("Bill of: %1s euros", getValue().toString(null));
    }
}
