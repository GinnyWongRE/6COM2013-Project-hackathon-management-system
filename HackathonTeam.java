public class HackathonTeam {
    private int teamNumber;
    private String teamName;
    private String university;
    private String category;

    public HackathonTeam(int teamNumber, String teamName, String university, String category) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.university = university;
        this.category = category;
    }

    public double getOverallScore() {
        return 5;
    }

    public String getFullDetails() {
        return "Team ID " + teamNumber + ", name " + teamName + " (" + university + ").\n" +
                teamName + " is competing in the " + category
                + " category and received scores [], resulting in an overall score of " + getOverallScore();
    }
}
