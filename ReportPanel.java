import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for generating and viewing reports
 */
public class ReportPanel extends JPanel {
    private HackathonController controller;
    private JTextArea reportArea;
    private JComboBox<String> reportTypeComboBox;

    public ReportPanel(HackathonController controller) {
        this.controller = controller;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Top panel for report controls
        JPanel controlPanel = new JPanel(new FlowLayout());

        controlPanel.add(new JLabel("Report Type:"));
        reportTypeComboBox = new JComboBox<>(new String[]{
                "Full Team Report",
                "Summary Statistics",
                "Score Frequency",
                "Highest Scoring Team"
        });
        controlPanel.add(reportTypeComboBox);

        JButton generateButton = new JButton("Generate Report");
        generateButton.addActionListener(e -> generateReport());
        controlPanel.add(generateButton);

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(e -> saveReportToFile());
        controlPanel.add(saveButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> reportArea.setText(""));
        controlPanel.add(clearButton);

        add(controlPanel, BorderLayout.NORTH);

        // Report display area
        reportArea = new JTextArea(20, 60);
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void generateReport() {
        String reportType = (String) reportTypeComboBox.getSelectedItem();
        String reportContent = "";

        switch (reportType) {
            case "Full Team Report":
                reportContent = controller.generateFullReport();
                break;
            case "Summary Statistics":
                reportContent = controller.getSummaryStatistics();
                break;
            case "Score Frequency":
                reportContent = generateFrequencyReport();
                break;
            case "Highest Scoring Team":
                Team highestTeam = controller.getHighestScoringTeam();
                if (highestTeam != null) {
                    reportContent = "=== HIGHEST SCORING TEAM ===\n\n" +
                            highestTeam.getFullDetails();
                } else {
                    reportContent = "No teams available.";
                }
                break;
        }

        reportArea.setText(reportContent);
    }

    private String generateFrequencyReport() {
        // We need to add getScoreFrequency to the controller
        // For now, let's create a simple implementation
        StringBuilder frequencyReport = new StringBuilder();
        frequencyReport.append("=== SCORE FREQUENCY REPORT ===\n\n");

        // This is a simplified version - you might want to enhance this
        int[] frequency = new int[6]; // indices 0-5 (we use 1-5)

        for (Team team : controller.getAllTeams()) {
            for (int score : team.getScoreArray()) {
                if (score >= 1 && score <= 5) {
                    frequency[score]++;
                }
            }
        }

        frequencyReport.append("Score | Frequency\n");
        frequencyReport.append("------|----------\n");
        for (int i = 1; i <= 5; i++) {
            frequencyReport.append(String.format("%5d | %9d\n", i, frequency[i]));
        }

        return frequencyReport.toString();
    }

    private void saveReportToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report As");

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            controller.writeReportToFile(filename);
            JOptionPane.showMessageDialog(this,
                    "Report saved to: " + filename,
                    "Save Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}