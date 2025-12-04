import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

/**
 * HackathonManager class - Main coordinator for the hackathon management system
 * Handles user interactions, file operations, and system coordination
 */
public class HackathonManager {
    private TeamList teamList;
    private Scanner scanner;
    private List<Judge> judges = new ArrayList<>();
    private List<JudgeCategoryAssignment> judgeAssignments = new ArrayList<>();

    public HackathonManager() {
        this.teamList = new TeamList();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main coordination method - runs the entire system
     */
    //Stage 5
//    public void run() {
//        System.out.println("=== HACKATHON MANAGEMENT SYSTEM - STAGE 5 ===");
//        System.out.println("Loading team data...\n");
//
//        // Step 1: Load teams from CSV
//        loadTeamData();
//
//        // Step 2: Display main menu
//        displayMainMenu();
//
//        scanner.close();
//    }
    // Stage 6
    public void run() {
        System.out.println("=== HACKATHON MANAGEMENT SYSTEM - STAGE 6 ===");
        System.out.println("Loading team data...\n");

        // Load team data
        loadTeamData();
        initializeJudges();

        System.out.println("Total teams loaded: " + teamList.getTotalTeams() + "\n");

        // Ask user if they want GUI or console
        boolean validChoice = false;

        while (!validChoice) {
            System.out.println("Choose interface:");
            System.out.println("1. Graphical User Interface (GUI)");
            System.out.println("2. Console Interface");
            System.out.print("Choose (1-2): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        // Launch GUI
                        validChoice = true;
                        // Launch GUI
                        HackathonController controller = new HackathonController(teamList);
                        SwingUtilities.invokeLater(() -> {
                            HackathonGUI gui = new HackathonGUI(controller);
                            gui.setVisible(true);
                        });
                        System.out.println("GUI launched successfully!");
                        break;
                    case 2:
                        // Use console interface
                        validChoice = true;
                        displayMainMenu();
                        scanner.close();
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1 or 2.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1 or 2).\n");
            }
        }
    }

    //Load team data from CSV file
    private void loadTeamData() {
        teamList.loadTeamsFromCSV("teams.csv");
        System.out.println("Total teams loaded: " + teamList.getTotalTeams() + "\n");
    }
    private void initializeJudges() {
        judges.add(new Judge(1, "Dr. Ahmad"));
        judges.add(new Judge(2, "Prof. Siti"));
        judges.add(new Judge(3, "Mr. Raj"));
        judges.add(new Judge(4, "Ms. Lee"));

        System.out.println("Initialized " + judges.size() + " judges.");
    }

    /**
     * Display main menu and handle user choices
     */
    private void displayMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== HACKATHON MANAGEMENT SYSTEM ===");
            System.out.println("1. View All Teams");
            System.out.println("2. Search Team by ID");
            System.out.println("3. Add New Team");
            System.out.println("4. Edit Team Details");
            System.out.println("5. Remove Team");
            System.out.println("6. Edit Team Scores");
            System.out.println("7. Assign Judge to Category");
            System.out.println("8. View Judge Assignments");
            System.out.println("9. Generate Reports");
            System.out.println("10. View Summary Statistics");
            System.out.println("11. View Score Frequency");
            System.out.println("12. Exit");
            System.out.print("Choose an option (1-12): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        viewAllTeams();
                        break;
                    case 2:
                        searchTeamByID();
                        break;
                    case 3:
                        addNewTeam();
                        break;
                    case 4:
                        editTeamDetails();
                        break;
                    case 5:
                        removeTeam();
                        break;
                    case 6:
                        editTeamScores();
                        break;
                    case 7:
                        assignJudgeToCategory();
                        break;
                    case 8:
                        viewJudgeAssignments();
                        break;
                    case 9:
                        generateCompleteReport();
                        break;
                    case 10:
                        displaySummaryStatistics();
                        break;
                    case 11:
                        displayScoreFrequency();
                        break;
                    case 12:
                        running = false;
                        System.out.println("Thank you for using Hackathon Management System!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose 1-12.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-12).\n");
            }
        }
    }

    //Display full team report to console
    private void viewAllTeams() {
        System.out.println("\n" + teamList.generateFullReport());
    }

    //Search for a team by ID and display short details
    private void searchTeamByID() {
        System.out.print("Enter Team ID to search: ");

        try {
            int teamID = Integer.parseInt(scanner.nextLine().trim());
            Team team = teamList.getTeamByID(teamID);

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
    }

    //Add new team
    private void addNewTeam() {
        System.out.println("\n=== ADD NEW TEAM ===");

        try {
            // Get team number
            System.out.print("Enter Team Number (unique): ");
            int teamNumber = Integer.parseInt(scanner.nextLine().trim());

            // Check if team number already exists
            if (teamList.getTeamByID(teamNumber) != null) {
                System.out.println("Error: Team number " + teamNumber + " already exists!");
                return;
            }

            // Get basic team info
            System.out.print("Team Name: ");
            String teamName = scanner.nextLine().trim();

            System.out.print("University: ");
            String university = scanner.nextLine().trim();

            // Select category
            System.out.println("Available Categories:");
            System.out.println("1. Web Development");
            System.out.println("2. Artificial Intelligence");
            System.out.println("3. Cybersecurity");
            System.out.println("4. Mobile Applications");
            System.out.print("Select category (1-4): ");
            int categoryChoice = Integer.parseInt(scanner.nextLine().trim());

            String categoryName;
            switch (categoryChoice) {
                case 1: categoryName = "Web Development"; break;
                case 2: categoryName = "Artificial Intelligence"; break;
                case 3: categoryName = "Cybersecurity"; break;
                case 4: categoryName = "Mobile Applications"; break;
                default:
                    System.out.println("Invalid category. Defaulting to Web Development.");
                    categoryName = "Web Development";
            }

            Category category = new Category(teamList.getTotalTeams() + 1, categoryName);

            // Get team leader details
            System.out.println("\n=== TEAM LEADER DETAILS ===");
            System.out.print("Leader Name: ");
            String leaderName = scanner.nextLine().trim();

            System.out.print("Leader Email: ");
            String leaderEmail = scanner.nextLine().trim();

            System.out.print("Leader Student ID: ");
            int studentID = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Leader Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine().trim());

            Competitor leader = new Competitor(studentID, leaderName, leaderEmail, dob, true);

            // Get team members
            System.out.print("\nNumber of additional team members (0-4): ");
            int numMembers = Integer.parseInt(scanner.nextLine().trim());
            Competitor[] members = new Competitor[numMembers];

            for (int i = 0; i < numMembers; i++) {
                System.out.println("\nMember " + (i + 1) + ":");
                System.out.print("Name: ");
                String memberName = scanner.nextLine().trim();

                System.out.print("Email: ");
                String memberEmail = scanner.nextLine().trim();

                System.out.print("Student ID: ");
                int memberStudentID = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Date of Birth (YYYY-MM-DD): ");
                LocalDate memberDOB = LocalDate.parse(scanner.nextLine().trim());

                members[i] = new Competitor(memberStudentID, memberName, memberEmail, memberDOB, false);
            }

            // Get initial scores
            System.out.println("\n=== INITIAL SCORES ===");
            int[] scores = new int[5];
            for (int i = 0; i < 5; i++) {
                System.out.print("Score " + (i + 1) + " (1-5): ");
                scores[i] = Integer.parseInt(scanner.nextLine().trim());
                if (scores[i] < 1 || scores[i] > 5) {
                    System.out.println("Invalid score. Must be between 1-5. Setting to 3.");
                    scores[i] = 3;
                }
            }

            // Create appropriate team type based on category
            Team newTeam;
            if (categoryName.equals("Artificial Intelligence")) {
                newTeam = new AITeam(teamNumber, teamName, university, category, leader, members, scores);
            } else {
                newTeam = new WebDevelopmentTeam(teamNumber, teamName, university, category, leader, members, scores);
            }

            // Add to team list
            teamList.addTeam(newTeam);

            System.out.println("\n✅ Team created successfully!");
            System.out.println("Team ID: " + teamNumber);
            System.out.println("Overall Score: " + newTeam.getOverallScore());

        } catch (Exception e) {
            System.out.println("Error creating team: " + e.getMessage());
        }
    }

    //Edit team details
    private void editTeamDetails() {
        System.out.print("\nEnter Team ID to edit: ");

        try {
            int teamID = Integer.parseInt(scanner.nextLine().trim());
            Team team = teamList.getTeamByID(teamID);

            if (team != null) {
                System.out.println("\nEditing Team: " + team.getTeamName());
                System.out.println("Current Details:");
                System.out.println("1. Team Name: " + team.getTeamName());
                System.out.println("2. University: " + team.getUniversity());
                System.out.println("3. Category: " + team.getCategory().getCategoryName());

                System.out.print("\nWhat would you like to edit? (1-3, or 0 to cancel): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        System.out.print("New Team Name: ");
                        String newName = scanner.nextLine().trim();
                        team.setTeamName(newName);
                        System.out.println("Team name updated!");
                        break;

                    case 2:
                        System.out.print("New University: ");
                        String newUni = scanner.nextLine().trim();
                        team.setUniversity(newUni);
                        System.out.println("University updated!");
                        break;

                    case 3:
                        System.out.println("Note: Changing category may require changing team type.");
                        System.out.println("Current category: " + team.getCategory().getCategoryName());
                        System.out.println("This feature requires more complex implementation.");
                        System.out.println("Please remove and recreate team with new category.");
                        break;

                    case 0:
                        System.out.println("Edit cancelled.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Team with ID " + teamID + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid team ID.");
        }
    }

    private void removeTeam() {
        System.out.print("\nEnter Team ID to remove: ");

        try {
            int teamID = Integer.parseInt(scanner.nextLine().trim());
            Team team = teamList.getTeamByID(teamID);

            if (team != null) {
                System.out.print("Are you sure you want to remove team '" + team.getTeamName() + "'? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();

                if (confirm.equals("yes") || confirm.equals("y")) {
                    if (teamList.removeTeam(teamID)) {
                        System.out.println("✅ Team removed successfully!");
                    } else {
                        System.out.println("❌ Failed to remove team.");
                    }
                } else {
                    System.out.println("Removal cancelled.");
                }
            } else {
                System.out.println("Team with ID " + teamID + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid team ID.");
        }
    }

    // Edit the score for the team only
    private void editTeamScores() {
        System.out.print("\nEnter Team ID to edit scores: ");

        try {
            int teamID = Integer.parseInt(scanner.nextLine().trim());
            Team team = teamList.getTeamByID(teamID);

            if (team != null) {
                System.out.println("\nEditing scores for: " + team.getTeamName());
                System.out.println("Current scores: " + java.util.Arrays.toString(team.getScoreArray()));
                System.out.println("Current overall score: " + team.getOverallScore());

                int[] newScores = new int[5];
                System.out.println("\nEnter new scores (1-5):");

                for (int i = 0; i < 5; i++) {
                    System.out.print("Score " + (i + 1) + ": ");
                    int score = Integer.parseInt(scanner.nextLine().trim());

                    if (score < 1 || score > 5) {
                        System.out.println("Invalid score. Must be 1-5. Keeping current value.");
                        newScores[i] = team.getScoreArray()[i];
                    } else {
                        newScores[i] = score;
                    }
                }

                team.setScores(newScores);
                System.out.println("\n✅ Scores updated successfully!");
                System.out.println("New overall score: " + team.getOverallScore());

            } else {
                System.out.println("Team with ID " + teamID + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid team ID.");
        }
    }


    // Add these methods
    private void assignJudgeToCategory() {
        System.out.println("\n=== ASSIGN JUDGE TO CATEGORY ===");

        try {
            // List existing judges or create new
            if (judges.isEmpty()) {
                System.out.println("No judges exist. Creating new judge...");
                System.out.print("Judge Name: ");
                String judgeName = scanner.nextLine().trim();

                Judge newJudge = new Judge(judges.size() + 1, judgeName);
                judges.add(newJudge);
                System.out.println("Created Judge ID: " + newJudge.getJudgeID());
            }

            // List judges
            System.out.println("\nAvailable Judges:");
            for (Judge judge : judges) {
                System.out.println(judge.getJudgeID() + ". " + judge.getJudgeName());
            }

            System.out.print("\nSelect Judge ID: ");
            int judgeID = Integer.parseInt(scanner.nextLine().trim());
            Judge selectedJudge = findJudgeByID(judgeID);

            if (selectedJudge == null) {
                System.out.println("Invalid Judge ID.");
                return;
            }

            // List categories
            System.out.println("\nAvailable Categories:");
            Set<String> categories = new HashSet<>();
            for (Team team : teamList.getAllTeams()) {
                categories.add(team.getCategory().getCategoryName());
            }

            int i = 1;
            List<String> categoryList = new ArrayList<>(categories);
            for (String cat : categoryList) {
                System.out.println(i++ + ". " + cat);
            }

            System.out.print("\nSelect Category (by number): ");
            int catChoice = Integer.parseInt(scanner.nextLine().trim()) - 1;

            if (catChoice < 0 || catChoice >= categoryList.size()) {
                System.out.println("Invalid category selection.");
                return;
            }

            String categoryName = categoryList.get(catChoice);
            Category category = new Category(catChoice + 1, categoryName);

            // Assign judge
            selectedJudge.assignToCategory(category);

            // Create assignment record
            JudgeCategoryAssignment assignment = new JudgeCategoryAssignment(
                    judgeAssignments.size() + 1,
                    selectedJudge,
                    category,
                    LocalDate.now(),
                    "Console Admin"
            );
            judgeAssignments.add(assignment);

            System.out.println("\n✅ Judge assigned successfully!");
            System.out.println("Judge: " + selectedJudge.getJudgeName());
            System.out.println("Category: " + categoryName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Judge findJudgeByID(int judgeID) {
        for (Judge judge : judges) {
            if (judge.getJudgeID() == judgeID) {
                return judge;
            }
        }
        return null;
    }

    private void viewJudgeAssignments() {
        System.out.println("\n=== JUDGE ASSIGNMENTS ===");

        if (judgeAssignments.isEmpty()) {
            System.out.println("No judge assignments found.");
            return;
        }

        for (JudgeCategoryAssignment assignment : judgeAssignments) {
            System.out.println("\nAssignment ID: " + assignment.getAssignmentID());
            System.out.println("Judge: " + assignment.getJudge().getJudgeName());
            System.out.println("Category: " + assignment.getCategory().getCategoryName());
            System.out.println("Assigned on: " + assignment.getAssignedDate());
            System.out.println("Assigned by: " + assignment.getAssignedBy());
            System.out.println("------------------------");
        }
    }

    //Generate complete report and write to file
    private void generateCompleteReport() {
        System.out.print("Enter output filename (e.g., final_report.txt): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            filename = "final_report.txt";
        }

        teamList.writeReportToFile(filename);    }

    //Display summary statistics
    private void displaySummaryStatistics() {
        System.out.println("\n" + teamList.getSummaryStatistics());

        // Display highest and lowest scoring teams
        Team highest = teamList.getHighestScoringTeam();
        Team lowest = teamList.getLowestScoringTeam();

        if (highest != null) {
            System.out.println("Highest Scoring Team:");
            System.out.println(highest.getShortDetails());
        }

        if (lowest != null) {
            System.out.println("Lowest Scoring Team:");
            System.out.println(lowest.getShortDetails());
        }
    }

    //Display score frequency report
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
    }

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        HackathonManager manager = new HackathonManager();
        manager.run();
    }
}