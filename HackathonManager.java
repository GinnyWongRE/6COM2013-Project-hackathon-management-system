import java.util.Scanner;

/**
 * HackathonManager class - Main coordinator for the hackathon management system
 * Handles user interactions, file operations, and system coordination
 */
public class HackathonManager {
    private TeamList teamList;
    private Scanner scanner;

    public HackathonManager() {
        this.teamList = new TeamList();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main coordination method - runs the entire system
     */
    public void run() {
        System.out.println("=== HACKATHON MANAGEMENT SYSTEM - STAGE 5 ===");
        System.out.println("Loading team data...\n");

        // Step 1: Load teams from CSV
        loadTeamData();

        // Step 2: Display main menu
        displayMainMenu();

        scanner.close();
    }

    /**
     * Load team data from CSV file
     */
    private void loadTeamData() {
        teamList.loadTeamsFromCSV("teams.csv");
        System.out.println("Total teams loaded: " + teamList.getTotalTeams() + "\n");
    }

    /**
     * Display main menu and handle user choices
     */
    private void displayMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("=== MAIN MENU ===");
            System.out.println("1. Display Full Team Report");
            System.out.println("2. Search Team by ID");
            System.out.println("3. Generate Complete Report to File");
            System.out.println("4. Display Summary Statistics");
            System.out.println("5. Display Score Frequency");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        displayFullReport();
                        break;
                    case 2:
                        searchTeamByID();
                        break;
                    case 3:
                        generateCompleteReport();
                        break;
                    case 4:
                        displaySummaryStatistics();
                        break;
                    case 5:
                        displayScoreFrequency();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Thank you for using Hackathon Management System!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1-6.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-6).\n");
            }
        }
    }

    /**
     * Display full team report to console
     */
    private void displayFullReport() {
        System.out.println("\n" + teamList.generateFullReport());
        waitForUser();
    }

    /**
     * Search for a team by ID and display short details
     */
    private void searchTeamByID() {
        System.out.print("Enter Team ID to search: ");

        try {
            int teamID = Integer.parseInt(scanner.nextLine().trim());
            HackathonTeam team = teamList.getTeamByID(teamID);

            if (team != null) {
                System.out.println("\n=== TEAM FOUND ===");
                System.out.println(team.getShortDetails());
                System.out.println("\nFull Details:");
                System.out.println(team.getFullDetails());
            } else {
                System.out.println("Team with ID " + teamID + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid team ID number.");
        }

        waitForUser();
    }

    /**
     * Generate complete report and write to file
     */
    private void generateCompleteReport() {
        System.out.print("Enter output filename (e.g., final_report.txt): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            filename = "final_report.txt";
        }

        teamList.writeReportToFile(filename);
        waitForUser();
    }

    /**
     * Display summary statistics
     */
    private void displaySummaryStatistics() {
        System.out.println("\n" + teamList.getSummaryStatistics());

        // Display highest and lowest scoring teams
        HackathonTeam highest = teamList.getHighestScoringTeam();
        HackathonTeam lowest = teamList.getLowestScoringTeam();

        if (highest != null) {
            System.out.println("Highest Scoring Team:");
            System.out.println(highest.getShortDetails());
        }

        if (lowest != null) {
            System.out.println("Lowest Scoring Team:");
            System.out.println(lowest.getShortDetails());
        }

        waitForUser();
    }

    /**
     * Display score frequency report
     */
    private void displayScoreFrequency() {
        System.out.println("\n=== SCORE FREQUENCY REPORT ===");
        var frequency = teamList.getScoreFrequency();

        System.out.println("Score | Frequency");
        System.out.println("------|----------");
        for (var entry : frequency.entrySet()) {
            System.out.println(entry.getKey() + "     | " + entry.getValue());
        }

        int totalScores = frequency.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total individual scores: " + totalScores);

        waitForUser();
    }

    /**
     * Wait for user to press Enter before continuing
     */
    private void waitForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        HackathonManager manager = new HackathonManager();
        manager.run();
    }
}