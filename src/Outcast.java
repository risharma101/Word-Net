import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxdist = 0;
        String outcast = nouns[0];
        for (String s : nouns) {
            int sum = 0;
            for (String x : nouns) {
                sum += wordnet.distance(s, x);
            }
            if (sum > maxdist) {
                maxdist = sum;
                outcast = s;
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("txtfiles\\" + args[0], "txtfiles\\" + args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In("txtfiles\\" + args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
