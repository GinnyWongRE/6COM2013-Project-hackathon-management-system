import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main GUI window for Hackathon Management System
 * Uses MVC pattern with tabs for different functionalities
 */
public class HackathonGUI extends JFrame {
    private HackathonController controller;
    private JTabbedPane tabbedPane;

    // Panels
    private TeamManagementPanel teamManagementPanel;
    private ScorePanel scorePanel;
    private ReportPanel reportPanel;

    public HackathonGUI(HackathonController controller) {
        this.controller = controller;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Hackathon Management System - Stage 6");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); // Center on screen

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Create panels
        teamManagementPanel = new TeamManagementPanel(controller);
        scorePanel = new ScorePanel(controller);
        reportPanel = new ReportPanel(controller);

        // Add tabs
        tabbedPane.addTab("Team Management", teamManagementPanel);
        tabbedPane.addTab("Score Management", scorePanel);
        tabbedPane.addTab("Reports", reportPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = new JButton("Close & Save Report");
        closeButton.addActionListener(e -> closeApplication());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeApplication();
            }
        });
    }

    private void closeApplication() {
        // Write final report
        controller.writeReportToFile("final_gui_report.txt");
        System.out.println("Final report saved to: final_gui_report.txt");

        // Exit application
        System.exit(0);
    }

    public static void main(String[] args) {
        // Load team data
        TeamList teamList = new TeamList();
        teamList.loadTeamsFromCSV("teams.csv");

        // Create controller and GUI
        HackathonController controller = new HackathonController(teamList);

        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            HackathonGUI gui = new HackathonGUI(controller);
            gui.setVisible(true);
        });
    }
}