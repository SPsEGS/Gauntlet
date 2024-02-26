import view.Editor;
import view.HallOfFlame;
import view.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain extends Application {
    public void start(Stage stage) {
        int sceneWidth = 700;
        int sceneHeight = 700;

        //Menu mypane = new Menu();
        HallOfFlame mypane = new HallOfFlame();
        Scene scene = new Scene(mypane, sceneWidth, sceneHeight);

        // FIXME: find the javafx way for styles
        scene.getStylesheets().add(getClass().getResource("/style/darkTheme.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
