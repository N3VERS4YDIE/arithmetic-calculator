package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import calculator.Calculator;

/**
 *
 * @author Santiago Palacio Vásquez
 */
class Main {

  public static void main(String[] args) {
    setLookAndFeel();
    Calculator.setVisible(true);
  }

  private static void setLookAndFeel() {
    try {
      FlatMacDarkLaf.setup();
    } catch (Exception exFlatLaf) {
      try {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
          if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
          }
        }
      } catch (
          ClassNotFoundException
          | IllegalAccessException
          | InstantiationException
          | UnsupportedLookAndFeelException exNimbus) {
        System.err.println(exNimbus.getMessage());
      }
    }
  }
}
