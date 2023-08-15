package calculator;

/**
 *
 * @author Santiago Palacio Vásquez
 */
public class Calculator {
    protected static final char[] HANDLED_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '+', '-',
            '*', '/', '%', '^', };

    private static final char[] OPERATORS = { '+', '-', '*', '/', '%', '^' };
    private static CalculatorGUI gui;

    private Calculator() {
    }

    public static void setVisible(boolean visible) {
        if (gui == null) {
            gui = new CalculatorGUI();
        }
        gui.setVisible(visible);
    }

    /**
     * Takes a mathematical expression as input and recursively evaluates it,
     * returning the result as a double.
     * 
     * @param expression Mathematical expression. It can only contain numbers and
     *                   arithmetic operators: <b>+, -, *, /, %, ^</b>.
     * @return Result of evaluating the given expression.
     */
    public static double solve(String expression) throws NumberFormatException {
        for (final char OPERATOR : OPERATORS) {
            final int SIGN_INDEX = expression.lastIndexOf(OPERATOR);
            final boolean IS_PLUS_SIGN = OPERATOR == '+' && SIGN_INDEX == 0;
            final boolean IS_MINUS_SIGN = OPERATOR == '-' && SIGN_INDEX == 0
                    || SIGN_INDEX > 0 && !Character.isDigit(expression.charAt(SIGN_INDEX - 1));

            if (SIGN_INDEX == -1 || IS_MINUS_SIGN || IS_PLUS_SIGN) {
                continue;
            }

            final double LEFT = solve(expression.substring(0, SIGN_INDEX));
            final double RIGHT = solve(expression.substring(SIGN_INDEX + 1));

            switch (OPERATOR) {
                case '+':
                    return LEFT + RIGHT;
                case '-':
                    return LEFT - RIGHT;
                case '*':
                    return LEFT * RIGHT;
                case '/':
                    return LEFT / RIGHT;
                case '%':
                    return LEFT % RIGHT;
                case '^':
                    return Math.pow(LEFT, RIGHT);
            }
        }
        return Double.parseDouble(expression);
    }
}
