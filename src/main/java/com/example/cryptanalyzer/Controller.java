package com.example.cryptanalyzer;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Controller {

    @FXML
    private TextField filePathTextField;

    @FXML
    private TextField referenceFilePathTextField;

    @FXML
    private ComboBox<String> modeComboBox;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField keyTextField;

    @FXML
    private Label referenceFileLabel;

    @FXML
    private HBox referenceFileBox;

    @FXML
    private Button executeButton;

    @FXML
    private VBox mainContainer;
    @FXML
    public void initialize() {
        mainContainer.setPadding(new Insets(20, 20, 20, 20));
    }
    @FXML
    private void handleBrowseAction(ActionEvent event) {
        selectFile(filePathTextField);
    }

    @FXML
    private void handleBrowseReferenceAction(ActionEvent event) {
        selectFile(referenceFilePathTextField);
    }

    private void selectFile(TextField textField) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("�������� ����");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("��������� �����", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(textField.getScene().getWindow());
        if (selectedFile != null) {
            textField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleClearAction(ActionEvent event) {
        filePathTextField.clear();
        outputTextArea.clear();
        keyTextField.clear();
        modeComboBox.getSelectionModel().clearSelection();
        referenceFilePathTextField.clear();
    }

    @FXML
    private void handleModeChange(ActionEvent event) {
        String mode = modeComboBox.getSelectionModel().getSelectedItem();
        switch (mode) {
            case "�������������� ������":
                referenceFileLabel.setVisible(true);
                referenceFileBox.setVisible(true);
                break;
            default:
                referenceFileLabel.setVisible(false);
                referenceFileBox.setVisible(false);
                break;
        }
    }

    @FXML
    public void handleExecuteAction(ActionEvent event) {
        animateButtonPress(executeButton);
        try {
            progressBar.setVisible(true);
            String mode = modeComboBox.getSelectionModel().getSelectedItem();
            String filePath = filePathTextField.getText();
            String content = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
            String result = "";
            String outputPath = "";

            switch (mode) {
                case "���������� �������":
                    result = CaesarCipher.encrypt(content, 3);
                    outputPath = filePath.replace(".txt", "_encrypted.txt");
                    Files.write(Paths.get(outputPath), result.getBytes("UTF-8"));
                    outputTextArea.setText("���������� ��������� �������. ���� ��������: " + outputPath + "\n\n������������� �����:\n" + result);
                    break;
                case "�����������":
                    result = CaesarCipher.decrypt(content, 3);
                    outputPath = filePath.replace(".txt", "_decrypted.txt");
                    Files.write(Paths.get(outputPath), result.getBytes("UTF-8"));
                    outputTextArea.setText("����������� ��������� �������. ���� ��������: " + outputPath + "\n\n�������������� �����:\n" + result);
                    break;
                case "Brute Force":
                    StringBuilder bruteForceResults = new StringBuilder();
                    for (int i = 1; i <= 33; i++) {
                        result = CaesarCipher.decrypt(content, i);
                        outputPath = filePath.replace(".txt", "_bruteforce_" + i + ".txt");
                        bruteForceResults.append("����� " + i + ":\n" + result + "\n���� ��������: " + outputPath + "\n\n");
                        Files.write(Paths.get(outputPath), result.getBytes("UTF-8"));
                    }
                    outputTextArea.setText("Brute Force ������ ��������.\n\n" + bruteForceResults.toString());
                    break;
                case "�������������� ������":
                    String referenceContent = new String(Files.readAllBytes(Paths.get(referenceFilePathTextField.getText())), "UTF-8");
                    Map<Character, Integer> referenceFrequencies = StatisticalAnalyzer.analyze(referenceContent);
                    result = StatisticalAnalyzer.decryptUsingStatisticalAnalysis(content, referenceFrequencies);
                    outputPath = filePath.replace(".txt", "_decrypted_statistical.txt");
                    Files.write(Paths.get(outputPath), result.getBytes("UTF-8"));
                    outputTextArea.setText("�������������� ������ ��������. ���� ��������: " + outputPath + "\n\n�������������� �����:\n" + result);
                    break;
            }
        } catch (IOException e) {
            outputTextArea.setText("��������� ������: " + e.getMessage());
        } finally {
            progressBar.setVisible(false);
        }
    }
    private void animateButtonPress(Button btn) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), btn);
        st.setByX(-0.1);
        st.setByY(-0.1);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }
}
