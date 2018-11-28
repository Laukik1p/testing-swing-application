package com.javacreed.examples.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final ApplicationPresenter domain = new ApplicationPresenter();
        final ApplicationView application = new ApplicationView();

        domain.setView(application);
        application.setPresenter(domain);

        application.setTitle("Test Swing Application");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
      }
    });
  }
}
