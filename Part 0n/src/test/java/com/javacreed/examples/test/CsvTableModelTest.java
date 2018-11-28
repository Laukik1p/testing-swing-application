package com.javacreed.examples.test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

public class CsvTableModelTest {

  @Test
  public void testParseRow() {
    final Map<String, String[]> data = new LinkedHashMap<>();
    data.put("a,b,c", new String[] { "a", "b", "c" });
    data.put("a,b\\,c", new String[] { "a", "b,c" });
    data.put("a,NULL", new String[] { "a", "NULL" });

    for (final Entry<String, String[]> entry : data.entrySet()) {
      final String line = entry.getKey();
      final List<String> expected = Arrays.asList(entry.getValue());
      Assert.assertEquals(line, expected, CsvTableModel.parseRow(line));
    }
  }
}
