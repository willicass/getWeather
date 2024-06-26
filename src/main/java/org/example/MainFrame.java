package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainFrame extends JFrame {
    private JTextField cepField;
    private JButton searchButton;
    private JTextArea resultArea;

    public MainFrame() {
        setTitle("Weather Lookup by ZIP Code");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel cepLabel = new JLabel("ZIP code:");
        cepLabel.setBounds(50, 30, 150, 25);
        add(cepLabel);

        cepField = new JTextField();
        cepField.setBounds(150, 30, 150, 25);
        add(cepField);

        searchButton = new JButton("Search Weather");
        searchButton.setBounds(120, 70, 150, 25);
        add(searchButton);

        resultArea = new JTextArea();
        resultArea.setBounds(50, 110, 300, 130);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        add(resultArea);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cep = cepField.getText();
                buscarClimaPorCEP(cep);
            }
        });
    }

    private void buscarClimaPorCEP(String cep) {
        try {
            String climaInfo = APICorreios.buscarCEP(cep);
            String extrairLocalidade = APICorreios.extrairLocalidadeDoJSON(climaInfo);
            String buscarClima = APIClima.buscarClima(extrairLocalidade);
            String extrairClima = APIClima.extrairClimaDoJSON(buscarClima);
            resultArea.setText("The ZIP " + cep + " from the city of: " + extrairLocalidade + " and it is currently: " + extrairClima);
        } catch (IOException e) {
            e.printStackTrace();
            resultArea.setText("Error retrieving weather information for the ZIP code " + cep);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}