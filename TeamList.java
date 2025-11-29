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