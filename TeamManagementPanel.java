import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for team management: view, sort, filter teams
 */
public class TeamManagementPanel extends JPanel {
    private HackathonController controller;
    private JTable teamTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> sortComboBox;

    public TeamManagementPanel(HackathonController controller) {
        this.controller = controller;
        initializePanel();
        refreshTeamTable();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        // Top panel for controls
        JPanel controlPanel = new JPanel(new FlowLayout());

        // Sort options
        controlPanel.add(new JLabel("Sort by:"));
        sortComboBox = new JComboBox<>(new String[]{
                "Team Number", "Team Name", "Overall Score", "Category"
        });
        sortComboBox.addActionListener(e -> sortTeams());
        controlPanel.add(sortComboBox);

        // Filter by category
        controlPanel.add(new JLabel("Filter by Category:"));
        categoryFilter = new JComboBox<>(new String[]{
                "All Categories", "Web Development", "Artificial Intelligence", "Cybersecurity", "Mobile Applications"
        });
        categoryFilter.addActionListener(e -> filterTeams());
        controlPanel.add(categoryFilter);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTeamTable());
        controlPanel.add(refreshButton);

        add(controlPanel, BorderLayout.NORTH);

        // Team table
        String[] columnNames = {"Team ID", "Team Name", "University", "Category", "Overall Score", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        teamTable = new JTable(tableModel);
        teamTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(teamTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for actions
        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton viewDetailsButton = new JButton("View Full Details");
        viewDetailsButton.addActionListener(e -> viewSelectedTeamDetails());
        actionPanel.add(viewDetailsButton);

        // Add the edit button here (AFTER actionPanel is created)
        JButton editButton = new JButton("Edit Team Details");
        editButton.addActionListener(e -> editTeamDetails());
        actionPanel.add(editButton);

        JButton removeButton = new JButton("Remove Selected Team");
        removeButton.addActionListener(e -> removeSelectedTeam());
        actionPanel.add(removeButton);

        add(actionPanel, BorderLayout.SOUTH);
    }

    private void refreshTeamTable() {
        tableModel.setRowCount(0); // Clear table

        ArrayList<Team> teams = controller.getAllTeams();
        for (Team team : teams) {
            Object[] rowData = {
                    team.getTeamNumber(),
                    team.getTeamName(),
                    team.getUniversity(),
                    team.getCategory().getCategoryName(),
                    team.getOverallScore(),
                    team.getClass().getSimpleName()
            };
            tableModel.addRow(rowData);
        }
    }

    // Update the sortTeams method
    private void sortTeams() {
        String sortBy = (String) sortComboBox.getSelectedItem();

        switch (sortBy) {
            case "Team Number":
                controller.sortTeamsByTeamNumber();  // Call the controller method
                refreshTeamTable();  // Refresh to show sorted data
                break;
            case "Team Name":
                controller.sortTeamsByName();
                refreshTeamTable();
                break;
            case "Overall Score":
                controller.sortTeamsByScore();
                refreshTeamTable();
                break;
            case "Category":
                controller.sortTeamsByCategory();
                refreshTeamTable();
                break;
        }
    }

    private void filterTeams() {
        String selectedCategory = (String) categoryFilter.getSelectedItem();

        if ("All Categories".equals(selectedCategory)) {
            refreshTeamTable();
        } else {
            tableModel.setRowCount(0);
            ArrayList<Team> filteredTeams = controller.getTeamsByCategory(selectedCategory);

            for (Team team : filteredTeams) {
                Object[] rowData = {
                        team.getTeamNumber(),
                        team.getTeamName(),
                        team.getUniversity(),
                        team.getCategory().getCategoryName(),
                        team.getOverallScore(),
                        team.getClass().getSimpleName()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    private void viewSelectedTeamDetails() {
        int selectedRow = teamTable.getSelectedRow();
        if (selectedRow >= 0) {
            int teamID = (int) tableModel.getValueAt(selectedRow, 0);
            Team team = controller.getTeamByID(teamID);
            if (team != null) {
                JOptionPane.showMessageDialog(this,
                        team.getFullDetails(),
                        "Team Details - " + team.getTeamName(),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a team first.",
                    "No Team Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editTeamDetails() {
        int selectedRow = teamTable.getSelectedRow();
        if (selectedRow >= 0) {
            int teamID = (int) tableModel.getValueAt(selectedRow, 0);
            Team team = controller.getTeamByID(teamID);

            if (team != null) {
                // Create edit dialog
                JPanel panel = new JPanel(new GridLayout(0, 2));
                JTextField nameField = new JTextField(team.getTeamName());
                JTextField uniField = new JTextField(team.getUniversity());

                panel.add(new JLabel("Team Name:"));
                panel.add(nameField);
                panel.add(new JLabel("University:"));
                panel.add(uniField);

                int result = JOptionPane.showConfirmDialog(
                        this, panel, "Edit Team Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    team.setTeamName(nameField.getText());
                    team.setUniversity(uniField.getText());
                    refreshTeamTable();
                    JOptionPane.showMessageDialog(this, "Team details updated!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a team first.",
                    "No Team Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeSelectedTeam() {
        int selectedRow = teamTable.getSelectedRow();
        if (selectedRow >= 0) {
            int teamID = (int) tableModel.getValueAt(selectedRow, 0);
            String teamName = (String) tableModel.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to remove team: " + teamName + "?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.removeTeam(teamID)) {
                    refreshTeamTable();
                    JOptionPane.showMessageDialog(this, "Team removed successfully.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a team to remove.",
                    "No Team Selected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}