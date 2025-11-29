import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main (String[] args){
        System.out.println("=== HACKATHON MANAGEMENT SYSTEM ===\n");

        // Create category objects.
        Category webDev = new Category(1, "Web Development");
        Category cybSec = new Category(1,"Cybersecurity");
        Category ai = new Category(3, "Artificial Intelligence");
        Category mobApp = new Category(4, "Mobile Application");

        // Create competitor for Team 100
        Competitor leader100 = new Competitor(10001, "Ahmad Faris", "ahmad@um.edu.my", LocalDate.of(2000,5,15),true);
        Competitor[] members100 ={
                new Competitor(10002, "Siti Aminah", "siti@um.edu.my", LocalDate.of(2001,3,20), false),
                new Competitor(10003, "Raj Kumar", "raj@um.edu.my", LocalDate.of(2000,8,10), false),
                new Competitor(10004, "Wei Chen", "wei@um.edu.my", LocalDate.of(2001,1,5), false)
        };
        int[] scores100 = {4, 5, 4, 5, 5};

        // Create Team 100
        HackathonTeam team100 = new HackathonTeam(100, "Team Innovate", "University of Malaya",
                webDev, leader100, members100, scores100);

        // Create competitor for Team 101
        Competitor leader101 = new Competitor(20001, "Sarah Lim", "sarah@utm.edu.my", LocalDate.of(1999,12,8),true);
        Competitor[] members101 ={
                new Competitor(20002, "Mike Tan", "mike@utm.edu.my", LocalDate.of(2000,7,22), false),
                new Competitor(20003, "Priya Devi", "priya@utm.edu.my", LocalDate.of(2001,4,30), false)
        };
        int[] scores101 = {5, 5, 5, 4, 5};

        // Create Team 101
        HackathonTeam team101 = new HackathonTeam(101, "Cyber Titans", "University Teknologi Malaysia",
                cybSec, leader101, members101, scores101);

        // Create competitor for Team 102
        Competitor leader102 = new Competitor(30001, "David Wong", "david@ukm.edu.my", LocalDate.of(2000,9,18),true);
        Competitor[] members102 ={
                new Competitor(30002, "Aisha Hassan", "aisha@ukm.edu.my", LocalDate.of(2001,2,14), false),
                new Competitor(30003, "James Lee", "james@ukm.edu.my", LocalDate.of(2000,11,13), false),
                new Competitor(30004, "Nurul Huda", "nurul@ukm.edu.my", LocalDate.of(2001,6,25), false)
        };
        int[] scores102 = {4, 5, 4, 5, 5};

        //Create Team 102
        HackathonTeam team102 = new HackathonTeam(102, "AI Pioneers", "University Kebangsaan Malaysia",
                ai, leader102, members102, scores102);

        System.out.println("=== TEST 1: FULL DETAILS ===");
        System.out.println(team100.getFullDetails() + "\n");
        System.out.println(team101.getFullDetails() + "\n");
        System.out.println(team102.getFullDetails() + "\n");

        System.out.println("=== TEST 2: SHORT DETAILS ===");
        System.out.println(team100.getShortDetails());
        System.out.println(team101.getShortDetails());
        System.out.println(team102.getShortDetails() + "\n");

        System.out.println("=== TEST 3: SCORE ARRAYS ===");
        System.out.println("Team 100 scores: " + Arrays.toString(team100.getScoreArray()));
        System.out.println("Team 101 scores: " + Arrays.toString(team101.getScoreArray()));
        System.out.println("Team 102 scores: " + Arrays.toString(team102.getScoreArray()) + "\n");

        System.out.println("=== TEST 4: OVERALL SCORES ===");
        System.out.println("Team 100 overall score: " + team100.getOverallScore());
        System.out.println("Team 101 overall score: " + team101.getOverallScore());
        System.out.println("Team 102 overall score: " + team102.getOverallScore() + "\n");

        System.out.println("=== TEST 5: GETTERS AND SETTERS ===");
        System.out.println("Team 100 name: " + team100.getTeamName());
        System.out.println("Team 101 university: " + team101.getUniversity());
        System.out.println("Team 102 category: " + team102.getCategory().getCategoryName());
        System.out.println("Team 100 leader: " + team100.getTeamLeader().getName() + "\n");

        System.out.println("=== TEST 6: COMPETITOR ELIGIBILITY ===");
        System.out.println("Team 100 leader eligibility: " + leader100.isEligible());
        System.out.println("Team 101 leader eligibility: " + leader101.isEligible());
        System.out.println("Team 102 leader eligilibity; " + leader102.isEligible() + "\n");

        System.out.println("=== TEST 7: TOSTRING METHOD ===");
        System.out.println("Team 100: " + team100.toString());
        System.out.println("Team 101: " + team101.toString());
        System.out.println("Team 102: " + team102.toString());
        System.out.println("Web Dev Category: " + webDev.toString());
        System.out.println("Team 100 Leader: " + leader100.toString());

        System.out.println("\n=== STAGE 4 TEST COMPLETED ===");

        System.out.println("=== HACKATHON MANAGEMENT SYSTEM - STAGE 5 ===");
        System.out.println("Starting Hackathon Manager...\n");

        // Create and run the manager
        HackathonManager manager = new HackathonManager();
        manager.run();
    }
}
