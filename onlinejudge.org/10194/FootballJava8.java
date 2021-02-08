import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This is my first Java 8 version of the 10194 Football (aka Soccer) problem from onlinejudge.org before UVa onlinejudge.
 * This version uses the comparing static function and the thenComparing default function from Comparator interface to sort
 * the Teams list.
 * onlinejudge.org verdict accepted
 * @author Diego Satoba
 */
class Main {
    static class Team {
        String name;
        int wins, ties, losses, goalsScored, goalsAgainst;
        Team(String name) {
            this.name = name;
        }
        // The following getter methods are needed to sort the Team list using method references and Comparator methods
        public String getName() {
            return this.name;
        }

        public int getGoalDifference() {
            return this.goalsScored - this.goalsAgainst;
        }

        public int getPoints() {
            return this.wins*3 + this.ties;
        }

        public int getWins() {
            return this.wins;
        }

        public int getGoalScored() {
            return this.goalsScored;
        }

        public int getGamesPlayed() {
            return this.wins + this.ties + this.losses;
        }

        public String toString() {
            return String.format("%s %dp, %dg (%d-%d-%d), %dgd (%d-%d)", name, 
                this.getPoints(), 
                this.getGamesPlayed(),
                this.wins,
                this.ties,
                this.losses,
                this.getGoalDifference(),
                this.goalsScored, 
                this.goalsAgainst);
        }
    }

    public static void main(String [] args) throws Exception {
        // Setting the charset to InputStreamReader and OutputStreamWriter is weird but needed to get AC
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.ISO_8859_1));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.ISO_8859_1))) {
            for (int T=Integer.parseInt(in.readLine().trim()); T-->0; ) {
                String league = in.readLine();
                Map<String, Team> teamByName = new HashMap<>();
                for (int N=Integer.parseInt(in.readLine().trim()); N-->0; ) {
                    String name = in.readLine().trim();
                    teamByName.putIfAbsent(name, new Team(name));
                }
                for (int N=Integer.parseInt(in.readLine().trim()); N-->0; ) {
                    String[] pieces = in.readLine().trim().split("[#@]");
                    Team teamA = teamByName.get(pieces[0]);
                    int goalsA = Integer.parseInt(pieces[1]);
                    int goalsB = Integer.parseInt(pieces[2]);
                    Team teamB = teamByName.get(pieces[3]);
                    teamA.goalsScored += goalsA;
                    teamA.goalsAgainst += goalsB;
                    teamB.goalsScored += goalsB;
                    teamB.goalsAgainst += goalsA;
                    if (goalsA > goalsB) {
                        teamA.wins++;
                        teamB.losses++;
                    } else if (goalsA < goalsB) {
                        teamA.losses++;
                        teamB.wins++;
                    } else {
                        teamA.ties++;
                        teamB.ties++;
                    }
                }
                // The magic is here
                ArrayList<Team> teams = new ArrayList<>(teamByName.values());
                teams.sort(Comparator.comparing(Team::getPoints).
                    thenComparing(Team::getWins).
                    thenComparing(Team::getGoalDifference).
                    thenComparing(Team::getGoalScored).
                    thenComparing(Comparator.comparing(Team::getGamesPlayed).reversed()).
                    thenComparing((s, t) -> -s.name.compareToIgnoreCase(t.name)).
                    reversed());
                
                out.println(league);
                for (int i=0; i<teams.size(); i++) 
                    out.printf("%d) %s%n", i+1, teams.get(i));
                if (T > 0) out.println();
            }
        }
    }
}
