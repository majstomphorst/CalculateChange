package ma.maxim;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MoneyUnitFactoryTest {

    @Test
    public void testCreateSingle() {
        // prepare
        var coin = new Money(0.10);

        // test
        var dut = MoneyUnitFactory.createSingle(coin);

        // validate
        Assert.assertNotNull(dut);
        Assert.assertEquals(Coin.class,dut.getClass());
        Assert.assertEquals(true,new Money(0.10).equals(dut.getValue()));
    }

    @Test
    public void createMultipleOfTheSameCoin() {
        var bill = new Money(5);
        var amount = 4;

        var dut = MoneyUnitFactory.createMultipleOfTheSameCoin(bill,amount);

        Assert.assertNotNull(dut);
        Assert.assertEquals(amount,dut.size());

        for( MoneyUnit unit : dut) {
            Assert.assertEquals(true,unit.getValue().equals(bill));
        }
    }

    @Test
    public void testCreateMultipleUnitsAndAmounts() {
        var five =      new Money(5);
        var fiftyCent = new Money(0.50);
        var oneCent =   new Money(0.01);

        Map<Money, List<MoneyUnit>> register = new TreeMap<Money, List<MoneyUnit>>(new ReverseComparer());
        register.put( five,      MoneyUnitFactory.createMultipleOfTheSameCoin( five,3));
        register.put( fiftyCent, MoneyUnitFactory.createMultipleOfTheSameCoin( fiftyCent,4));
        register.put( oneCent,   MoneyUnitFactory.createMultipleOfTheSameCoin( oneCent,2));

        Assert.assertEquals(3,register.size());
        var i = register.get(five);
        Assert.assertEquals(3,register.get(five).size());
        Assert.assertEquals(4,register.get(fiftyCent).size());
        Assert.assertEquals(2,register.get(oneCent).size());
    }

}
