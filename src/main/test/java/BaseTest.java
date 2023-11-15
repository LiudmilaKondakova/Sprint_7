package test.java;

import createCourier.Courier;
import org.junit.After;

public class BaseTest {
    protected Courier courier;
    private final Utils utils = new Utils();

    @After
    public void deleteCourier() {
        if (courier != null) {
            utils.deleteCourier(courier);
        }
    }
}
