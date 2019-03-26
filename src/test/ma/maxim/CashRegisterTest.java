package ma.maxim;

import org.junit.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CashRegisterTest {

    public static Map<Money,List<MoneyUnit>> makeSmallCashRegister() {
        var five =      new Money(5);
        var one =       new Money(1);
        var fiftyCent = new Money(0.50);
        var twentyCent =new Money(0.20);
        var fiveCent =  new Money(0.05);
        var twoCent =   new Money(0.02);
        var oneCent =   new Money(0.01);

        Map<Money, List<MoneyUnit>> register = new TreeMap<Money, List<MoneyUnit>>(new ReverseComparer());
        register.put( five,      MoneyUnitFactory.createMultipleOfTheSameCoin( five,0));
        register.put( one,       MoneyUnitFactory.createMultipleOfTheSameCoin( one,9));
        register.put( fiftyCent, MoneyUnitFactory.createMultipleOfTheSameCoin( fiftyCent,5));
        register.put( twentyCent,MoneyUnitFactory.createMultipleOfTheSameCoin( twentyCent,1));
        register.put( fiveCent,  MoneyUnitFactory.createMultipleOfTheSameCoin( fiveCent,0));
        register.put( twoCent,   MoneyUnitFactory.createMultipleOfTheSameCoin( twoCent,0));
        register.put( oneCent,   MoneyUnitFactory.createMultipleOfTheSameCoin( oneCent,0));
        return register;
    }

    @Test
    public void testConstructor() {
        var dut = new CashRegister(new TreeMap<Money,List<MoneyUnit>>(new ReverseComparer()));
        Assert.assertNotNull(dut);
    }

    @Test
    public void testEndOfDayNoSales(){
        // Prepare
        var smallRegister = makeSmallCashRegister();
        var dut = new CashRegister(smallRegister);

        // Test
        var result1 = dut.processEndOfDay();

        // Verify
        Assert.assertEquals(true,result1.equals(new Money(0.00)));

    }
    @Test
    public void testEndOfDayOneSales() {
        // Prepare
        var smallRegister = makeSmallCashRegister();
        var dut = new CashRegister(smallRegister);
        var oneEuro = new Money(1);
        var fityCent = new Money(0.5);

        // test
        var result = dut.makeChange(new Money(2.5), MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(10),1));

        // verify
        Assert.assertNotNull(result);
        Assert.assertEquals(2,result.size());

        Assert.assertTrue(result.containsKey(oneEuro));
        Assert.assertTrue(result.containsKey(fityCent));

        Assert.assertEquals(7,result.get(oneEuro).size());
        Assert.assertEquals(1,result.get(fityCent).size());

    }

    @Test
    public void testNeedRounding(){
        // prepare
        var smallRegister = makeSmallCashRegister();
        var dut = new CashRegister(smallRegister);

        // test
        var result = dut.makeChange(new Money(0.16), MoneyUnitFactory.createMultipleOfTheSameCoin(new Money(0.20),2));

        // verify
        Assert.assertNotNull(result);
        Assert.assertEquals(1,result.size());
        Assert.assertTrue(result.containsKey(new Money(0.20)));
        Assert.assertEquals(1,result.get(new Money(0.20)).size());
    }
}
