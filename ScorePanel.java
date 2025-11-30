// File: ScorePanel.java
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for managing and editing team scores
 */
public class ScorePanel extends JPanel {
    private HackathonController controller;
    private JComboBox<Team> teamSelector;
    private JSpinner[] scoreSpinners;
    private JLabel overallScoreLabel;
    private JTextArea scoreDetailsArea;

    public ScorePanel(HackathonController controller) {
        this.controller = controller;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Initialize ALL components first
        initializeComponents();

        // Build the UI layout
        buildUI();

        // Now that everything is initialized, load the data
        refreshTeamSelector();
    }

    private void initializeComponents() {
        // Initialize score spinners
        scoreSpinners = new JSpinner[5];
        for (int i = 0; i < scoreSpinners.length; i++) {
            SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 5, 1);
            scoreSpinners[i] = new JSpinner(model);
        }

        // Initialize other components
        teamSelector = new JComboBox<>();
        overallScoreLabel = new JLabel("0.0");
        overallScoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreDetailsArea = new JTextArea(8, 40);
        scoreDetailsArea.setEditable(false);
        scoreDetailsArea.setBorder(BorderFactory.createTitledBorder("Team Details"));
    }

    private void buildUI() {
        // Top panel for team selection
        JPanel selectionPanel = new JPanel(new FlowLayout());
        selectionPanel.add(new JLabel("Select Team:"));

        teamSelector.addActionListener(e -> displaySelectedTeamScores());
        selectionPanel.add(teamSelector);

        JButton refreshButton = new JButton("Refresh Teams");
        refreshButton.addActionListener(e -> refreshTeamSelector());
        selectionPanel.add(refreshButton);

        add(selectionPanel, BorderLayout.NORTH);

        // Center panel for score editing
        JPanel scorePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the initialized score spinners
        for (int i = 0; i < scoreSpinners.length; i++) {
            scorePanel.add(new JLabel("Score " + (i + 1) + ":"));
            scorePanel.add(scoreSpinners[i]);
        }

        // Overall score display
        scorePanel.add(new JLabel("Overall Score:"));
        scorePanel.add(overallScoreLabel);

        add(scorePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton updateScoresButton = new JButton("Update Scores");
        updateScoresButton.addActionListener(e -> updateScores());
        buttonPanel.add(updateScoresButton);

        JButton resetButton = new JButton("Reset to Original");
        resetButton.addActionListener(e -> displaySelectedTeamScores());
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Details area
        JScrollPane detailsScrollPane = new JScrollPane(scoreDetailsArea);
        add(detailsScrollPane, BorderLayout.EAST);
    }

    private void refreshTeamSelector() {
        teamSelector.removeAllItems();
        ArrayList<Team> teams = controller.getAllTeams();
        for (Team team : teams) {
            teamSelector.addItem(team);
        }
        if (teams.size() > 0) {
            displaySelectedTeamScores();
        }
    }

    private void displaySelectedTeamScores() {
        Team selectedTeam = (Team) teamSelector.getSelectedItem();
        if (selectedTeam != null) {
            // Display current scores
            int[] scores = selectedTeam.getScoreArray();
            for (int i = 0; i < scoreSpinners.length && i < scores.length; i++) {
                scoreSpinners[i].setValue(scores[i]);
            }

            // Update overall score
            overallScoreLabel.setText(String.format("%.1f", selectedTeam.getOverallScore()));

            // Display team details
            scoreDetailsArea.setText(selectedTeam.getFullDetails());
        }
    }

    private void updateScores() {
        Team selectedTeam = (Team) teamSelector.getSelectedItem();
        if (selectedTeam != null) {
            // Get new scores from spinners
            int[] newScores = new int[scoreSpinners.length];
            for (int i = 0; i < scoreSpinners.length; i++) {
                newScores[i] = (Integer) scoreSpinners[i].getValue();
            }

            // Update scores in controller
            controller.updateTeamScores(selectedTeam.getTeamNumber(), newScores);

            // Refresh display to show updated overall score
            displaySelectedTeamScores();

            JOptionPane.showMessageDialog(this,
                    "Scores updated successfully!\nNew overall score: " +
                            String.format("%.1f", selectedTeam.getOverallScore()),
                    "Scores Updated",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}