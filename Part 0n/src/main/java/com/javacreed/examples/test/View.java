package com.javacreed.examples.test;

import java.io.File;

public interface View {

  String getNewHeaderName(String currentColumnName);

  void setPresenter(Presenter presenter);

  void showMessage(String title, String message);

  File showOpenFileChoser(File selectedFile);

  void showWarning(String title, String message);

  void useCsvTableModel(CsvTableModel tableModel);

}
