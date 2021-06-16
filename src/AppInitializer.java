import controller.MainFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.AppBarIcon;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        MainFormController ctrl = fxmlLoader.getController();
        ctrl.navigate("Student Management System","/view/HomeForm.fxml", AppBarIcon.NAV_ICON_NONE);
        mainScene.setUserData(ctrl);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Student Management System");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
