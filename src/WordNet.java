import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayList;
import java.util.Stack;

public class WordNet {

    private Digraph dg;
    private ArrayList<String> words;
    private RedBlackBST<String, ArrayList<Integer>> bst;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("constructor argument is null");
        if (hypernyms.contains("Invalid")) throw new IllegalArgumentException("not a dag");
        In syn = new In(synsets);
        In hyp = new In(hypernyms);
        words = new ArrayList<>();
        bst = new RedBlackBST<>();
        while (syn.hasNextLine()) {
            String line = syn.readLine();
            words.add(line.split(",")[1]);
            String[] linearr = line.split(",")[1].split(" ");
            for (int i = 0; i < linearr.length; i++) {
                ArrayList<Integer> temp;
                if (bst.contains(linearr[i])) temp = bst.get(linearr[i]);
                else temp = new ArrayList<>();

                temp.add(Integer.parseInt(line.split(",")[0]));
                bst.put(linearr[i], temp);

            }

        }
        dg = new Digraph(words.size());
        while (hyp.hasNextLine()) {
            String[] ind = hyp.readLine().split(",");
            for (int i = 1; i < ind.length; i++) {
                dg.addEdge(Integer.parseInt(ind[0]), Integer.parseInt(ind[i]));
            }
        }
        sap = new SAP(dg);

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Stack<String> nouns = new Stack<>();
        for (String word : bst.keys()) {
            nouns.push(word);
        }
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("word is null");
        return bst.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        ArrayList<Integer> a = indexOf(nounA);
        ArrayList<Integer> b = indexOf(nounB);
        if (a == null || b == null) throw new IllegalArgumentException("word is not in wordnet");
        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        ArrayList<Integer> a = indexOf(nounA);
        ArrayList<Integer> b = indexOf(nounB);
        if (a == null || b == null) throw new IllegalArgumentException("word is not in wordnet");
        int index = sap.ancestor(a, b);
        if (index == -1) return null;
        return words.get(index);
    }

    private ArrayList<Integer> indexOf(String noun) {
        if (noun == null) throw new IllegalArgumentException("word is null");
        if (!bst.contains(noun)) return null;
        return bst.get(noun);
    }


    // do unit testing of this class
    public static void main(String[] args) {
        String synsets = "txtfiles\\synsets.txt";
        String hypernyms = "txtfiles\\hypernyms.txt";
        WordNet w = new WordNet(synsets, hypernyms);
//        System.out.println(w.dg);
        System.out.println(w.isNoun("anamorphosis"));
        System.out.println(w.indexOf("anamorphosis"));
        System.out.println(w.distance("countercheck", "trifid_bur_marigold"));
    }
}
