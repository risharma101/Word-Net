import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("constructor argument is null");
        dg = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V())
            throw new IllegalArgumentException("verticies are not in range");

        int mindist = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bdv = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bdw = new BreadthFirstDirectedPaths(dg, w);
        for (int x = 0; x < dg.V(); x++) {
            if (bdv.hasPathTo(x) && bdw.hasPathTo(x)) {
                int dist = bdv.distTo(x) + bdw.distTo(x);
                if (dist < mindist) mindist = dist;
            }
        }
        if (mindist == Integer.MAX_VALUE) return -1;
        return mindist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= dg.V() || w >= dg.V())
            throw new IllegalArgumentException("verticies are not in range");

        int mindist = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bdv = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bdw = new BreadthFirstDirectedPaths(dg, w);
        for (int x = 0; x < dg.V(); x++) {
            if (bdv.hasPathTo(x) && bdw.hasPathTo(x)) {
                int dist = bdv.distTo(x) + bdw.distTo(x);
                if (dist < mindist) {
                    mindist = dist;
                    ancestor = x;
                }
            }
        }
        return ancestor;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        if (v == null || w == null) throw new IllegalArgumentException("argument is null");

        boolean emptyv = true;
        boolean emptyw = true;

        for (Integer x : v) {
            emptyv = false;
            break;
        }
        for (Integer x : w) {
            emptyw = false;
            break;
        }
        if (emptyv || emptyw) return -1;

        int mindist = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bdv = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bdw = new BreadthFirstDirectedPaths(dg, w);
        for (int x = 0; x < dg.V(); x++) {
            if (bdv.hasPathTo(x) && bdw.hasPathTo(x)) {
                int dist = bdv.distTo(x) + bdw.distTo(x);
                if (dist < mindist) mindist = dist;
            }
        }
        if (mindist == Integer.MAX_VALUE) return -1;
        return mindist;

//        if (v == null || w == null) throw new IllegalArgumentException("argument is null");
//        int mindist = Integer.MAX_VALUE;
//        for (Integer vv : v) {
//            for (Integer ww : w) {
//                if (vv == null || ww == null) throw new IllegalArgumentException("null value");
//                if (vv < 0 || ww < 0 || vv >= dg.V() || ww >= dg.V())
//                    throw new IllegalArgumentException("verticies are not in range");
//                int dist = length(vv, ww);
//                if (dist != -1 && dist < mindist) mindist = dist;
//            }
//        }
//        if (mindist < Integer.MAX_VALUE) return mindist;
//        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
//        if (v == null || w == null) throw new IllegalArgumentException("argument is null");
//        int mindist = Integer.MAX_VALUE;
//        int sca = -1;
//        for (Integer vv : v) {
//            for (Integer ww : w) {
//                if (vv == null || ww == null) throw new IllegalArgumentException("null value");
//                if (vv < 0 || ww < 0 || vv >= dg.V() || ww >= dg.V())
//                    throw new IllegalArgumentException("verticies are not in range");
//                int dist = length(vv, ww);
//                if (dist != -1 && dist < mindist) {
//                    mindist = dist;
//                    sca = ancestor(vv, ww);
//                }
//            }
//        }
//        if (mindist < Integer.MAX_VALUE) return sca;
//        return -1;

        if (v == null || w == null) throw new IllegalArgumentException("argument is null");

        boolean emptyv = true;
        boolean emptyw = true;

        for (Integer x : v) {
            emptyv = false;
            break;
        }
        for (Integer x : w) {
            emptyw = false;
            break;
        }
        if (emptyv || emptyw) return -1;


        int mindist = Integer.MAX_VALUE;
        int ancestor = -1;
        BreadthFirstDirectedPaths bdv = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths bdw = new BreadthFirstDirectedPaths(dg, w);
        for (int x = 0; x < dg.V(); x++) {
            if (bdv.hasPathTo(x) && bdw.hasPathTo(x)) {
                int dist = bdv.distTo(x) + bdw.distTo(x);
                if (dist < mindist) {
                    mindist = dist;
                    ancestor = x;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("txtfiles\\" + args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
