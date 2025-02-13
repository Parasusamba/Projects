import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class ScientificCalculator {
    private JFrame frame;
    private JTextField display;
    private String currentInput = "";
    private ArrayList<String> history = new ArrayList<>();
    private HistoryWindow historyWindow;

    public ScientificCalculator() {
        frame = new JFrame("Scientific Calculator");
        frame.setSize(500, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
         
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.lightGray);
        display.setForeground(Color.BLACK);
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setForeground(Color.CYAN);
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            ".", "0", "=", "+",
            "sin", "cos", "tan", "log",
            "^","sqrt", "AC", "History"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(e -> onButtonClick(e.getActionCommand()));
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        historyWindow = new HistoryWindow();
    }

    private void onButtonClick(String command) {
        try {
            switch (command) {
                case "=":
                    evaluateExpression();
                    break;
                case "AC":
                    currentInput = "";
                    display.setText("");
                    break;
                case "sin":
                case "cos":
                case "tan":
                case "log":
                case "sqrt":
                    evaluateScientificFunction(command);
                    break;
                case "History":
                    historyWindow.showHistory(history);
                    break;
                default:
                    currentInput += command;
                    display.setText(currentInput);
            }
        } catch (Exception ex) {
            display.setText("Error");
            currentInput = "";
        }
    }

    private void evaluateExpression() {
        try {
            double result = evaluate(currentInput);
            history.add(currentInput + " = " + result);
            display.setText(String.valueOf(result));
            currentInput = "";
        } catch (Exception e) {
            display.setText("Error");
            currentInput = "";
        }
    }

    private void evaluateScientificFunction(String function) {
        try {
            double value = Double.parseDouble(currentInput);
            double result = switch (function) {
                case "sin" -> Math.sin(Math.toRadians(value));
                case "cos" -> Math.cos(Math.toRadians(value));
                case "tan" -> Math.tan(Math.toRadians(value));
                case "log" -> Math.log10(value);
                case "sqrt" -> Math.sqrt(value);
                default -> 0;
            };
            history.add(function + "(" + value + ") = " + result);
            display.setText(String.valueOf(result));
            currentInput = "";
        } catch (Exception e) {
            display.setText("Error");
            currentInput = "";
        }
    }

    private double evaluate(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    private ArrayList<String> infixToPostfix(String expression) {
        ArrayList<String> output = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder numBuffer = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                numBuffer.append(c);
            } else {
                if (numBuffer.length() > 0) {
                    output.add(numBuffer.toString());
                    numBuffer.setLength(0);
                }
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.add(String.valueOf(stack.pop()));
                }
                stack.push(c);
            }
        }
        if (numBuffer.length() > 0) {
            output.add(numBuffer.toString());
        }
        while (!stack.isEmpty()) {
            output.add(String.valueOf(stack.pop()));
        }
        return output;
    }

    private double evaluatePostfix(ArrayList<String> postfix) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> stack.push(a / b);
                    case "^" -> stack.push(Math.pow(a, b));
                }
            }
        }
        return stack.pop();
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' ->  3;
            default -> 0;
        };
    }


    class HistoryWindow {
        private JFrame historyFrame;
        private JTextArea historyArea;
        private JButton clearButton;

        public HistoryWindow() {
            historyFrame = new JFrame("Calculation History");
            historyFrame.setSize(300, 400);
            historyFrame.setLayout(new BorderLayout());
            historyArea = new JTextArea();
            historyArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(historyArea);
            historyFrame.add(scrollPane, BorderLayout.CENTER);

            clearButton = new JButton("Clear History");
            clearButton.addActionListener(e -> clearHistory());
            historyFrame.add(clearButton, BorderLayout.SOUTH);
        }

        public void showHistory(ArrayList<String> history) {
            historyArea.setText(String.join("\n", history));
            historyFrame.setVisible(true);
        }

        private void clearHistory() {
            history.clear();
            historyArea.setText("");
        }
    }

    public static void main(String[] args) {
        new ScientificCalculator();
    }
}
