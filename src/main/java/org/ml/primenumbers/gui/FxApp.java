package org.ml.primenumbers.gui;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;
import org.ml.primenumbers.PrimeNumbers;
import org.ml.primenumbers.util.AlgorithmFinder;
import org.ml.primenumbers.util.Notifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//@SuppressWarnings("restriction")
public class FxApp extends Application {

    private static final String INTERVAL_OVER_MAX = "Maximum cannot be less than interval";
    private static final String INTERVAL_MULTIPLY = "Maximum must be a multiply of interval value";
    private static final String PATH_MISSING = "Please specify output file";

    private AlgorithmFinder algorithmFinder;
    private ObservableList<AlgorithmItem> algorithms = FXCollections.observableArrayList();

    private Notifier onListReady = () -> {

        List<AlgorithmItem> list = algorithmFinder.getList()
                        .stream()
                        .map((a) -> new AlgorithmItem(a))
                        .collect(Collectors.toList());
        list.sort((o1, o2) -> o1.getAlgorithm().getName().compareTo(o2.getAlgorithm().getName()));
        algorithms.clear();
        algorithms.addAll(list);
    };

    private FileChooser fileChooser;
    private Stage stage;
    private Scene mainScene;
    private BorderPane main;
    private BorderPane progressPane;
    // parameters
    private TextField intervalValue;
    private TextField maxValue;
    private Spinner<Integer> repeatValue;
    private TextField outputFileNameField;
    // int params

    long intervalParam = 0;
    long maxParam = 0;
    int repeatParam = 0;

    // varia
    private Alert errorAlert;
    private Alert yesNoAlert;
    private Alert successAlert;

    private PrimeNumbers primeNumbers;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    @Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Prime numbers algorithm tester");

		stage = primaryStage;

		main = new BorderPane();
        main.setPadding(new Insets(10, 10, 10, 10));

        algorithmFinder = new AlgorithmFinder(onListReady);

        // Title
        Label title = new Label("Prime numbers algorithm tester");
        title.setStyle("font-size: 2em; font-weight: bold");

        // Buttons
        HBox buttons = new HBox(5);
        buttons.setAlignment(Pos.TOP_RIGHT);
        //buttons.setPadding(new Insets(5, 5, 5, 5));
        Button generate = new Button("Generate");
        generate.setOnAction((ActionEvent evt) -> {
            validateParameters();
        });
        buttons.getChildren().add(generate);


        // List of algorithms
        ListView<AlgorithmItem> list = new ListView<>();
        list.setItems(algorithms);
        list.getStyleClass().add("prime-list");
        list.setEditable(false);
        list.setCellFactory(CheckBoxListCell.forListView(AlgorithmItem::onProperty));

        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Parameters
        GridPane grid = new GridPane();
        // grid settings
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        addParameters(grid);
        setupAlerts();
        createProgressPane();

        // file chooser
        outputFileNameField = addParameter(grid, "Output:", "output", 4, null);
        Path currentPath = Paths.get("html", "results.js");

        outputFileNameField.setText(currentPath.toAbsolutePath().toString());

        Button selectFile = new Button("...");
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JS Files", "*.js"));
        //fileChooser.setInitialFileName("results.js");
        selectFile.setOnAction((final ActionEvent e) -> {
            TextField file = outputFileNameField;
            int nameStart = file.getText().lastIndexOf(File.separatorChar);
            if (nameStart != -1) {
                fileChooser.setInitialFileName(file.getText().substring(nameStart + 1));
                fileChooser.setInitialDirectory(new File(file.getText().substring(0, nameStart)));
            } else if (file.getText().length() > 0) {
                fileChooser.setInitialFileName(file.getText());
            }
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                file.setText(selectedFile.getAbsolutePath());
            }
        });
        GridPane.setConstraints(selectFile, 2, 4);
        grid.getChildren().add(selectFile);

        main.setCenter(grid);
        main.setLeft(list);
        main.setBottom(buttons);
        //main.setTop(title);

        mainScene = new Scene(main, 600, 400);
        mainScene.getStylesheets().add("prime.css");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        new Thread(algorithmFinder).start();
	}

	private void setupAlerts() {
	    errorAlert = new Alert(Alert.AlertType.ERROR);
        yesNoAlert = new Alert(Alert.AlertType.WARNING);
        yesNoAlert.getButtonTypes().clear();
        yesNoAlert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        successAlert = new Alert(Alert.AlertType.INFORMATION);
    }

	private void validateParameters() {
        long interval = Double.valueOf(intervalValue.getText()).longValue();
        long max = Integer.valueOf(maxValue.getText()).longValue();
        int repeat = repeatValue.getValue();
        String path = outputFileNameField.getText();

        if (max < interval) {
            errorAlert.contentTextProperty().setValue(INTERVAL_OVER_MAX);
            errorAlert.showAndWait();
            return;
        }
        if (max % interval > 0) {
            errorAlert.contentTextProperty().setValue(INTERVAL_MULTIPLY);
            errorAlert.showAndWait();
            return;
        }

        if (path.isEmpty()) {
            errorAlert.contentTextProperty().setValue(PATH_MISSING);
            errorAlert.showAndWait();
            return;
        }

        int steps = (int) (max / interval);
        boolean runTest = true;
        if (steps > 100) {
            runTest = false;
            yesNoAlert.contentTextProperty().setValue("Number of iterations (" + steps + ") is too high. Do you want to continue?");
            Optional<ButtonType> result = yesNoAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                runTest = true;
            }
        }

        intervalParam = interval;
        maxParam = max;
        repeatParam = repeat;

        if (runTest) {
            startTest();
        }
    }

    private void startTest() {
	    primeNumbers = null;
        primeNumbers = new PrimeNumbers(intervalParam, maxParam, repeatParam);
        //primeNumbers.setOnFinish(() -> Platform.runLater(() ->  onFinish()));

        for (AlgorithmItem ai : algorithms) {
            if (ai.onProperty().get()) {
                primeNumbers.addAlgorithm(ai.getAlgorithm());
            }
        }

        // change scene
        mainScene.setRoot(progressPane);

        // TODO: rewrite with executorService, callbacks and Callable<Result> ?
        executor.submit(() -> {
            primeNumbers.run();
            Platform.runLater(() ->  onFinish());
        });

        //primeThread = new Thread(primeNumbers, "primenumbers-thread");
        //primeThread.start();
    }

    private void onFinish() {
        mainScene.setRoot(main);
        if (primeNumbers.isDone()) {
            try {
                PrimeNumbers.saveResults(outputFileNameField.getText(), primeNumbers);
                successAlert.setContentText("");
                successAlert.setHeaderText("Results successfully exported");
                successAlert.setTitle("Success");
                successAlert.showAndWait();

            } catch (IOException e) {
                errorAlert.setContentText("IO Error: " + e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            errorAlert.setContentText("Process interrupted");
            errorAlert.showAndWait();
        }

    }

    private TextField addParameter(Pane parent, String text, String id, int row, String value) {
        TextField textField = new TextField();
        textField.setId(id);
        Label label = new Label(text);
        GridPane.setConstraints(label, 0, row);
        GridPane.setConstraints(textField, 1, row);
        parent.getChildren().addAll(label, textField);

        if (value != null) textField.setText(value);

        return textField;
    }

    private void addParameters(Pane parent) {

        Label label = new Label("Interval:");
        intervalValue = new TextField();
        intervalValue.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter()));
        intervalValue.setTooltip(new Tooltip("Interval for test iterations"));
        intervalValue.setText("1000000");
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(intervalValue, 1, 0);
        parent.getChildren().addAll(label, intervalValue);


        Label label2 = new Label("Maximum:");

        maxValue = new TextField();
        maxValue.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter()));
        maxValue.setTooltip(new Tooltip("Maximum number to find from"));
        maxValue.setText("10000000");
        GridPane.setConstraints(label2, 0, 1);
        GridPane.setConstraints(maxValue, 1, 1);
        parent.getChildren().addAll(label2, maxValue);


        Label label3 = new Label("Repeat:");

        repeatValue = new Spinner<>();
        repeatValue.setId("step");
        //repeatValue.getEditor().setAlignment(Pos.CENTER_RIGHT);
        repeatValue.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3));
        repeatValue.setTooltip(new Tooltip("Number of times to repeat each test to get average result"));
        GridPane.setConstraints(label3, 0, 2);
        GridPane.setConstraints(repeatValue, 1, 2);
        parent.getChildren().addAll(label3, repeatValue);


    }

    private void createProgressPane() {
	    progressPane = new BorderPane();
	    progressPane.setPadding(new Insets(10, 10, 10, 10));

	    // progress bar
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(100);
        progressPane.setCenter(progressBar);

        // cancel
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction((final ActionEvent e) -> {
            primeNumbers.interrupt();
            mainScene.setRoot(main);
        });
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(cancelButton);

        progressPane.setBottom(bottom);

    }
}
