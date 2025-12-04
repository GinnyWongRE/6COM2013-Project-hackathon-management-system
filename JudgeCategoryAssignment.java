// File: JudgeCategoryAssignment.java
import java.time.LocalDate;

public class JudgeCategoryAssignment {
    private int assignmentID;
    private Judge judge;
    private Category category;
    private LocalDate assignedDate;
    private String assignedBy; // organizer name

    public JudgeCategoryAssignment(int assignmentID, Judge judge, Category category,
                                   LocalDate assignedDate, String assignedBy) {
        this.assignmentID = assignmentID;
        this.judge = judge;
        this.category = category;
        this.assignedDate = assignedDate;
        this.assignedBy = assignedBy;
    }

    // Getters
    public int getAssignmentID() { return assignmentID; }
    public Judge getJudge() { return judge; }
    public Category getCategory() { return category; }
    public LocalDate getAssignedDate() { return assignedDate; }
    public String getAssignedBy() { return assignedBy; }
}