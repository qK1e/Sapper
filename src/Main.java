/**
 * Created by potap on 17.06.2017.
 */
public class Main {
    public static void main(String[] args)
    {
        Model model = new Model();
        View view = new View(model);

        model.addListener(view);
    }
}
