package com.javacreed.examples.test;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CsvHeaderTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 4277523246275303487L;

  public static CsvHeaderTableModel create(final String data) {
    final String[] lines = data.split("\n");

    final CsvHeaderTableModel tableModel = new CsvHeaderTableModel();
    tableModel.headers = CsvHeaderTableModel.parseRow(lines[0]);

    for (int i = 1; i < lines.length; i++) {
      final String line = lines[i];
      if (line.startsWith("--")) {
        continue;
      }

      tableModel.data.add(CsvHeaderTableModel.parseRow(line));
    }

    return tableModel;
  }

  static String formatCell(final String cell) {
    if (cell.contains(",")) {
      return cell.replace(",", "\\,");
    }

    return cell;
  }

  static String formatRow(final String[] cells) {
    return CsvHeaderTableModel.formatRow(cells, new StringBuilder()).toString();
  }

  static StringBuilder formatRow(final String[] cells, final StringBuilder formatted) {
    formatted.append(CsvHeaderTableModel.formatCell(cells[0]));
    for (int i = 1; i < cells.length; i++) {
      formatted.append(",");
      formatted.append(CsvHeaderTableModel.formatCell(cells[0]));
    }
    return formatted;
  }

  static String[] parseRow(final String line) {
    final String[] cells = line.split("(?<!\\\\),");

    for (int i = 0; i < cells.length; i++) {
      if (cells[i].contains("\\,")) {
        cells[i] = cells[i].replace("\\,", ",");
      }
    }

    return cells;
  }

  private String[] headers;

  private final List<String[]> data = new ArrayList<>();

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return String.class;
  }

  @Override
  public int getColumnCount() {
    return headers.length;
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return headers[columnIndex];
  }

  @Override
  public int getRowCount() {
    return data.size();
  }

  @Override
  public Object getValueAt(final int rowIndex, final int columnIndex) {
    final String[] cells = data.get(rowIndex);
    return cells[columnIndex];
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return true;
  }

  @Override
  public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
    final String value = (String) aValue;
    final String[] cells = data.get(rowIndex);
    cells[columnIndex] = value;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    CsvHeaderTableModel.formatRow(headers, formatted).append("\n");
    for (final String[] row : data) {
      CsvHeaderTableModel.formatRow(row, formatted).append("\n");
    }
    return formatted.toString();
  }
}
