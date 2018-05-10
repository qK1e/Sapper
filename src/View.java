import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by potap on 17.06.2017.
 */
public class View extends JFrame implements MinerView, ActionListener {
    Cell[][] cells = new Cell[Model.FIELD_SIZE][Model.FIELD_SIZE];
    JButton[][] buttons = new JButton[Model.FIELD_SIZE][Model.FIELD_SIZE];
    Controller controller;
    GridLayout grid = new GridLayout(Model.FIELD_SIZE, Model.FIELD_SIZE);

    View(Model model) {
        controller = new Controller(model);

        setTitle("Сапёр");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);

        setResizable(true);

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.white);
        add(BorderLayout.CENTER, canvas);
        canvas.setLayout(grid);

        for (int j = 0; j < Model.FIELD_SIZE; j++) {
            for (int i = 0; i < Model.FIELD_SIZE; i++) {
                buttons[i][j] = new JButton();
                cells[i][j] = new Cell(j, i);
                buttons[i][j].addActionListener(this);
                canvas.add(buttons[i][j]);
            }
        }

        JMenuBar jmb = new JMenuBar();
        JMenu jmGame = new JMenu("Игра");
        JMenuItem jmiNewGame = new JMenuItem("Новая игра");
        jmiNewGame.addActionListener(this);
        jmGame.add(jmiNewGame);
        jmb.add(jmGame);
        this.setJMenuBar(jmb);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.getJMenuBar().getMenu(0).getItem(0))) {
            controller.newGame(Model.FIELD_SIZE, Model.mines);
        }
        for (int j = 0; j < buttons[0].length; j++) {
            for (int i = 0; i < buttons.length; i++) {
                if (e.getSource().equals(buttons[i][j]))
                {
                    try
                    {
                        controller.openCell(j, i);
                    }
                    catch (NullPointerException ы)
                    {
                        JOptionPane.showMessageDialog(this, "Игра не запущена");
                    }

                    if (cells[i][j].mined) {
                        buttons[i][j].setForeground(Color.red);
                        buttons[i][j].setText("*");

                    } else {
                        buttons[i][j].setText(cells[i][j].counter + "");
                    }
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    public void update(Cell cell) {
        cells[cell.column][cell.row] = cell;
        if (cell.state == "opened") {
            if (cell.mined == true) {
                buttons[cell.column][cell.row].setText("*");
            } else {
                buttons[cell.column][cell.row].setText(cell.counter + "");

                if (cells[cell.column][cell.row].state == "opened") {
                    buttons[cell.column][cell.row].setEnabled(false);
                }
            }
        }
    }

    @Override
    public void gameOver() {
        this.setTitle("Игра окончена");

        for (int j = 0; j < buttons.length; j++) {
            for (int i = 0; i < buttons[0].length; i++) {
                if (cells[i][j].mined) {
                    buttons[i][j].setBackground(Color.red);
                    buttons[i][j].setText("*");
                }
                buttons[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, "Игра окончена");
    }

    @Override
    public void startGame() {
        this.setTitle("Игра идёт");
        for (int j = 0; j < buttons.length; j++) {
            for (int i = 0; i < buttons[0].length; i++) {
                buttons[i][j].setEnabled(true);
                buttons[i][j].setText("");
                buttons[i][j].setBackground((new JButton()).getBackground());
                buttons[i][j].setDisabledIcon((new JButton()).getDisabledIcon());
            }
        }
    }

    @Override
    public void win()
    {
        this.setTitle("Сапёр");
        for (int j = 0; j < buttons.length; j++) {
            for (int i = 0; i < buttons[0].length; i++) {
                buttons[i][j].setEnabled(false);
                if(cells[i][j].mined)
                {
                    buttons[i][j].setText("*");
                    buttons[i][j].setBackground(Color.green);
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Ты победил(а)!");
    }
}
