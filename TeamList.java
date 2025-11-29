import java.time.LocalDate;
import java.util.*;
import java.io.*;

/**
 * TeamList class to manage ArrayList of HackathonTeam objects
 * Handles team storage, searching, and reporting operations
 */
public class TeamList {
    private ArrayList<HackathonTeam> teams;

    public TeamList() {
        this.teams = new ArrayList<>();
    }

    // CSV Reading Method
    public void loadTeamsFromCSV(String filename) {
        int lineNumber = 0;
        int successfulTeams = 0;
        int errorCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Set<Integer> usedTeamNumbers = new HashSet<>(); // Track team numbers for duplicates

            while ((line = br.readLine()) != null) {
                lineNumber++;

                // Skip empty lines and header
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (lineNumber == 1) {
                    continue; // Skip header row
                }

                try {
                    String[] data = line.split(",");

                    // Error Check 1: Validate minimum required fields
                    if (data.length < 8) {
                        System.err.println("Error on line " + lineNumber + ": Insufficient data fields. Expected at least 8, found " + data.length);
                        errorCount++;
                        continue;
                    }

                    // Error Check 2: Validate team number
                    int teamNumber;
                    try {
                        teamNumber = Integer.parseInt(data[0].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Error on line " + lineNumber + ": Invalid team number '" + data[0] + "'");
                        errorCount++;
                        continue;
                    }

                    // Error Check 3: Check for duplicate team numbers
                    if (usedTeamNumbers.contains(teamNumber)) {
                        System.err.println("Error on line " + lineNumber + ": Duplicate team number " + teamNumber);
                        errorCount++;
                        continue;
                    }
                    usedTeamNumbers.add(teamNumber);

                    // Error Check 4: Validate required string fields
                    String teamName = data[1].trim();
                    String university = data[2].trim();
                    String categoryName = data[3].trim();
                    String leaderName = data[4].trim();
                    String leaderEmail = data[5].trim();

                    if (teamName.isEmpty() || university.isEmpty() || categoryName.isEmpty() ||
                            leaderName.isEmpty() || leaderEmail.isEmpty()) {
                        System.err.println("Error on line " + lineNumber + ": Required field is empty");
                        errorCount++;
                        continue;
                    }

                    // Error Check 5: Validate leader student ID
                    int leaderStudentID;
                    try {
                        leaderStudentID = Integer.parseInt(data[6].trim());
                        if (leaderStudentID <= 0) {
                            throw new NumberFormatException("Student ID must be positive");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error on line " + lineNumber + ": Invalid student ID '" + data[6] + "'");
                        errorCount++;
                        continue;
                    }

                    // Error Check 6: Validate date of birth
                    LocalDate leaderDOB;
                    try {
                        leaderDOB = LocalDate.parse(data[7].trim());
                        // Additional check: leader should be at least 16 years old
                        LocalDate minDOB = LocalDate.now().minusYears(16);
                        if (leaderDOB.isAfter(minDOB)) {
                            System.err.println("Warning on line " + lineNumber + ": Leader is under 16 years old");
                            // Continue anyway, just a warning
                        }
                    } catch (Exception e) {
                        System.err.println("Error on line " + lineNumber + ": Invalid date format '" + data[7] + "'. Expected format: YYYY-MM-DD");
                        errorCount++;
                        continue;
                    }

                    // Error Check 7: Validate scores
                    int[] scores = new int[data.length - 8];
                    boolean validScores = true;

                    if (scores.length < 4 || scores.length > 6) {
                        System.err.println("Error on line " + lineNumber + ": Invalid number of scores. Expected 4-6, found " + scores.length);
                        errorCount++;
                        continue;
                    }

                    for (int i = 8; i < data.length; i++) {
                        try {
                            int score = Integer.parseInt(data[i].trim());
                            if (score < 1 || score > 5) {
                                System.err.println("Error on line " + lineNumber + ": Score out of range (1-5): " + score);
                                validScores = false;
                                break;
                            }
                            scores[i - 8] = score;
                        } catch (NumberFormatException e) {
                            System.err.println("Error on line " + lineNumber + ": Invalid score format '" + data[i] + "'");
                            validScores = false;
                            break;
                        }
                    }

                    if (!validScores) {
                        errorCount++;
                        continue;
                    }

                    // All validations passed - create the team
                    Category category = new Category(teams.size() + 1, categoryName);
                    Competitor leader = new Competitor(leaderStudentID, leaderName, leaderEmail, leaderDOB, true);
                    Competitor[] members = {}; // Simplified for Stage 5

                    HackathonTeam team = new HackathonTeam(teamNumber, teamName, university, category, leader, members, scores);
                    teams.add(team);
                    successfulTeams++;

                } catch (Exception e) {
                    System.err.println("Unexpected error processing line " + lineNumber + ": " + e.getMessage());
                    errorCount++;
                }
            }

            // Summary report
            System.out.println("CSV loading completed:");
            System.out.println("  - Successfully loaded: " + successfulTeams + " teams");
            System.out.println("  - Errors encountered: " + errorCount);
            System.out.println("  - Total teams in system: " + teams.size());

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Basic ArrayList operations
    public void addTeam(HackathonTeam team) {
        teams.add(team);
    }

    public boolean removeTeam(int teamNumber) {
        return teams.removeIf(team -> team.getTeamNumber() == teamNumber);
    }

    public HackathonTeam getTeamByID(int teamID) {
        for (HackathonTeam team : teams) {
            if (team.getTeamNumber() == teamID) {
                return team;
            }
        }
        return null;
    }

    public ArrayList<HackathonTeam> getAllTeams() {
        return new ArrayList<>(teams); // Return copy to prevent external modification
    }

    public int getTotalTeams() {
        return teams.size();
    }

    // Reporting methods that will be called by the manager class
    public String generateFullReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== HACKATHON TEAMS FULL REPORT ===\n\n");

        for (HackathonTeam team : teams) {
            report.append(team.getFullDetails()).append("\n\n");
        }

        return report.toString();
    }

    public HackathonTeam getHighestScoringTeam() {
        if (teams.isEmpty()) return null;

        HackathonTeam highest = teams.get(0);
        for (HackathonTeam team : teams) {
            if (team.getOverallScore() > highest.getOverallScore()) {
                highest = team;
            }
        }
        return highest;
    }

    /**
     * Get the team with the lowest overall score
     */
    public HackathonTeam getLowestScoringTeam() {
        if (teams.isEmpty()) return null;

        HackathonTeam lowest = teams.get(0);
        for (HackathonTeam team : teams) {
            if (team.getOverallScore() < lowest.getOverallScore()) {
                lowest = team;
            }
        }
        return lowest;
    }

    /**
     * Calculate average overall score across all teams
     */
    public double getAverageOverallScore() {
        if (teams.isEmpty()) return 0;

        double total = 0;
        for (HackathonTeam team : teams) {
            total += team.getOverallScore();
        }
        return Math.round((total / teams.size()) * 10) / 10.0;
    }

    /**
     * Get minimum overall score across all teams
     */
    public double getMinOverallScore() {
        if (teams.isEmpty()) return 0;

        double min = teams.get(0).getOverallScore();
        for (HackathonTeam team : teams) {
            if (team.getOverallScore() < min) {
                min = team.getOverallScore();
            }
        }
        return min;
    }

    /**
     * Get maximum overall score across all teams
     */
    public double getMaxOverallScore() {
        if (teams.isEmpty()) return 0;

        double max = teams.get(0).getOverallScore();
        for (HackathonTeam team : teams) {
            if (team.getOverallScore() > max) {
                max = team.getOverallScore();
            }
        }
        return max;
    }

    /**
     * Generate frequency report of individual scores (1-5)
     * Counts how many times each score appears across all teams and all criteria
     */
    public Map<Integer, Integer> getScoreFrequency() {
        Map<Integer, Integer> frequency = new TreeMap<>();

        // Initialize frequency map with scores 1-5
        for (int i = 1; i <= 5; i++) {
            frequency.put(i, 0);
        }

        // Count occurrences of each score
        for (HackathonTeam team : teams) {
            int[] scores = team.getScoreArray();
            for (int score : scores) {
                if (score >= 1 && score <= 5) {
                    frequency.put(score, frequency.get(score) + 1);
                }
            }
        }

        return frequency;
    }

    /**
     * Get summary statistics as a formatted string
     */
    public String getSummaryStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== SUMMARY STATISTICS ===\n");
        stats.append("Total Teams: ").append(getTotalTeams()).append("\n");
        stats.append("Average Overall Score: ").append(getAverageOverallScore()).append("\n");
        stats.append("Highest Overall Score: ").append(getMaxOverallScore()).append("\n");
        stats.append("Lowest Overall Score: ").append(getMinOverallScore()).append("\n");

        // Additional statistics
        int totalScores = 0;
        for (HackathonTeam team : teams) {
            totalScores += team.getScoreArray().length;
        }
        stats.append("Total Individual Scores: ").append(totalScores).append("\n");

        return stats.toString();
    }

    /**
     * Generate complete final report with all required components
     */
    public String generateFinalReport() {
        StringBuilder report = new StringBuilder();

        // 1. Table of teams with full details
        report.append(generateFullReport()).append("\n");

        // 2. Team with highest overall score
        HackathonTeam highest = getHighestScoringTeam();
        if (highest != null) {
            report.append("=== HIGHEST SCORING TEAM ===\n");
            report.append(highest.getFullDetails()).append("\n\n");
        }

        // 3. Summary statistics
        report.append(getSummaryStatistics()).append("\n");

        // 4. Frequency report
        report.append("=== SCORE FREQUENCY REPORT ===\n");
        Map<Integer, Integer> frequency = getScoreFrequency();
        report.append("Score | Frequency\n");
        report.append("------|----------\n");
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            report.append(entry.getKey()).append("     | ").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }

    /**
     * Write report to text file
     */
    public void writeReportToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(generateFinalReport());
            System.out.println("Report successfully written to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing report to file: " + e.getMessage());
        }
    }
}