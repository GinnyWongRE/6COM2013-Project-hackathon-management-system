import java.util.*;

// File: Judge.java
public class Judge {
    private int judgeID;
    private String judgeName;
    private List<Category> assignedCategories;

    public Judge(int judgeID, String judgeName) {
        this.judgeID = judgeID;
        this.judgeName = judgeName;
        this.assignedCategories = new ArrayList<>();
    }

    // Getters and setters
    public int getJudgeID() { return judgeID; }
    public String getJudgeName() { return judgeName; }
    public List<Category> getAssignedCategories() { return assignedCategories; }

    public void assignToCategory(Category category) {
        if (!assignedCategories.contains(category)) {
            assignedCategories.add(category);
        }
    }

    public void removeFromCategory(Category category) {
        assignedCategories.remove(category);
    }
}