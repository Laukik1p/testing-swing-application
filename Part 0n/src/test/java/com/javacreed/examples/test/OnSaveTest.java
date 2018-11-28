package com.javacreed.examples.test;

import java.io.File;
import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class OnSaveTest {

  @Test
  public void testSaveWithFile() throws IOException {
    final Presenter presenter = new Domain();
    final View view = EasyMock.createNiceMock(View.class);

    final File returnFile = File.createTempFile("Some File", "csv");
    returnFile.deleteOnExit();
    Assert.assertTrue(returnFile.isFile());

    EasyMock.expect(view.showOpenFileChoser(null)).andReturn(returnFile);
    view.useCsvTableModel(EasyMock.<CsvTableModel> anyObject());
    view.showMessage(EasyMock.eq("Save File"), EasyMock.eq("File Saved"));
    EasyMock.replay(view);

    presenter.setView(view);
    view.setPresenter(presenter);

    presenter.onOpen();
    presenter.onSave();
    EasyMock.verify(view);
  }

  @Test
  public void testSaveWithNoFile() throws IOException {
    final Presenter presenter = new Domain();
    final View view = EasyMock.createNiceMock(View.class);

    view.showWarning(EasyMock.eq("Save File"), EasyMock.eq("No file is opened"));
    EasyMock.replay(view);

    presenter.setView(view);
    view.setPresenter(presenter);

    presenter.onSave();
    EasyMock.verify(view);
  }
}
