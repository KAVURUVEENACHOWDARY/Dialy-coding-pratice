import java.util.*;

class Solution {

    static class Edge {
        int to;
        int cost;
        Edge(int t, int c) {
            to = t;
            cost = c;
        }
    }

    public int minCost(int n, int[][] edges) {

        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Build graph
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            graph[u].add(new Edge(v, w));        // normal edge
            graph[v].add(new Edge(u, 2 * w));    // reversed edge
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a[1], b[1])
        );

        dist[0] = 0;
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[0];
            long cost = cur[1];

            if (cost > dist[u]) continue;

            for (Edge e : graph[u]) {
                int v = e.to;
                long newCost = cost + e.cost;
                if (newCost < dist[v]) {
                    dist[v] = newCost;
                    pq.offer(new long[]{v, newCost});
                }
            }
        }

        return dist[n - 1] == Long.MAX_VALUE ? -1 : (int) dist[n - 1];
    }
}
