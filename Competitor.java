import java.time.LocalDate;

public class Competitor {
    private int studentID;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private boolean isLeader;

    public Competitor(int studentID, String name, String email, boolean isLeader, LocalDate dateOfBirth) {
        this.studentID = studentID;
        this.name = name;
        this.email = email;
        this.isLeader = isLeader;
        this.dateOfBirth = dateOfBirth;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public boolean isEligible(){
        return studentID > 0;
    }

    public String toString(){
        return name + (isLeader ? " (Leader)" : "");
    }
}
