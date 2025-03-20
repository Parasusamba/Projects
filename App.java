import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class TicTacToe extends JFrame implements ActionListener {
  private JPanel panel,set;
  private char currentPlayer = 'X';
  private JButton[][] button = new JButton[3][3];
  private JButton reset, exit;
  private JTextField score;
  private int Xscore=0, Oscore=0;
  
  public TicTacToe() {
    this.setSize(600,600);
    this.setResizable(false);
    this.setTitle("Tic-Tac-Toe");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    panel = new JPanel();
    panel.setLayout(new GridLayout(3,3,0,0));
    Border border = BorderFactory.createLineBorder(Color.BLACK,5);
    for(int row=0;row<3;row++) {
      for(int col=0;col<3;col++) {
        button[row][col] = new JButton("");
        button[row][col].setBackground(new Color(120,200,200));
        button[row][col].setForeground(Color.BLACK);
        button[row][col].setCursor(new Cursor(1));
        button[row][col].setBorder(border);
        button[row][col].setFont(new Font("Arial", Font.BOLD, 50));
        button[row][col].setFocusable(false);
        button[row][col].addActionListener(this);
        panel.add(button[row][col]);
      }
    }
    score = new JTextField("\t"+"X-score : "+Xscore+"\t\t"+"O-score : "+Oscore);
    score.setFont(new Font("Arial",Font.ROMAN_BASELINE,15));
    score.setEditable(false);
    score.setFocusable(false);
   
    set = new JPanel();
    set.setBackground(Color.BLACK);
    set.setLayout(new GridLayout(1,2,3,0));
    reset = new JButton("Reset");
    reset.setFont(new Font("Arial",Font.BOLD,15));
    reset.setFocusable(false);
    reset.setBackground(new Color(200,200,200));
    reset.addActionListener(e -> resetBoard());
    exit = new JButton("Exit");
    exit.setFont(new Font("Arial",Font.BOLD,15));
    exit.setFocusable(false);
    exit.setBackground(new Color(200,200,200));
    exit.addActionListener(e -> this.dispose());
    set.add(reset);
    set.add(exit);
    this.add(set,BorderLayout.NORTH);
    this.add(panel,BorderLayout.CENTER);
    this.add(score,BorderLayout.SOUTH);
    this.setVisible(true);
  }
   
  public void actionPerformed(ActionEvent e) {
    JButton b = (JButton)e.getSource();
    if(!b.getText().equals("")) return;
    b.setText(String.valueOf(currentPlayer));
    if(checkWin()) {
      JOptionPane.showMessageDialog(this,"Player "+currentPlayer+" wins!");
      if(currentPlayer=='X') Xscore++;
      if(currentPlayer=='O') Oscore++;
      score.setText("\t"+"X-score : "+Xscore+"\t\t"+"O-score : "+Oscore);
      resetBoard();
      currentPlayer = 'X';
      return;    
    }

    if(checkDraw()) {
      JOptionPane.showMessageDialog(this,"It's a draw!");
      resetBoard();
      currentPlayer = 'X';
      return;    
    }
    currentPlayer = (currentPlayer=='X')? 'O' : 'X';
  }

  private boolean checkWin() {
    for (int i = 0; i < 3; i++) {
      if (checkRowCol(button[i][0], button[i][1], button[i][2])) return true;
      if (checkRowCol(button[0][i], button[1][i], button[2][i])) return true;
    }
    return checkRowCol(button[0][0], button[1][1], button[2][2]) ||
          checkRowCol(button[0][2], button[1][1], button[2][0]);
  }
   
  private boolean checkRowCol(JButton b1, JButton b2, JButton b3) {
      return !b1.getText().equals("") && b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText());
  }
  public boolean checkDraw() {
    for(JButton[] row:button) {
      for(JButton bt:row) {
        if(bt.getText().equals("")) return false;
      }
    }
    return true;
  }
   
  public void resetBoard() {
    for(JButton[] row:button) {
      for(JButton bt:row) {
        bt.setText("");
      }
    }
  }

     public static void main(String[] args) {
       //SwingUtilities.invokeLater(testTest::new);
      new TicTacToe();
    }
}   
