package com.javacreed.examples.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Domain implements Presenter {

  private View view;

  private File currentFile;
  private CsvTableModel tableModel;

  private boolean failIfNoModel(final String title) {
    if (tableModel == null) {
      getView().showWarning(title, "No file is opened");
      return true;
    }

    return false;
  }

  private View getView() {
    if (view == null) {
      throw new IllegalStateException("The view is not set");
    }
    return view;
  }

  private void loadFile(final File file) {
    tableModel = null;
    currentFile = file;

    try {
      final String data = new String(FileUtils.readFileToString(currentFile, "UTF-8"));
      tableModel = CsvTableModel.create(data);
      getView().useCsvTableModel(tableModel);
    } catch (final IOException e) {
      getView().showWarning("Load File", "Failed to load file: '" + file.getAbsolutePath() + "'");
    }
  }

  @Override
  public void onAddColumn() {
    if (failIfNoModel("Add Column")) {
      return;
    }

    final String newColumnName = getView().getNewHeaderName(null);
    if (StringUtils.isNoneBlank(newColumnName)) {
      tableModel.addColumn(newColumnName);
    }
  }

  @Override
  public void onAddRow() {
    if (failIfNoModel("Reload File")) {
      return;
    }

    tableModel.addRow();
  }

  @Override
  public void onDeleteColumn() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onDeleteRow() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onOpen() {
    final File file = view.showOpenFileChoser(currentFile);
    if (file != null) {
      if (file.isFile()) {
        loadFile(file);
      } else {
        getView().showWarning("Open File", "The file: '" + file.getAbsolutePath() + "' is not a file");
      }
    }
  }

  @Override
  public void onReload() {
    if (failIfNoModel("Reload File")) {
      return;
    }

    loadFile(currentFile);
  }

  @Override
  public void onSave() {
    if (failIfNoModel("Save File")) {
      return;
    }

    try {
      final String data = tableModel.toString();
      FileUtils.write(currentFile, data, "UTF-8");
      getView().showMessage("Save File", "File Saved");
    } catch (final IOException e) {
      getView().showWarning("Save File", "Failed to save data to file due to an error");
    }
  }

  @Override
  public void setView(final View view) {
    this.view = view;
  }

}
