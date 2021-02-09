import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Java solution implementing Comparable interface verdict Accepted
 * 
 * @author Diego Satoba
 */
class Main {
    static class Team implements Comparable<Team> {
        String name;
        int wins;
        int losses;
        int ties;
        int goalsScored;
        int goalsAgainst;

        public Team(String name) {
            this.name = name;
        }

        public int getPoints() {
            return wins * 3 + ties;
        }

        public int getGamesPlayed() {
            return wins + ties + losses;
        }

        public int getGoalDifference() {
            return goalsScored - goalsAgainst;
        }

        @Override
        public String toString() {
            return String.format("%s %dp, %dg (%d-%d-%d), %dgd (%d-%d)", name, getPoints(), getGamesPlayed(), wins,
                    ties, losses, getGoalDifference(), goalsScored, goalsAgainst);
        }

        @Override
        public int compareTo(Team that) {
            // 1. most points earned
            if (this.getPoints() > that.getPoints())
                return -1;
            if (this.getPoints() < that.getPoints())
                return +1;
            // 2. most wins
            if (this.wins > that.wins)
                return -1;
            if (this.wins < that.wins)
                return +1;
            // 3. most goal difference
            if (this.getGoalDifference() > that.getGoalDifference())
                return -1;
            if (this.getGoalDifference() < that.getGoalDifference())
                return +1;
            // 4. most goals scored
            if (this.goalsScored > that.goalsScored)
                return -1;
            if (this.goalsScored < that.goalsScored)
                return +1;

            // 5. less games played
            if (this.getGamesPlayed() < that.getGamesPlayed())
                return -1;
            if (this.getGamesPlayed() > that.getGamesPlayed())
                return +1;
            // 6. Lexicographic order

            return this.name.compareToIgnoreCase(that.name);
        }
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.ISO_8859_1))) {
            for (int N = Integer.parseInt(in.readLine().trim()); N-- > 0;) {
                String tournament = in.readLine();
                HashMap<String, Team> map = new HashMap<>();
                int T = Integer.parseInt(in.readLine().trim());
                for (int t = 0; t < T; t++) {
                    String teamName = in.readLine();
                    map.put(teamName, new Team(teamName));
                }
                int G = Integer.parseInt(in.readLine().trim());
                for (int g = 0; g < G; g++) {
                    String line = in.readLine();
                    StringTokenizer stk = new StringTokenizer(line, "@#");
                    String teamName1 = stk.nextToken();
                    int goals1 = Integer.parseInt(stk.nextToken());
                    int goals2 = Integer.parseInt(stk.nextToken());
                    String teamName2 = stk.nextToken();
                    Team team1 = map.get(teamName1);
                    Team team2 = map.get(teamName2);
                    if (goals1 == goals2) {
                        team1.ties++;
                        team2.ties++;
                    } else if (goals1 < goals2) {
                        team1.losses++;
                        team2.wins++;
                    } else {
                        team1.wins++;
                        team2.losses++;
                    }
                    team1.goalsScored += goals1;
                    team2.goalsScored += goals2;
                    team1.goalsAgainst += goals2;
                    team2.goalsAgainst += goals1;
                }
                Team[] teams = map.values().toArray(new Team[0]);
                Arrays.sort(teams);
                out.println(tournament);
                for (int i = 0; i < teams.length; i++)
                    out.printf("%d) %s%n", i + 1, teams[i]);
                if (N > 0)
                    out.println();
            }
        }
    }
}
