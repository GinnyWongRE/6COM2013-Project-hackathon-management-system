/**
 * WebDevelopmentTeam - Uses simple average scoring
 */
public class WebDevelopmentTeam extends Team {
    public WebDevelopmentTeam(int teamNumber, String teamName, String university, Category category,
                              Competitor teamLeader, Competitor[] teamMember, int[] scores) {
        super(teamNumber, teamName, university, category, teamLeader, teamMember, scores);
    }

    @Override
    public double getOverallScore() {
        if (scores == null || scores.length == 0) return 0;
        double sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return Math.round((sum / scores.length) * 10) / 10.0; // Simple average
    }
}