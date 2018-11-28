package com.javacreed.examples.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CsvTableModel extends AbstractTableModel {

  private static final long serialVersionUID = 4277523246275303487L;

  public static CsvTableModel create(final String data) {
    final String[] lines = data.split("\n");

    final CsvTableModel tableModel = new CsvTableModel();
    tableModel.headers.addAll(CsvTableModel.parseRow(lines[0]));
    final int columns = tableModel.getColumnCount();

    for (int i = 1; i < lines.length; i++) {
      final String line = lines[i];
      if (line.startsWith("--")) {
        continue;
      }

      tableModel.data.add(CsvTableModel.parseRow(line, columns));
    }

    return tableModel;
  }

  static String formatCell(final String cell) {
    if (cell.contains(",")) {
      return cell.replace(",", "\\,");
    }

    return cell;
  }

  static String formatRow(final List<String> cells) {
    return CsvTableModel.formatRow(cells, new StringBuilder()).toString();
  }

  static StringBuilder formatRow(final List<String> cells, final StringBuilder formatted) {
    formatted.append(CsvTableModel.formatCell(cells.get(0)));
    for (int i = 1; i < cells.size(); i++) {
      formatted.append(",");
      formatted.append(CsvTableModel.formatCell(cells.get(i)));
    }
    return formatted;
  }

  static List<String> parseRow(final String line) {
    final List<String> cells = new ArrayList<>(Arrays.asList(line.split("(?<!\\\\),")));

    for (int i = 0; i < cells.size(); i++) {
      if (cells.get(i).contains("\\,")) {
        cells.set(i, cells.get(i).replace("\\,", ","));
      }
    }

    return cells;
  }

  static List<String> parseRow(final String line, final int columns) {
    final List<String> cells = CsvTableModel.parseRow(line);
    while (cells.size() < columns) {
      cells.add("");
    }
    return cells;
  }

  private final List<String> headers = new ArrayList<>();

  private final List<List<String>> data = new ArrayList<>();

  public void addColumn(final String newColumnName) {
    headers.add(newColumnName);
    for (final List<String> row : data) {
      row.add("");
    }
    fireTableStructureChanged();
  }

  public void addRow() {
    final int columns = getColumnCount();
    final List<String> row = new ArrayList<String>(columns);
    for (int i = 0; i < columns; i++) {
      row.add("");
    }

    final int index = getRowCount();
    data.add(row);
    fireTableRowsInserted(index, index);
  }

  @Override
  public Class<?> getColumnClass(final int columnIndex) {
    return String.class;
  }

  @Override
  public int getColumnCount() {
    return headers.size();
  }

  @Override
  public String getColumnName(final int columnIndex) {
    return headers.get(columnIndex);
  }

  @Override
  public int getRowCount() {
    return data.size();
  }

  @Override
  public Object getValueAt(final int rowIndex, final int columnIndex) {
    final List<String> cells = data.get(rowIndex);
    return cells.get(columnIndex);
  }

  @Override
  public boolean isCellEditable(final int rowIndex, final int columnIndex) {
    return true;
  }

  @Override
  public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
    final String value = (String) aValue;
    final List<String> cells = data.get(rowIndex);
    cells.set(columnIndex, value);
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    CsvTableModel.formatRow(headers, formatted).append("\n");
    for (final List<String> row : data) {
      CsvTableModel.formatRow(row, formatted).append("\n");
    }
    return formatted.toString();
  }
}
