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
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header row
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 8) { // Ensure we have enough data
                    // Parse team basic info
                    int teamNumber = Integer.parseInt(data[0].trim());
                    String teamName = data[1].trim();
                    String university = data[2].trim();
                    String categoryName = data[3].trim();

                    // Create category
                    Category category = new Category(teams.size() + 1, categoryName);

                    // Create team leader
                    Competitor leader = new Competitor(
                            Integer.parseInt(data[6].trim()), // leader student ID
                            data[4].trim(), // leader name
                            data[5].trim(), // leader email
                            LocalDate.parse(data[7].trim()), // leader DOB
                            true
                    );

                    // Parse scores (remaining columns)
                    int[] scores = new int[data.length - 8];
                    for (int i = 8; i < data.length; i++) {
                        scores[i - 8] = Integer.parseInt(data[i].trim());
                    }

                    // Create team with empty members array for now (simplified)
                    Competitor[] members = {}; // We'll handle multiple members in enhancement

                    HackathonTeam team = new HackathonTeam(
                            teamNumber, teamName, university, category, leader, members, scores
                    );

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

    // More methods to be added for statistics and frequency...
}