package ma.maxim;

import java.util.Comparator;

/**
 * A comparer of Money that orders the list from large to small
 *
 */
public class ReverseComparer implements Comparator<Money> {

    @Override
    public int compare(Money a, Money b) {
        return b.compareTo(a);
    }

}
