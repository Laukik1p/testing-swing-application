package com.javacreed.examples.test;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(final String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        final Domain domain = new Domain();
        final Application application = new Application();

        domain.setView(application);
        application.setPresenter(domain);

        application.setTitle("CSV Editor");
        try {
          application.setIconImage(ImageIO.read(Main.class.getResource("/icons/application.png")));
        } catch (final IOException e) {}
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
      }
    });
  }
}
