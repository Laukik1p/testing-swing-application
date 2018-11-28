package com.javacreed.examples.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

public class Application extends JFrame implements View {

  private static final long serialVersionUID = -7343389333426996183L;

  private static AbstractAction createAction(final String name, final String icon, final ActionListener actionListener) {
    return new AbstractAction(name, new ImageIcon(Application.class.getResource(icon))) {
      private static final long serialVersionUID = -8733978494447301385L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        actionListener.actionPerformed(e);
      }
    };
  }

  private Presenter presenter;

  private Action openAction;
  private Action saveAction;
  private Action reloadAction;

  private Action addRowAction;
  private Action addColumnAction;
  private Action deleteRowAction;
  private Action deleteColumnAction;

  private JTable editorTable;

  public Application() {
    initActions();
    initMenu();
    initToolbar();
    initComponents();
  }

  @Override
  public String getNewHeaderName(final String currentColumnName) {
    return JOptionPane.showInputDialog("Column Name:", currentColumnName);
  }

  private Presenter getPresenter() {
    if (presenter == null) {
      throw new IllegalStateException("The presenter is not set");
    }
    return presenter;
  }

  private void initActions() {
    openAction = Application.createAction("Open", "/icons/open.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onOpen();
      }
    });

    saveAction = Application.createAction("Save", "/icons/save.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onSave();
      }
    });

    reloadAction = Application.createAction("Reload File", "/icons/reload.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onReload();
      }
    });

    addRowAction = Application.createAction("New Row", "/icons/add_row.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onAddRow();
      }
    });

    addColumnAction = Application.createAction("New Column", "/icons/add_column.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onAddColumn();
      }
    });

    deleteRowAction = Application.createAction("Delete Row", "/icons/delete_row.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onDeleteRow();
      }
    });

    deleteColumnAction = Application.createAction("Delete Column", "/icons/delete_column.png", new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        getPresenter().onDeleteColumn();
      }
    });
  }

  private void initComponents() {
    editorTable = new JTable();
    editorTable.setFont(editorTable.getFont().deriveFont(14F));
    // editorTable.setDefaultRenderer(String.class, new CsvTableCellRenderer());
    add(new JScrollPane(editorTable), BorderLayout.CENTER);
  }

  private void initMenu() {
    final JMenuBar menubar = new JMenuBar();
    setJMenuBar(menubar);

    final JMenu file = menubar.add(new JMenu("File"));
    file.add(openAction);
    file.add(saveAction);
    file.addSeparator();
    file.add(reloadAction);

    final JMenu edit = menubar.add(new JMenu("Edit"));
    edit.add(addRowAction);
    edit.add(addColumnAction);
    edit.addSeparator();
    edit.add(deleteRowAction);
    edit.add(deleteColumnAction);
  }

  private void initToolbar() {

    final JToolBar toolBar = new JToolBar();
    add(toolBar, BorderLayout.PAGE_START);

    toolBar.add(openAction);
    toolBar.add(saveAction);
    toolBar.add(reloadAction);

    toolBar.addSeparator();

    toolBar.add(addRowAction);
    toolBar.add(addColumnAction);
    toolBar.add(deleteRowAction);
    toolBar.add(deleteColumnAction);
  }

  @Override
  public void setPresenter(final Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void showMessage(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public File showOpenFileChoser(final File selectedFile) {
    final JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(selectedFile);
    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile();
    }

    return null;
  }

  @Override
  public void showWarning(final String title, final String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void useCsvTableModel(final CsvTableModel tableModel) {
    editorTable.setModel(tableModel);
  }

}
