import java.util.Arrays;

public class HackathonTeam {
    private int teamNumber;
    private String teamName;
    private String university;
    private Category category;
    private Competitor teamLeader;
    private Competitor[] teamMember;
    private int[] scores;

    public HackathonTeam(int teamNumber, String teamName, String university, Category category,
                         Competitor teamLeader, Competitor[] teamMember, int[] scores) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.university = university;
        this.category = category;
        this.teamLeader = teamLeader;
        this.teamMember = teamMember;
        this.scores = scores;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Competitor[] getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(Competitor[] teamMember) {
        this.teamMember = teamMember;
    }

    public Competitor getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(Competitor teamLeader) {
        this.teamLeader = teamLeader;
    }

    public int[] getScoreArray() {
        return scores;
    }

    public void setScoreArray(int[] scores) {
        this.scores = scores;
    }

    public double getOverallScore() {
        //return 5; // Stage 4

        if (scores == null || scores.length == 0) {
            return 0;
        }

        // Calculate weighted average (more complex than simple average)
        double sum = 0;
        double totalWeight = 0;

        // Assign different weights to different score positions
        // Higher weights for middle scores (judges tend to be more careful with middle scores)
        double[] weights = {0.15, 0.25, 0.20, 0.25, 0.15}; // weights for 5 scores

        for (int i = 0; i < scores.length; i++) {
            double weight = (i < weights.length) ? weights[i] : 1.0; // default weight if more scores
            sum += scores[i] * weight;
            totalWeight += weight;
        }

        double average = sum / totalWeight;

        // Round to 1 decimal place
        return Math.round(average * 10) / 10.0;
    }

    // Method that get full details.
    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Team ID ").append(teamNumber)
                .append(", name ").append(teamName).append(" (").append(university).append(").\n")
                .append(teamName).append(" is competing in the ").append(category.getCategoryName())
                .append(" category and received scores ")
                .append(Arrays.toString(scores))
                .append(", resulting in an overall score of ").append(getOverallScore());
        return details.toString();
    }

    // Method to get short details.
    public String getShortDetails() {
        // Extract initials from team name (first letter of each word)
        String[] words = teamName.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                initials.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        return "TID " + teamNumber + " (" + initials.toString() + ") has an overall score of " + getOverallScore();
    }

    public String toString(){
        return teamName + "(Team: " + teamNumber + ")";
    }

}
