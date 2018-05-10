import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by potap on 17.06.2017.
 */
public class Controller
{
    Model model;

    Controller(Model model)
    {
        this.model = model;
    }
    void takeModel(Model model)
    {
        this.model = model;
    }

    void openCell(int row, int column) throws NullPointerException
    {
        model.openCell(row, column);
    }
    void newGame(int FIELD_SIZE, int mines)
    {
        model.generateBoard(FIELD_SIZE, mines);
    }
}
