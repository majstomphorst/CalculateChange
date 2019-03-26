package ma.maxim;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class CashRegisterParameterizedTest {

    @Parameters( name = "toPay:{0}, paid:{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { new Money(3.0), new float[] {5.0f}, new float[] {1.0f, 2f} },
                { new Money(1.3), new float[] {2.0f}, new float[] {0.5f, 1f, 0.2f, 1f} },
                { new Money(2.0), new float[] {20.0f}, null},
                { new Money(4.5), new float[] {15.0f}, new float[] {1.0f, 9f, 0.5f, 3f} },
                { new Money(2.48), new float[]{3.0f}, new float[] {0.5f, 1f} },
                { new Money(2.5), new float[]{2.5f}, new float[]{} }
        });
    }

    private Money toPay;
    private List<MoneyUnit> paid;
    private Map<Money, List<MoneyUnit>> expected = null;

    public CashRegisterParameterizedTest(Money toPay, float[] paid, float[] expected) {
        this.toPay = toPay;
        this.paid = convertArray(paid);
        this.expected = convertMap(expected);
    }

    private List<MoneyUnit> convertArray(float[] paid) {
        var list = new ArrayList<MoneyUnit>();
        for(float amount : paid) {
            list.add(MoneyUnitFactory.createSingle(new Money(amount)));
        }
        return list;
    }

    private Map<Money,List<MoneyUnit>> convertMap(float[] expected) {
        if (expected == null) {
            return null;
        }

        var result = new TreeMap<Money,List<MoneyUnit>>(new ReverseComparer());

        for (int i = 0; i < expected.length; i += 2) {
            var money = new Money(expected[i]);
            result.put(money,MoneyUnitFactory.createMultipleOfTheSameCoin(money, (int)expected[i+1]) );
        }

        return result;
    }

    @Test
    public void testMakeChange() {
        // prepare
        var register = new CashRegister(CashRegisterTest.makeSmallCashRegister());

        // test
        var result = register.makeChange(toPay,paid);

        // validation
        if (expected == null) {
            Assert.assertNull(result);
        } else {
            Assert.assertNotNull(result);
            Assert.assertEquals(expected.size(),result.size());

            for(Map.Entry<Money, List<MoneyUnit>> item : expected.entrySet()) {
                Assert.assertTrue(result.containsKey(item.getKey()));
                var i = result.get(item.getKey()).size();
                Assert.assertEquals(item.getValue().size(),result.get(item.getKey()).size());
            }
        }
    }

}