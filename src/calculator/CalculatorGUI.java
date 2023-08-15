package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Santiago Palacio Vásquez
 */
class CalculatorGUI extends JFrame implements ActionListener {
  private static final String ERROR_MSG = "...";

  protected CalculatorGUI() {
    initComponents();
    addListeners();
    setSize(getPreferredSize());
    setLocationRelativeTo(null);
    txtExpression.setText("");
    txtResult.setText("0");
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    txtExpression.select(txtExpression.getSelectionStart(), txtExpression.getSelectionEnd());
    txtExpression.replaceSelection(((JButton) evt.getSource()).getText());
    solveExpression();
  }

  private void addListeners() {
    final JButton[] buttons = { btnN0, btnN1, btnN2, btnN3, btnN4, btnN5, btnN6, btnN7, btnN8, btnN9, btnDot, btnAdd,
        btnSubtract, btnMultiply, btnDivide, btnMod, btnPow, };

    for (final JButton button : buttons) {
      button.addActionListener(this);
    }

    txtExpression.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent evt) {
        txtExpressionKeyTyped(evt);
      }

      @Override
      public void keyReleased(KeyEvent evt) {
        txtExpressionKeyReleased(evt);
      }

    });
    btnClear.addActionListener(this::btnClearActionPerformed);
    btnClearAll.addActionListener(this::btnClearAllActionPerformed);
    btnEqual.addActionListener(this::btnEqualActionPerformed);
  }

  private void txtExpressionKeyTyped(KeyEvent evt) {
    final char KEY_CHAR = evt.getKeyChar();
    final boolean IS_BACKSPACE_OR_DELETE = KEY_CHAR == KeyEvent.VK_BACK_SPACE || KEY_CHAR == KeyEvent.VK_DELETE;

    if (KEY_CHAR == KeyEvent.VK_ENTER) {
      btnEqualActionPerformed(null);
    } else if (KEY_CHAR == KeyEvent.VK_ESCAPE) {
      txtExpression.setText("");
      txtResult.setText("0");
    } else if (!isNumberOrOperator(KEY_CHAR) && !IS_BACKSPACE_OR_DELETE) {
      evt.consume();
    }
  }

  private void txtExpressionKeyReleased(KeyEvent evt) {
    if (!isNumberOrOperator(evt.getKeyChar()) && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
      return;
    }
    if (txtExpression.getText().length() == 0) {
      txtResult.setText("0");
    }

    solveExpression();
  }

  private void btnClearActionPerformed(ActionEvent evt) {
    final String EXPRESSION = txtExpression.getText();

    if (EXPRESSION.length() > 0) {
      txtExpression.select(txtExpression.getSelectionStart() - 1, txtExpression.getSelectionEnd());
      txtExpression.replaceSelection("");
    }
    if (EXPRESSION.length() == 1) {
      txtResult.setText("0");
    }

    solveExpression();
  }

  private void btnClearAllActionPerformed(ActionEvent evt) {
    txtExpression.setText("");
    txtResult.setText("0");
  }

  private void btnEqualActionPerformed(ActionEvent evt) {
    solveExpression();

    if (!txtResult.getText().equalsIgnoreCase(ERROR_MSG)) {
      txtExpression.setText(txtResult.getText());
    }
  }

  private void solveExpression() {
    if (txtExpression.getText().isBlank()) {
      return;
    }

    final int SELECTION_START = txtExpression.getSelectionStart();
    final int SELECTION_END = txtExpression.getSelectionEnd();
    final int LENGTH = txtExpression.getText().length();
    final String REDUCED_EXPRESSION = multiplyAdditionSigns(txtExpression.getText());

    try {
      final String RESULT = formatDecimals(Calculator.solve(REDUCED_EXPRESSION));
      txtResult.setText(RESULT);

      final int DELTA_LENGTH = LENGTH - txtExpression.getText().length();
      txtExpression.setSelectionStart(SELECTION_START - DELTA_LENGTH);
      txtExpression.setSelectionEnd(SELECTION_END - DELTA_LENGTH);
    } catch (NumberFormatException ex) {
      txtResult.setText(ERROR_MSG);
    }
  }

  private String formatDecimals(Double number) {
    String strNumber = "";

    try {
      strNumber = BigDecimal.valueOf(number).stripTrailingZeros().toPlainString();
    } catch (Exception ex) {
      strNumber = ERROR_MSG;
    }

    return strNumber;
  }

  private boolean isNumberOrOperator(char keyChar) {
    for (final char CHAR : Calculator.HANDLED_CHARS) {
      if (CHAR == keyChar) {
        return true;
      }
    }

    return false;
  }

  /**
   * Reduces consecutive '+' and '-' signs in an expression by multiplying them
   * and replacing them with the resulting sign.
   * 
   * @param expression Mathematical expression.
   * @return A reduced expression.
   */
  private String multiplyAdditionSigns(String expression) {
    final List<List<Character>> signGroups = new ArrayList<>();
    final int LENGTH = expression.length();
    int index = 0;

    while (index < LENGTH) {
      final char CURRENT_CHAR = expression.charAt(index);

      if (CURRENT_CHAR != '+' && CURRENT_CHAR != '-') {
        index++;
        continue;
      }

      final List<Character> signGroup = new ArrayList<>();
      while (index < LENGTH && (expression.charAt(index) == '+' || expression.charAt(index) == '-')) {
        signGroup.add(expression.charAt(index++));
      }
      signGroups.add(signGroup);
      index++;
    }

    final StringBuilder result = new StringBuilder(expression);
    for (final List<Character> SIGN_GROUP : signGroups) {
      byte multiplicationFactor = 1;

      for (final Character SIGN : SIGN_GROUP) {
        if (SIGN == '-') {
          multiplicationFactor *= -1;
        }
      }

      if (!SIGN_GROUP.isEmpty()) {
        final StringBuilder signSB = new StringBuilder();
        for (final Character SIGN : SIGN_GROUP) {
          signSB.append(SIGN);
        }
        final int START_INDEX = result.indexOf(signSB.toString());
        final int END_INDEX = START_INDEX + SIGN_GROUP.size();
        result.replace(START_INDEX, END_INDEX, multiplicationFactor > 0 ? "+" : "-");
      }
    }

    return result.toString();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // <editor-fold defaultstate="collapsed" desc="Generated
  // Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    pnlDisplay = new javax.swing.JPanel();
    txtExpression = new javax.swing.JTextField();
    sprLine = new javax.swing.JSeparator();
    txtResult = new javax.swing.JTextField();
    pnlButtons = new javax.swing.JPanel();
    btnN7 = new javax.swing.JButton();
    btnN8 = new javax.swing.JButton();
    btnN9 = new javax.swing.JButton();
    btnClear = new javax.swing.JButton();
    btnClearAll = new javax.swing.JButton();
    btnN4 = new javax.swing.JButton();
    btnN5 = new javax.swing.JButton();
    btnN6 = new javax.swing.JButton();
    btnAdd = new javax.swing.JButton();
    btnSubtract = new javax.swing.JButton();
    btnN1 = new javax.swing.JButton();
    btnN2 = new javax.swing.JButton();
    btnN3 = new javax.swing.JButton();
    btnMultiply = new javax.swing.JButton();
    btnDivide = new javax.swing.JButton();
    btnN0 = new javax.swing.JButton();
    btnDot = new javax.swing.JButton();
    btnMod = new javax.swing.JButton();
    btnPow = new javax.swing.JButton();
    btnEqual = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Arithmetic Calculator");
    setBackground(new java.awt.Color(51, 51, 51));
    setMinimumSize(new java.awt.Dimension(460, 400));

    pnlDisplay.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    pnlDisplay.setMinimumSize(new java.awt.Dimension(31, 96));

    txtExpression.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    txtExpression.setForeground(new java.awt.Color(255, 255, 255));
    txtExpression.setBorder(null);

    txtResult.setEditable(false);
    txtResult.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    txtResult.setForeground(new java.awt.Color(0, 255, 0));
    txtResult.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    txtResult.setBorder(null);

    javax.swing.GroupLayout pnlDisplayLayout = new javax.swing.GroupLayout(pnlDisplay);
    pnlDisplay.setLayout(pnlDisplayLayout);
    pnlDisplayLayout.setHorizontalGroup(
        pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtExpression, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sprLine)
                    .addComponent(txtResult))
                .addContainerGap()));
    pnlDisplayLayout.setVerticalGroup(
        pnlDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtExpression, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtResult, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sprLine, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap()));

    pnlButtons.setLayout(new java.awt.GridLayout(4, 5, 10, 10));

    btnN7.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN7.setForeground(new java.awt.Color(255, 255, 255));
    btnN7.setText("7");
    btnN7.setFocusable(false);
    pnlButtons.add(btnN7);

    btnN8.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN8.setForeground(new java.awt.Color(255, 255, 255));
    btnN8.setText("8");
    btnN8.setFocusable(false);
    pnlButtons.add(btnN8);

    btnN9.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN9.setForeground(new java.awt.Color(255, 255, 255));
    btnN9.setText("9");
    btnN9.setFocusable(false);
    pnlButtons.add(btnN9);

    btnClear.setBackground(new java.awt.Color(0, 204, 102));
    btnClear.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnClear.setForeground(new java.awt.Color(255, 255, 255));
    btnClear.setText("C");
    btnClear.setFocusable(false);
    pnlButtons.add(btnClear);

    btnClearAll.setBackground(new java.awt.Color(0, 204, 102));
    btnClearAll.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnClearAll.setForeground(new java.awt.Color(255, 255, 255));
    btnClearAll.setText("CA");
    btnClearAll.setFocusable(false);
    pnlButtons.add(btnClearAll);

    btnN4.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN4.setForeground(new java.awt.Color(255, 255, 255));
    btnN4.setText("4");
    btnN4.setFocusable(false);
    pnlButtons.add(btnN4);

    btnN5.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN5.setForeground(new java.awt.Color(255, 255, 255));
    btnN5.setText("5");
    btnN5.setFocusable(false);
    pnlButtons.add(btnN5);

    btnN6.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN6.setForeground(new java.awt.Color(255, 255, 255));
    btnN6.setText("6");
    btnN6.setFocusable(false);
    pnlButtons.add(btnN6);

    btnAdd.setBackground(new java.awt.Color(57, 59, 61));
    btnAdd.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnAdd.setForeground(new java.awt.Color(255, 255, 255));
    btnAdd.setText("+");
    btnAdd.setFocusable(false);
    pnlButtons.add(btnAdd);

    btnSubtract.setBackground(new java.awt.Color(57, 59, 61));
    btnSubtract.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnSubtract.setForeground(new java.awt.Color(255, 255, 255));
    btnSubtract.setText("-");
    btnSubtract.setFocusable(false);
    pnlButtons.add(btnSubtract);

    btnN1.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN1.setForeground(new java.awt.Color(255, 255, 255));
    btnN1.setText("1");
    btnN1.setFocusable(false);
    pnlButtons.add(btnN1);

    btnN2.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN2.setForeground(new java.awt.Color(255, 255, 255));
    btnN2.setText("2");
    btnN2.setFocusable(false);
    pnlButtons.add(btnN2);

    btnN3.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN3.setForeground(new java.awt.Color(255, 255, 255));
    btnN3.setText("3");
    btnN3.setFocusable(false);
    pnlButtons.add(btnN3);

    btnMultiply.setBackground(new java.awt.Color(57, 59, 61));
    btnMultiply.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnMultiply.setForeground(new java.awt.Color(255, 255, 255));
    btnMultiply.setText("*");
    btnMultiply.setFocusable(false);
    pnlButtons.add(btnMultiply);

    btnDivide.setBackground(new java.awt.Color(57, 59, 61));
    btnDivide.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnDivide.setForeground(new java.awt.Color(255, 255, 255));
    btnDivide.setText("/");
    btnDivide.setFocusable(false);
    pnlButtons.add(btnDivide);

    btnN0.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnN0.setForeground(new java.awt.Color(255, 255, 255));
    btnN0.setText("0");
    btnN0.setFocusable(false);
    pnlButtons.add(btnN0);

    btnDot.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnDot.setForeground(new java.awt.Color(255, 255, 255));
    btnDot.setText(".");
    btnDot.setFocusable(false);
    pnlButtons.add(btnDot);

    btnMod.setBackground(new java.awt.Color(57, 59, 61));
    btnMod.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnMod.setForeground(new java.awt.Color(255, 255, 255));
    btnMod.setText("%");
    btnMod.setFocusable(false);
    pnlButtons.add(btnMod);

    btnPow.setBackground(new java.awt.Color(57, 59, 61));
    btnPow.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnPow.setForeground(new java.awt.Color(255, 255, 255));
    btnPow.setText("^");
    btnPow.setFocusable(false);
    pnlButtons.add(btnPow);

    btnEqual.setBackground(new java.awt.Color(0, 204, 102));
    btnEqual.setFont(new java.awt.Font("Cascadia Code", 1, 30)); // NOI18N
    btnEqual.setForeground(new java.awt.Color(255, 255, 255));
    btnEqual.setText("=");
    btnEqual.setFocusable(false);
    pnlButtons.add(btnEqual);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDisplay, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap()));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap()));
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnAdd;
  private javax.swing.JButton btnClear;
  private javax.swing.JButton btnClearAll;
  private javax.swing.JButton btnDivide;
  private javax.swing.JButton btnDot;
  private javax.swing.JButton btnEqual;
  private javax.swing.JButton btnMod;
  private javax.swing.JButton btnMultiply;
  private javax.swing.JButton btnN0;
  private javax.swing.JButton btnN1;
  private javax.swing.JButton btnN2;
  private javax.swing.JButton btnN3;
  private javax.swing.JButton btnN4;
  private javax.swing.JButton btnN5;
  private javax.swing.JButton btnN6;
  private javax.swing.JButton btnN7;
  private javax.swing.JButton btnN8;
  private javax.swing.JButton btnN9;
  private javax.swing.JButton btnPow;
  private javax.swing.JButton btnSubtract;
  private javax.swing.JPanel pnlButtons;
  private javax.swing.JPanel pnlDisplay;
  private javax.swing.JSeparator sprLine;
  private javax.swing.JTextField txtExpression;
  private javax.swing.JTextField txtResult;
  // End of variables declaration//GEN-END:variables
}
