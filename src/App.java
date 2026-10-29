import views.MainView;
// import controllers.MainController;

public class App {
    public static void main(String[] args) throws Exception {
        var view = new MainView();
        // new MainController(view);
        view.setVisible(true);
    }
}
