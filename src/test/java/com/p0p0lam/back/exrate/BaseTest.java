package com.p0p0lam.back.exrate;

import junit.framework.TestCase;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;

/**
 * Created by Sergey on 25.02.2016.
 */
public class BaseTest extends TestCase {
    public static final Point POINT = new Point(30.499734, 50.471488);
    public static final Distance DISTANCE_MAX = new Distance(2, Metrics.KILOMETERS);
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }
}
