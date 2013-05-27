package com.tailf.jnc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrefixTest {

	@Test
	public void constructWithPrefix() {
		final String xmlString = new Prefix("prefix", "nsValue").toXMLString();
		assertEquals("xmlns:prefix=\"nsValue\"", xmlString);
	}

	@Test
	public void constructWithEmptyPrefix() {
		final String xmlString = new Prefix("", "nsValue").toXMLString();
		assertEquals("xmlns=\"nsValue\"", xmlString);
	}
}
