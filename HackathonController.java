import java.util.ArrayList;
import java.util.Comparator;

/**
 * Controller for the Hackathon Management System
 * Mediates between the Model (TeamList) and View (GUI)
 */
public class HackathonController {
    private TeamList teamList;

    public HackathonController(TeamList teamList) {
        this.teamList = teamList;
    }

    // Team management methods
    public ArrayList<Team> getAllTeams() {
        return teamList.getAllTeams();
    }

    public Team getTeamByID(int teamID) {
        return teamList.getTeamByID(teamID);
    }

    public boolean removeTeam(int teamID) {
        return teamList.removeTeam(teamID);
    }

    // Sorting methods
    public void sortTeamsByScore() {
        ArrayList<Team> teams = teamList.getAllTeams();
        teams.sort((t1, t2) -> Double.compare(t2.getOverallScore(), t1.getOverallScore()));
    }

    public void sortTeamsByName() {
        ArrayList<Team> teams = teamList.getAllTeams();
        teams.sort((t1, t2) -> t1.getTeamName().compareToIgnoreCase(t2.getTeamName()));
    }

    public void sortTeamsByCategory() {
        ArrayList<Team> teams = teamList.getAllTeams();
        teams.sort((t1, t2) -> t1.getCategory().getCategoryName()
                .compareToIgnoreCase(t2.getCategory().getCategoryName()));
    }

    // Filtering methods
    public ArrayList<Team> getTeamsByCategory(String categoryName) {
        ArrayList<Team> filtered = new ArrayList<>();
        for (Team team : teamList.getAllTeams()) {
            if (team.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                filtered.add(team);
            }
        }
        return filtered;
    }

    public ArrayList<Team> getTeamsByMinScore(double minScore) {
        ArrayList<Team> filtered = new ArrayList<>();
        for (Team team : teamList.getAllTeams()) {
            if (team.getOverallScore() >= minScore) {
                filtered.add(team);
            }
        }
        return filtered;
    }

    // Score editing
    public void updateTeamScores(int teamID, int[] newScores) {
        Team team = teamList.getTeamByID(teamID);
        if (team != null) {
            team.setScores(newScores);
        }
    }

    // Reporting
    public String generateFullReport() {
        return teamList.generateFullReport();
    }

    public String getSummaryStatistics() {
        return teamList.getSummaryStatistics();
    }

    public void writeReportToFile(String filename) {
        teamList.writeReportToFile(filename);
    }

    public Team getHighestScoringTeam() {
        return teamList.getHighestScoringTeam();
    }
}