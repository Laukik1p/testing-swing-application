package com.javacreed.examples.test;

import java.io.File;

public class ApplicationPresenter implements Presenter {

  private View view;

  private File currentFile;

  private View getView() {
    if (view == null) {
      throw new IllegalStateException("The view is not set");
    }
    return view;
  }

  @Override
  public void onOpen() {
    final File file = view.showOpenFileChooser(currentFile);
    if (file != null) {
      if (file.isFile()) {
        /* Load File */
        currentFile = file;
      } else {
        getView().showWarning("Open File", "The file: '" + file.getAbsolutePath() + "' is not a file");
      }
    }
  }

  @Override
  public void setView(final View view) {
    this.view = view;
  }
}
