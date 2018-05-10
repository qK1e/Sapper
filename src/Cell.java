/**
 * Created by potap on 17.06.2017.
 */
public class Cell
{
    int row, column;
    String state;
    boolean mined;
    int counter;

    Cell()
    {
        mined = false;
        counter = 0;
        state = "closed";
    }

    Cell(int row, int column)
    {
        mined = false;
        counter = 0;
        state = "closed";
        this.row = row;
        this.column = column;
    }

    void nextMark()
    {}
    void open()
    {
        state = "opened";
    }
}
