package com.tailf.confm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class YangStringTest {

    private String str1;
    private String str2;
    private String str3;
    private String str4;
    private int intVal;
    private YangString ys1;
    private YangString ys2;
    private YangString ys3;
    private YangString ys4;
    private YangInt16 yi16;


    @Before
    public void setUp() throws Exception {
        str1 = "A test string";
        str2 = "A test string";
        str3 = "Another \n string";
        str4 = "  Hi   there ";
        intVal = 7;
        ys1 = new YangString(str1);
        ys2 = new YangString(str1);
        ys3 = new YangString(str3);
        ys4 = new YangString(str4);
        yi16 = new YangInt16(intVal);
    }

    @Test
    public void testSetValueString() {
        assertTrue(ys1.value.equals(str1));
        assertTrue(ys1.value.equals(str2));
        assertFalse(ys1.value.equals(str3));
        ys1.setValue(str3);
        assertTrue(ys1.value.equals(str3));
        assertFalse(ys1.value.equals(str1));
    }

    @Test
    public void testToString() {
        assertTrue(ys1.toString().equals(str1));
    }

    @Test
    public void testEqualsObject() {
        assertTrue(ys1.equals(ys1));
        assertTrue(ys1.equals(ys2));
        assertTrue(ys1.equals(str1));
        assertFalse(ys1.equals(yi16));
        assertFalse(yi16.equals(ys1));
    }

    @Test
    public void testCanEqual() {
        assertTrue(ys1.canEqual(ys1));
        assertTrue(ys1.canEqual(ys2));
        assertTrue(ys1.canEqual(str1));
        assertTrue(ys1.canEqual(str2));
        assertFalse(ys1.canEqual(intVal));
        assertFalse(ys1.canEqual(yi16));
    }

    @Test
    public void testFromStringString() {
        assertTrue(ys1.fromString(str1).equals(str1));
        assertTrue(ys1.fromString(str2).equals(str2));
    }

    @Test
    public void testEqualsString() {
        assertTrue(ys1.equals(str1));
        assertTrue(ys1.equals(str2));
        assertFalse(ys1.equals(str3));
    }

    @Test
    public void testPatternString() throws ConfMException {
        ys1.pattern(str1);
        ys2.pattern(".*string");
        ys3.pattern(".*\n.*string");
        ys3.pattern("An.*\n.*");
    }

    @Test(expected=ConfMException.class)
    public void testPatternStringException() throws ConfMException {
        ys1.pattern(str1+str2);
    }

    @Test
    public void testPatternStringArray() throws ConfMException {
        ys1.pattern(new String[] {"A .*", ".*string"});
    }

    @Test
    public void testLength() throws ConfMException {
        ys1.length(str1.length());
        ys3.length(str3.length());
    }

    @Test(expected=ConfMException.class)
    public void testLengthException() throws ConfMException {
        ys1.length(str3.length());
    }

    @Test
    public void testMinInt() throws ConfMException {
        ys1.min(str1.length()-1);
    }

    @Test
    public void testMaxInt() throws ConfMException {
        ys1.max(str1.length()+1);
    }

    @Test(expected=ConfMException.class)
    public void testMinString() throws ConfMException {
        ys1.min(str1);
    }

    @Test(expected=ConfMException.class)
    public void testMaxString() throws ConfMException {
        ys1.max(str1);
    }

    @Test
    public void testWsReplace() {
        assertTrue(ys3.value.contains("\n"));
        ys3.wsReplace();
        assertFalse(ys3.value.contains("\n"));
    }

    @Test
    public void testWsCollapse() {
        assertTrue(ys4.value.equals(str4));
        assertTrue(ys4.value.startsWith(" "));
        assertTrue(ys4.value.endsWith(" "));
        assertTrue(ys4.value.contains("   "));
        ys4.wsCollapse();
        assertFalse(ys4.value.equals(str4));
        assertFalse(ys4.value.startsWith(" "));
        assertFalse(ys4.value.endsWith(" "));
        assertFalse(ys4.value.contains("   "));
        assertTrue(ys4.value.equals("Hi there"));
    }

    @Test
    public void testEnumeration() {
        assertTrue(ys1.enumeration(str1));
    }

}
