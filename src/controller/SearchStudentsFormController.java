package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Student;
import model.StudentTM;
import service.StudentService;
import service.exception.NotFoundException;
import util.AppBarIcon;
import util.MaterialUI;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SearchStudentsFormController {

    public TextField txtQuery;
    public TableView<StudentTM> tblResults;

    private StudentService studentService = new StudentService();

    public void initialize(){
        MaterialUI.paintTextFields(txtQuery);

        tblResults.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblResults.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tblResults.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<StudentTM, HBox> lastCol = (TableColumn<StudentTM, HBox>) tblResults.getColumns().get(3);

        lastCol.setCellValueFactory(param -> {
            ImageView imgEdit = new ImageView("/view/assets/Edit.png");
            ImageView imgTrash = new ImageView("/view/assets/Trash.png");

            imgEdit.getStyleClass().add("action-icons");
            imgTrash.getStyleClass().add("action-icons");

            imgEdit.setOnMouseClicked(event -> updateStudent(param.getValue()));
            imgTrash.setOnMouseClicked(event -> deleteStudent(param.getValue()));

            return new ReadOnlyObjectWrapper<>(new HBox(10, imgEdit, imgTrash));
        });

        txtQuery.textProperty().addListener((observable, oldValue, newValue) -> loadAllStudents(newValue));

        loadAllStudents("");
    }

    private void deleteStudent(StudentTM tm){
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this student?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                studentService.deleteStudent(tm.getNic());
                tblResults.getItems().remove(tm);
            }
        }catch (NotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something terribly went wrong, please contact DEPPO!", ButtonType.OK).show();
        }
    }

    private void updateStudent(StudentTM tm){
        try {
            Stage secondaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/MainForm.fxml"));
            Scene secondaryScene = new Scene(loader.load());
            MainFormController ctrl = loader.getController();

            secondaryStage.setScene(secondaryScene);
            secondaryScene.setFill(Color.TRANSPARENT);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initOwner(txtQuery.getScene().getWindow());
            secondaryStage.setTitle("Update Student");
            ctrl.navigate("Update Student","/view/StudentForm.fxml", AppBarIcon.NAV_ICON_NONE, null, tm);

            secondaryStage.showAndWait();
            tblResults.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudents(String query){
        tblResults.getItems().clear();

        for (Student student : studentService.findStudents(query)) {
            tblResults.getItems().add(new StudentTM(student.getNic(), student.getFullName(), student.getAddress()));
        }
    }

    public void tblResults_OnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DELETE){
            deleteStudent(tblResults.getSelectionModel().getSelectedItem());
        }else if (keyEvent.getCode() == KeyCode.ENTER){
            updateStudent(tblResults.getSelectionModel().getSelectedItem());
        }
    }
}