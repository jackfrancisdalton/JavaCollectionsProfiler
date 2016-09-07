package ui;

import config.Function;
import config.Order;
import helper.SortingMethodHelper;
import helper.TestRunner;
import sortingMethods.SortStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class HomeForm extends JFrame {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;
    private JPanel mainPanel;
    private JButton runButton;

    //List type label and checkboxes
    private JLabel listTypeLabel;
    private java.util.List<JCheckBox> listTypeCBList;
    private JCheckBox listTypeCB_arrayList;
    private JCheckBox listTypeCB_linkedList;
    private JCheckBox listTypeCB_vector;
    private JCheckBox listTypeCB_copyOnWriteArrayList;

    //Function type label and combo
    private JLabel functionTypeLabel;
    private JComboBox comboBoxFunction;

    //Order type label and combo
    private JLabel orderTypeLabel;
    private JComboBox comboBoxOrder;

    //Sort Type label and combo
    private JLabel sortTypeLabel;
    private JComboBox comboBoxSort;

    //Configuration label and text fields
    private JLabel numberOfRunsLabel;
    private JLabel minimumSampleSizeLabel;
    private JLabel maximumSampleSizeLabel;
    private JLabel sampleSizeStepLabel;
    private JTextField numberOfRuns;
    private JTextField minimumSampleSize;
    private JTextField maximumSampleSize;
    private JTextField sampleSizeStep;

    //units
    private JComboBox timeUnitsComboBox;

    public HomeForm() {
        setUpViewElements();
        populateViewElements();
        setUpListeners();
    }

    private void setUpListeners() {
        runButton.addActionListener(new ButtonActionListener());

        comboBoxFunction.addActionListener(e -> {
            String currentlySelected = comboBoxFunction.getSelectedItem().toString().toUpperCase();

            if(Function.SORT.toString().equalsIgnoreCase(currentlySelected)) {
                toggleOrderSortVisibility(true);
            } else {
                toggleOrderSortVisibility(false);
            }

            //TODO: if random is already chosen then changing

            runButton.setEnabled(true);
        });

        comboBoxOrder.addActionListener(e -> {
            String selectedOrder = comboBoxOrder.getSelectedItem().toString().toUpperCase();
            String selectedFunction = comboBoxFunction.getSelectedItem().toString().toUpperCase();

            if(selectedOrder.equalsIgnoreCase(Order.RANDOM.toString()) && selectedFunction.equalsIgnoreCase(Function.ITERATE.toString())) {
                runButton.setEnabled(false);
            } else {
                runButton.setEnabled(true);
            }
        });
    }

    private void setUpViewElements() {
        setSize(WIDTH, HEIGHT);
        setTitle("Collection Profiler");
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.insets = new Insets(2, 2, 2, 2);
        gc.weightx = 1;
        gc.weighty = 1;

        //Set up check boxes for list type

        listTypeCB_arrayList = new JCheckBox(ArrayList.class.getSimpleName());
        gc.gridy = 0;
        mainPanel.add(listTypeCB_arrayList, gc);

        listTypeCB_linkedList = new JCheckBox(LinkedList.class.getSimpleName());
        gc.gridy = 1;
        mainPanel.add(listTypeCB_linkedList, gc);

        listTypeCB_vector = new JCheckBox(Vector.class.getSimpleName());
        gc.gridy = 2;
        mainPanel.add(listTypeCB_vector, gc);

        listTypeCB_copyOnWriteArrayList = new JCheckBox(CopyOnWriteArrayList.class.getSimpleName());
        gc.gridy = 3;
        mainPanel.add(listTypeCB_copyOnWriteArrayList, gc);

        //set up function label and comboBox
        functionTypeLabel = new JLabel("Function Implementation");
        gc.gridy = 5;
        mainPanel.add(functionTypeLabel, gc);

        comboBoxFunction = new JComboBox();
        gc.gridy = 6;
        mainPanel.add(comboBoxFunction, gc);

        gc.gridx = 1;
        gc.gridwidth = 2;

        //set up order label and comboBox
        orderTypeLabel = new JLabel("Order Implementation");
        gc.gridy = 5;
        mainPanel.add(orderTypeLabel, gc);

        comboBoxOrder = new JComboBox();
        gc.gridy = 6;
        mainPanel.add(comboBoxOrder, gc);

        //set up sort label and comboBox

        sortTypeLabel = new JLabel("Sort Method");
        sortTypeLabel.setVisible(false);
        gc.gridy = 5;
        mainPanel.add(sortTypeLabel, gc);

        comboBoxSort = new JComboBox();
        comboBoxSort.setVisible(false);
        gc.gridy = 6;
        mainPanel.add(comboBoxSort, gc);

        gc.gridwidth = 4;
        gc.gridx = 0;

        //Time
        timeUnitsComboBox = new JComboBox();
        gc.gridy = 7;
        mainPanel.add(timeUnitsComboBox, gc);

        //set up run button
        runButton = new JButton("Run");
        gc.gridy = 8;
        mainPanel.add(runButton, gc);

        //set up edit text fields for entering sample sizes and runs
        gc.gridx = 1;
        gc.gridwidth = 1;

        numberOfRunsLabel = new JLabel("Repetitions");
        numberOfRunsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gc.gridy = 0;
        mainPanel.add(numberOfRunsLabel, gc);

        minimumSampleSizeLabel = new JLabel("Starting Sample Size");
        minimumSampleSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gc.gridy = 1;
        mainPanel.add(minimumSampleSizeLabel, gc);

        maximumSampleSizeLabel = new JLabel("Final Sample Size");
        maximumSampleSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gc.gridy = 2;
        mainPanel.add(maximumSampleSizeLabel, gc);

        sampleSizeStepLabel = new JLabel("Step");
        sampleSizeStepLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gc.gridy = 3;
        mainPanel.add(sampleSizeStepLabel, gc);

        gc.gridx = 2;

        numberOfRuns = new JTextField("10");
        gc.gridy = 0;
        mainPanel.add(numberOfRuns, gc);

        minimumSampleSize = new JTextField("1000");
        gc.gridy = 1;
        mainPanel.add(minimumSampleSize, gc);

        maximumSampleSize = new JTextField("10000");
        gc.gridy = 2;
        mainPanel.add(maximumSampleSize, gc);

        sampleSizeStep = new JTextField("1000");
        gc.gridy = 3;
        mainPanel.add(sampleSizeStep, gc);

        setupGroupings();
    }

    private void setupGroupings() {
        listTypeCBList = new ArrayList<>();
        listTypeCBList.add(listTypeCB_arrayList);
        listTypeCBList.add(listTypeCB_linkedList);
        listTypeCBList.add(listTypeCB_vector);
        listTypeCBList.add(listTypeCB_copyOnWriteArrayList);
    }

    private void populateViewElements() {

        orderTypeLabel.setText("Order Type");
        comboBoxOrder.addItem(toProperCase(Order.STANDARD.toString()));
        comboBoxOrder.addItem(toProperCase(Order.RANDOM.toString()));
        comboBoxOrder.addItem(toProperCase(Order.REVERSE.toString()));

        functionTypeLabel.setText("Function Type");
        comboBoxFunction.addItem(toProperCase(Function.SORT.toString()));
        comboBoxFunction.addItem(toProperCase(Function.ITERATE.toString()));
        comboBoxFunction.addItem(toProperCase(Function.INSERT.toString()));
        comboBoxFunction.addItem(toProperCase(Function.REMOVE.toString()));

        for (String className : SortingMethodHelper.getInstance().getClassList()) {
            comboBoxSort.addItem(className);
        }
        comboBoxSort.setVisible(false);

        timeUnitsComboBox.addItem(ChronoUnit.MILLIS);
        timeUnitsComboBox.addItem(ChronoUnit.SECONDS);
        timeUnitsComboBox.addItem(ChronoUnit.MINUTES);
    }

    private class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            java.util.List<String> listImplementationsToTest = new ArrayList<>();

            for(JCheckBox jCheckBox : listTypeCBList)
                if(jCheckBox.isSelected())
                    listImplementationsToTest.add(jCheckBox.getText());

            Function functionTypeValue = Function.valueOf(comboBoxFunction.getSelectedItem().toString().toUpperCase());
            Order orderValue = Order.valueOf(comboBoxOrder.getSelectedItem().toString().toUpperCase());
            int runs = Integer.parseInt(numberOfRuns.getText());
            int minSamples = Integer.parseInt(minimumSampleSize.getText());
            int maxSamples = Integer.parseInt(maximumSampleSize.getText());
            int step = Integer.parseInt(sampleSizeStep.getText());

            SortStrategy sortStrategy = null;
            try {
                sortStrategy = SortingMethodHelper.getInstance().convertStringToClass(comboBoxSort.getSelectedItem().toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            ChronoUnit chronoUnit = ChronoUnit.valueOf(timeUnitsComboBox.getSelectedItem().toString().toUpperCase());

            try {
                TestRunner.runTest(runs, minSamples, maxSamples, step, listImplementationsToTest, functionTypeValue, orderValue, sortStrategy, chronoUnit);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void toggleOrderSortVisibility(boolean setSort) {
        sortTypeLabel.setVisible(setSort);
        comboBoxSort.setVisible(setSort);
        orderTypeLabel.setVisible(!setSort);
        comboBoxOrder.setVisible(!setSort);
        comboBoxSort.setEnabled(true);
        comboBoxOrder.setEnabled(true);
    }

    static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
