package ma.maxim;

import java.util.Date;

public class Coin extends MoneyUnit {

    // the value of coin should be in cents
    public Coin(Money value) {
        super(value);
    }

    /**
     * Print a representative of the bill
     */
    @Override
    public String toString() {
        return  String.format("Coin of: %1d cents", getCents());
    }




}
