import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by potap on 17.06.2017.
 */
public class Model
{
    static final int FIELD_SIZE = 10;
    static final int mines = 7;

    Cell[][] cells;
    ArrayList<MinerView> listeners = new ArrayList<MinerView>();

    void addListener(MinerView view)
    {
        listeners.add(view);
    }

    void openCell(int row, int column) throws NullPointerException
    {
            cells[column][row].state = "opened";

            if(cells[column][row].counter == 0)
            {
                for (int j = -1; j < 2; j++)
                {
                    for (int i = -1; i < 2; i++)
                    {
                        if(!( (i == 0) && (j == 0) ))
                        {
                            if (!outOfBounds(column + i, row + j))
                            {
                                if(cells[column + i][row + j].state != "opened")
                                {
                                    openCell(row + j, column + i);
                                }
                            }
                        }
                    }
                }
            }

            for (MinerView view : listeners)
            {
                view.update(cells[column][row]);
            }

            if (cells[column][row].mined == true)
            {
                for (MinerView view : listeners)
                {
                    view.gameOver();
                    return;
                }
            }

            if(isWon())
            {
                for (MinerView view : listeners)
                {
                    view.win();
                    return;
                }
            }
    }
    Cell getCell(int row, int column)
    {
        return cells[column][row];
    }

     public void generateBoard(int FIELD_SIZE, int mines)
     {
         cells = new Cell[FIELD_SIZE][FIELD_SIZE];
         for (int j = 0; j < FIELD_SIZE; j++)
         {
             for (int i = 0; i < FIELD_SIZE; i++)
             {
                 cells[i][j] = new Cell(j,i);
             }
         }

         ArrayList<Integer> list = new ArrayList<Integer>();
         for(int row = 0; row < FIELD_SIZE; row++)
         {
             for (int column = 0; column < FIELD_SIZE; column++)
             {
                 list.add(row*100 + column);
             }
         }

         for (int a = 0; a < mines; a++)
         {
             int choice = (int)(Math.random()*list.size());
             cells[list.get(choice) / 100][list.get(choice) % 100].mined = true;
             incrementCountAroundCell(list.get(choice) % 100, list.get(choice)/100 );
             list.remove(choice);
         }

         for(MinerView view: listeners)
         {
             view.startGame();
         }
     }

     private void incrementCountAroundCell(int row, int column)
     {
        for (int j = -1; j < 2; j++)
        {
            for (int i = -1; i < 2; i++)
            {
                if(!outOfBounds(column + i, row + j))
                {
                    if((i == 0) && (j == 0))
                    {
                        cells[column + i][row + j].counter = 10;
                        cells[column + i][row + j].mined = true;
                    }
                    else
                    {
                        cells[column + i][row + j].counter++;
                    }

                    for (MinerView view: listeners)
                    {
                        view.update(cells[column + i][row + j]);
                    }
                }
            }
        }
     }

     private boolean outOfBounds(int column,int row)
     {
         try
         {
             Cell cell = cells[column][row];
             return false;
         }
         catch (IndexOutOfBoundsException e)
         {
             return true;
         }

     }

     private boolean isWon()
     {
         for(int j = 0; j < cells.length; j++)
         {
             for(int i = 0; i < cells[0].length; i++)
             {
                 if((cells[i][j].state == "closed") && (cells[i][j].mined == false))
                 {
                     return false;
                 }
             }
         }
         return true;
     }
}
