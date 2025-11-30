/**
 * AITeam - Uses weighted average scoring
 */
public class AITeam extends Team {
    public AITeam(int teamNumber, String teamName, String university, Category category,
                  Competitor teamLeader, Competitor[] teamMember, int[] scores) {
        super(teamNumber, teamName, university, category, teamLeader, teamMember, scores);
    }

    @Override
    public double getOverallScore() {
        if (scores == null || scores.length == 0) return 0;

        // Weighted average for AI teams
        double[] weights = {0.15, 0.25, 0.20, 0.25, 0.15};
        double sum = 0;
        double totalWeight = 0;

        for (int i = 0; i < scores.length; i++) {
            double weight = (i < weights.length) ? weights[i] : 1.0;
            sum += scores[i] * weight;
            totalWeight += weight;
        }

        return Math.round((sum / totalWeight) * 10) / 10.0;
    }
}