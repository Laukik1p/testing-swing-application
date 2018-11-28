package com.javacreed.examples.test;

public interface Presenter {

  void onAddColumn();

  void onAddRow();

  void onDeleteColumn();

  void onDeleteRow();

  void onOpen();

  void onReload();

  void onSave();

  void setView(View view);

}
