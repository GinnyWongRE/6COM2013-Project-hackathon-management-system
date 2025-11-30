import java.time.LocalDate;
import java.util.*;
import java.io.*;

/**
 * TeamList class to manage ArrayList of HackathonTeam objects
 * Handles team storage, searching, and reporting operations
 */
public class TeamList {
    private ArrayList<Team> teams;

    public TeamList() {
        this.teams = new ArrayList<>();
    }

    /**
     * Load teams from CSV file
     */
    public void loadTeamsFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header row
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 8) {
                    // Parse team basic info
                    int teamNumber = Integer.parseInt(data[0].trim());
                    String teamName = data[1].trim();
                    String university = data[2].trim();
                    String categoryName = data[3].trim();

                    // Create category
                    Category category = new Category(teams.size() + 1, categoryName);

                    // Create team leader
                    Competitor leader = new Competitor(
                            Integer.parseInt(data[6].trim()),
                            data[4].trim(),
                            data[5].trim(),
                            LocalDate.parse(data[7].trim()),
                            true
                    );

                    // Parse scores (remaining columns)
                    int[] scores = new int[data.length - 8];
                    for (int i = 8; i < data.length; i++) {
                        scores[i - 8] = Integer.parseInt(data[i].trim());
                    }

                    // Create team with empty members array for now
                    Competitor[] members = {};
                    Team team;

//                    Team team = new Team(
//                            teamNumber, teamName, university, category, leader, members, scores
//                    );

                    // Create appropriate subclass based on category
                    if (categoryName.equalsIgnoreCase("Artificial Intelligence")) {
                        team = new AITeam(teamNumber, teamName, university, category, leader, members, scores);
                    } else {
                        team = new WebDevelopmentTeam(teamNumber, teamName, university, category, leader, members, scores);
                    }

                    teams.add(team);
                }
            }
            System.out.println("Successfully loaded " + teams.size() + " teams from " + filename);

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number in CSV: " + e.getMessage());
        }
    }

    // Basic ArrayList operations
    public void addTeam(Team team) {
        teams.add(team);
    }

    public boolean removeTeam(int teamNumber) {
        return teams.removeIf(team -> team.getTeamNumber() == teamNumber);
    }

    public Team getTeamByID(int teamID) {
        for (Team team : teams) {
            if (team.getTeamNumber() == teamID) {
                return team;
            }
        }
        return null;
    }

    public ArrayList<Team> getAllTeams() {
        return new ArrayList<>(teams); // Return copy to prevent external modification
    }

    public int getTotalTeams() {
        return teams.size();
    }

    // Reporting methods that will be called by the manager class
    public String generateFullReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== HACKATHON TEAMS FULL REPORT ===\n\n");

        for (Team team : teams) {
            report.append(team.getFullDetails()).append("\n\n");
        }

        return report.toString();
    }

    public Team getHighestScoringTeam() {
        if (teams.isEmpty()) return null;

        Team highest = teams.get(0);
        for (Team team : teams) {
            if (team.getOverallScore() > highest.getOverallScore()) {
                highest = team;
            }
        }
        return highest;
    }

    /**
     * Get the team with the lowest overall score
     */
    public Team getLowestScoringTeam() {
        if (teams.isEmpty()) return null;

        Team lowest = teams.get(0);
        for (Team team : teams) {
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
        for (Team team : teams) {
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
        for (Team team : teams) {
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
        for (Team team : teams) {
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
        for (Team team : teams) {
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
        for (Team team : teams) {
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
        Team highest = getHighestScoringTeam();
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