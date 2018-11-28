package com.javacreed.examples.test;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CsvTableCellRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = -3944399782686922350L;

  @Override
  public Component getTableCellRendererComponent(final JTable table, Object value, final boolean isSelected,
      final boolean hasFocus, final int row, final int column) {

    if (value == null) {
      value = "{NULL}";
    }

    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
  }

}
