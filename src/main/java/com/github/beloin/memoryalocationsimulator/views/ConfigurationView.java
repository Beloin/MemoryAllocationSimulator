package com.github.beloin.memoryalocationsimulator.views;

import com.github.beloin.memoryalocationsimulator.models.Strategy;
import com.github.beloin.memoryalocationsimulator.models.configuration.EntryConfiguration;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

import static javafx.application.Application.launch;

public class ConfigurationView {
    public interface ConfigurationFunction {
        void handle(EntryConfiguration config) throws FileNotFoundException;
    }

    private final Group parent;

    public ConfigurationView(Group parent) {
        this.parent = parent;
    }


    private boolean hasBeenBuild = false;
    Label label, strategyLabel, label3, label4, label5, label6, label7;
    TextField processCountTextField, totalMTextField, tamSOMTextField;

    VBox vBox;
    HBox hBox;
    private RadioButton FFradio;
    private RadioButton WFradio;
    private RadioButton BFradio;
    private TextField startM;
    private TextField endM;
    private TextField startTi;
    private TextField endTi;
    private TextField startTp;
    private TextField endTp;

    public void buildMenu(ConfigurationFunction returnFunction) {
        label = new Label("Quantidade de processos:");
        processCountTextField = new TextField();
        processCountTextField.setText("10");


        strategyLabel = new Label("Estratégia utilizada:");
        FFradio = new RadioButton("FF");
        BFradio = new RadioButton("BF");
        WFradio = new RadioButton("WF");
        FFradio.setSelected(true);

        label3 = new Label("Tamanho da memória real:");
        totalMTextField = new TextField();
        totalMTextField.setText("2000");

        label4 = new Label("Tamanho da memória ocupada pelo SO:");
        tamSOMTextField = new TextField();
        tamSOMTextField.setText("200");

        label5 = new Label("Intervalo para gerar a memória de cada processo:");
        startM = new TextField("100");
        endM = new TextField("300");
        HBox MintervalBox = new HBox(startM, endM);




        label6 = new Label(" Intervalo para geração do Tempo de instanciação:");
        startTi = new TextField("1");
        endTi = new TextField("10");
        HBox TIintervalBox = new HBox(startTi, endTi);

        label7 = new Label("Intervalo para gerar a duração de cada processo:");
        startTp = new TextField("1");
        endTp = new TextField("30");
        HBox TpintervalBox = new HBox(startTp, endTp);

        vBox = new VBox();


        // Butons
        ToggleGroup tg = new ToggleGroup();
        HBox radioButtons = new HBox(FFradio, BFradio, WFradio);
        FFradio.setToggleGroup(tg);
        BFradio.setToggleGroup(tg);
        WFradio.setToggleGroup(tg);
        radioButtons.setSpacing(10);
        radioButtons.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().add(strategyLabel);
        vBox.getChildren().add(radioButtons);

        vBox.getChildren().add(label);
        vBox.getChildren().add(processCountTextField);
        vBox.getChildren().add(label3);
        vBox.getChildren().add(totalMTextField);
        vBox.getChildren().add(label4);
        vBox.getChildren().add(tamSOMTextField);


        vBox.getChildren().add(label5);
        vBox.getChildren().add(MintervalBox);


        vBox.getChildren().add(label6);
        vBox.getChildren().add(TIintervalBox);


        vBox.getChildren().add(label7);
        vBox.getChildren().add(TpintervalBox);
        vBox.setSpacing(10);

        hBox = new HBox();
        hBox.getChildren().add(vBox);
        hBox.setSpacing(-35);
        hBox.setAlignment(Pos.BOTTOM_CENTER);


        Button ok = new Button("Ok!");
        ok.setOnAction(actionEvent -> {
            try {
                removeMenu(returnFunction);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        hBox.getChildren().add(ok);
        hasBeenBuild = true;

        parent.getChildren().add(hBox);
        ok.requestFocus();
    }

    private String[] getMInterval() {
        String[] inteiros = new String[2];
        inteiros[0] = startM.getText();
        inteiros[1] = endM.getText();
        return inteiros;
    }

    private String[] getTIInterval() {
        String[] inteiros = new String[2];
        inteiros[0] = startTi.getText();
        inteiros[1] = endTi.getText();
        return inteiros;
    }

    private String[] getTpInterval() {
        String[] inteiros = new String[2];
        inteiros[0] = startTp.getText();
        inteiros[1] = endTp.getText();
        return inteiros;
    }

    private void removeMenu(ConfigurationFunction returnFunction) throws FileNotFoundException {
        if (hasBeenBuild) {
            try {
                EntryConfiguration config = new EntryConfiguration();

                Strategy strategy = getStrategy();
                config.setStrategy(strategy);

                int pCount = parseInt(processCountTextField.getText());
                config.setProccessCount(pCount);

                int tamMemoria = Integer.parseInt(totalMTextField.getText());
                config.setRealMemorySize(tamMemoria);

                int tamSo = Integer.parseInt(tamSOMTextField.getText());
                config.setMemoryUsedByOS(tamSo);

                String[] mInterval = getMInterval();
                config.setMemoryIntervalStart(parseInt(mInterval[0]));
                config.setMemoryIntervalEnd(parseInt(mInterval[1]));

                String[] tiInterval = getTIInterval();
                config.setInstantiationIntervalStart(parseInt(tiInterval[0]));
                config.setInstantiationIntervalEnd(parseInt(tiInterval[1]));

                String[] tpInterval = getTpInterval();
                config.setProcessDurationIntervalStart(parseInt(tpInterval[0]));
                config.setProcessDurationIntervalEnd(parseInt(tpInterval[1]));

                parent.getChildren().remove(hBox);
                System.out.println(config);
                returnFunction.handle(config);
            } catch (Exception e) {
                System.out.println("Wrong Value: " + e.getMessage());
            }
        }
    }

    private int parseInt(String it) {
        return Integer.parseInt(it);
    }

    private Strategy getStrategy() throws Exception {
        if (FFradio.isSelected()) {
            return Strategy.FIRST_FIT;
        }

        if (BFradio.isSelected()) {
            return Strategy.BEST_FIT;
        }

        if (WFradio.isSelected()) {
            return Strategy.WORST_FIT;
        }

        throw new Exception("No Strategy selected");
    }

}
