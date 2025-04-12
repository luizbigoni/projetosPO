package fipp.com.trab2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class TimSortApplication extends Application {

    private GridPane gridPane;
    private Button[] buttons;
    private Label esquerdaLabel;
    private Label direitaLabel;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Método Tim Sort");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("""
            -fx-background-color: #000000;
            -fx-padding: 20;
            -fx-alignment: center;
        """);

        int numButtons = 15;
        buttons = new Button[numButtons];
        desenharBotoes(numButtons);
        preenchendoArray(1, 100);

        esquerdaLabel = new Label("Esquerda: ");
        esquerdaLabel.setFont(new Font("Arial", 16));
        esquerdaLabel.setTextFill(Color.RED);
        esquerdaLabel.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-border-color: #ff0000; -fx-border-radius: 5; -fx-background-radius: 5;");

        direitaLabel = new Label("Direita: ");
        direitaLabel.setFont(new Font("Arial", 16));
        direitaLabel.setTextFill(Color.BLUE);
        direitaLabel.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-border-color: #0000ff; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Adicionando os blocos esquerda/direita antes do código fonte
        GridPane infoPane = new GridPane();
        infoPane.setHgap(20);
        infoPane.add(esquerdaLabel, 0, 0);
        infoPane.add(direitaLabel, 1, 0);
        gridPane.add(infoPane, 0, 1, numButtons, 1);

        Label codeLabel = new Label("public void timSort() {\n" +
                "    int divisor = 4;\n\n" +
                "    for (int i = 0; i < TL; i += divisor)\n" +
                "        insercaoDiretaTim(getIndex(i), getIndex(Math.min(i + divisor - 1, (TL - 1))));\n\n" +
                "    for (int n = divisor; n < TL; n = 2 * n) {\n" +
                "        for (int ini = 0; ini < TL; ini += 2 * n) {\n" +
                "            int meio = ini + n - 1;\n" +
                "            int fim = Math.min(ini + 2 * n - 1, TL - 1);\n" +
                "            if (meio < fim)\n" +
                "                mergeTim(ini, meio, meio + 1, fim);\n" +
                "        }\n" +
                "    }\n" +
                "}");
        codeLabel.setFont(new Font("Courier New", 12));
        codeLabel.setStyle("""
            -fx-padding: 15;
            -fx-background-color: #2e2e2e;
            -fx-text-fill: #00ff99;
            -fx-border-color: #555;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """);

        gridPane.add(codeLabel, 0, 2, numButtons, 1);

        // Inicia automaticamente o Tim Sort
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                timSort(0, buttons.length - 1);
                return null;
            }
        };
        new Thread(task).start();

        Scene scene = new Scene(gridPane, 1200, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void desenharBotoes(int numButtons) {
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = new Button();
            buttons[i].setMinWidth(50);
            buttons[i].setMinHeight(50);
            buttons[i].setFont(new Font("Arial", 14));
            buttons[i].setFocusTraversable(false);
            buttons[i].setStyle("""
                -fx-background-color: #2196F3;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-background-radius: 5;
                -fx-cursor: hand;
            """);
            int index = i;
            buttons[i].setOnMouseEntered(e -> buttons[index].setStyle("""
                -fx-background-color: #1976D2;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-background-radius: 5;
                -fx-cursor: hand;
            """));
            buttons[i].setOnMouseExited(e -> buttons[index].setStyle("""
                -fx-background-color: #2196F3;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-background-radius: 5;
                -fx-cursor: hand;
            """));
            gridPane.add(buttons[i], i, 0);
        }
    }

    private void preenchendoArray(Integer min, Integer max) {
        Random random = new Random();
        for (int i = 0; i < buttons.length; i++) {
            int randomNumber = random.nextInt(max - min + 1) + min;
            buttons[i].setText(Integer.toString(randomNumber));
        }
    }

    private void swapButtons(int i, int j) {
        String tempText = buttons[i].getText();
        buttons[i].setText(buttons[j].getText() + " ▲");
        buttons[j].setText(tempText + " ▲");
        buttons[i].setStyle("-fx-background-color: #81C784; -fx-border-color: #388E3C; -fx-border-width: 2px;");
        buttons[j].setStyle("-fx-background-color: #81C784; -fx-border-color: #388E3C; -fx-border-width: 2px;");
    }

    private void resetButtonStyles() {
        for (Button button : buttons) {
            button.setStyle("""
                -fx-background-color: #2196F3;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-background-radius: 5;
                -fx-cursor: hand;
            """);
            String text = button.getText().replace(" ▲", "");
            button.setText(text);
        }
    }

    private void insertionSort(int left, int right) throws InterruptedException {
        for (int i = left + 1; i <= right; i++) {
            int temp = Integer.parseInt(buttons[i].getText().replace(" ▲", ""));
            int[] j = {i - 1};

            final int currentIndex = i;

            Platform.runLater(() -> {
                buttons[currentIndex].setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            });
            Thread.sleep(400);

            while (j[0] >= left && Integer.parseInt(buttons[j[0]].getText().replace(" ▲", "")) > temp) {
                int finalJ = j[0];
                Platform.runLater(() -> {
                    swapButtons(finalJ, finalJ + 1);
                });
                Thread.sleep(400);
                j[0]--;
            }

            int finalJ = j[0] + 1;
            Platform.runLater(() -> {
                buttons[finalJ].setText(String.valueOf(temp));
                resetButtonStyles();
            });
            Thread.sleep(400);
        }
    }

    private void merge(int l, int m, int r) throws InterruptedException {
        int len1 = m - l + 1;
        int len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];

        for (int i = 0; i < len1; i++)
            left[i] = Integer.parseInt(buttons[l + i].getText().replace(" ▲", ""));
        for (int i = 0; i < len2; i++)
            right[i] = Integer.parseInt(buttons[m + 1 + i].getText().replace(" ▲", ""));

        StringBuilder leftList = new StringBuilder("Esquerda: ");
        StringBuilder rightList = new StringBuilder("Direita: ");

        for (int x : left) leftList.append(x).append(" ");
        for (int x : right) rightList.append(x).append(" ");

        Platform.runLater(() -> {
            esquerdaLabel.setText(leftList.toString());
            direitaLabel.setText(rightList.toString());
        });

        int i = 0, j = 0;
        int k = l;

        while (i < len1 && j < len2) {
            int finalK = k;
            if (left[i] <= right[j]) {
                int tempValue = left[i];
                Platform.runLater(() -> buttons[finalK].setText(tempValue + " ▲"));
                i++;
            } else {
                int tempValue = right[j];
                Platform.runLater(() -> buttons[finalK].setText(tempValue + " ▲"));
                j++;
            }
            Thread.sleep(600);
            k++;
        }

        while (i < len1) {
            int finalK = k;
            int tempValue = left[i];
            Platform.runLater(() -> buttons[finalK].setText(tempValue + " ▲"));
            Thread.sleep(600);
            i++;
            k++;
        }

        while (j < len2) {
            int finalK = k;
            int tempValue = right[j];
            Platform.runLater(() -> buttons[finalK].setText(tempValue + " ▲"));
            Thread.sleep(600);
            j++;
            k++;
        }

        Platform.runLater(this::resetButtonStyles);
    }

    private void timSort(int l, int r) throws InterruptedException {
        int RUN = 4;
        for (int i = l; i < r; i += RUN)
            insertionSort(i, Math.min(i + RUN - 1, r));

        for (int size = RUN; size < r - l + 1; size *= 2) {
            for (int left = l; left < r; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), r);
                if (mid < right)
                    merge(left, mid, right);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}