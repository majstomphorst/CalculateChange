package ma.maxim;

/**
 * Simple implementation of a money class that wraps a BigDecimal
 */
public class Money {

    public long getCents() {
        return cents;
    }

    private long cents;

    public static Money ZERO = new Money(0);

    /**
     * Constructor
     * @param val the value
     */
    public Money(int val) {
        cents = val * 100;
    }

    /**
     * Constructor
     * @param val the value
     */
    public Money(double val) {
        cents = (long)(val * 100.0);
    }

    /**
     * internal Constructor for add etc.
     * @param val
     */
    private Money(long cents, int decimals) {
        this.cents = cents;
    }

    /**
     * Print a representative of the Money value
     */
    @Override
    public String toString() {
        return toString('E');
    }

    /**
     * Print a representative of the Money value
     * @param currencySymbol the symbol to use as currency or <code>null</code> for no symbol
     */
    public String toString(Character currencySymbol) {

        if (currencySymbol == null) {
            return String.format("%1$,.2f", cents/100.0);
        }
        return String.format("%1s %2$,.2f", currencySymbol, cents/100.0);

    }

    /**
     * Compare to another money object
     * @param val the other value
     * @return -1, 0, or 1 as this Money is numerically less than, equal to, or greater than val.
     */
    public int compareTo(Money val) {
        return Long.compare(cents, val.cents);
    }

    /**
     * compare one money value to another
     * @param other the other
     * @return true if the same amount
     */
    public boolean equals(Money other) {
        if (other == null) {
            return false;
        }
        return cents == other.cents;
    }

    public Money modulo(Money val) {
        return new Money(cents % val.cents,2);
    }

    /**
     * Add more money to our value
     * @param val the value toe add
     * @return the sum value
     */
    public Money add(Money val) {
        return new Money(cents + val.cents, 2);
    }

    /**
     * Subtract money from our value
     * @param val the value to subtract
     * @return the resulting value
     */
    public Money subtract(Money val) {
        return new Money(cents - val.cents, 2);
    }

    /**
     * Multiply by a number
     * @param count the number to multiply by
     * @return the value * count
     */
    public Money times(double count) {
        return new Money((long)(cents * count), 2);
    }

    /**
     * Divide by a number
     * @param divisor the number to divide by
     * @return  the value / divisor
     */
    public Money divide(double divisor) {
        return new Money((long)(cents / divisor), 2);
    }

    /**
     * Find the ratio between two amounts
     * @param divisor the amount to divide by
     * @return the resulting ratio
     */
    public double divide(Money divisor) {

        return (double)cents / (double)divisor.cents;
    }

    /**
     * Divide by a whole number
     * @param divisor the number to divide by
     * @return  the value / divisor
     */
    public long divideToIntegralValue(Money divisor) {
        return cents / divisor.cents;
    }


    /**
     * Give the remainder after dividing by a number
     * @param divisor the number to divide by
     * @return the remainder after value / divisor
     */
    public Money remainder(double divisor) {
        return new Money((long)(cents % divisor), 2);
    }

    /**
     * Give the remainder after dividing by a number
     * @param divisor the number to divide by
     * @return the remainder after value / divisor
     */
    public double remainder(Money divisor) {
        return (double)cents % (double)divisor.cents;
    }

    public Money round() {
        return new Money((cents/100)*100, 2);
    }
}
