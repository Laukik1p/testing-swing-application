package com.javacreed.examples.test;

import java.io.File;

public interface View {

  void setPresenter(Presenter presenter);

  void showMessage(String title, String message);

  File showOpenFileChooser(File currentFile);

  void showWarning(String title, String message);

}
