package com.theastrologist.rest.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DecanTest {
    @Test
    public void testPremierDecanZero() {
        HouseDecan decan = HouseDecan.getDecan(new Degree(0), House.III);
        assertThat(decan.getDecanNumber(), equalTo(1));
    }

    @Test
    public void testPremierDecan() {
        HouseDecan decan = HouseDecan.getDecan(new Degree(7.35), House.III);
        assertThat(decan.getDecanNumber(), equalTo(1));
    }

    @Test
    public void testDeuxiemeDecan() {
        HouseDecan decan = HouseDecan.getDecan(new Degree(15.6), House.III);
        assertThat(decan.getDecanNumber(), equalTo(2));
    }

    @Test
    public void testTroisiemeDecan() {
        HouseDecan decan = HouseDecan.getDecan(new Degree(22.45), House.III);
        assertThat(decan.getDecanNumber(), equalTo(3));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testOutDecan() {
        HouseDecan.getDecan(new Degree(35.25), House.III);
    }
}