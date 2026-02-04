## LeetCode 3651 - Minimum Cost Path With Teleportations

### ❌ Initial Approach (Time Limit Exceeded)

**Idea:**
- Used a brute-force / naive graph traversal
- Explored all possible paths including repeated teleport usage

**Why it TLEd:**
- Too many states were revisited
- Time complexity was exponential / too large for constraints
- Did not use an efficient shortest-path algorithm

⏱ **Result:** Time Limit Exceeded

---

### ✅ Optimized Approach (Accepted)

**Idea:**
- Modeled the problem as a graph
- Used Dijkstra / BFS with priority queue (based on weights)
- Maintained minimum cost to reach each node
- Avoided revisiting expensive paths

**Why it works:**
- Each node processed optimally
- Efficient pruning of higher-cost paths

⏱ **Time Complexity:** `O(E log V)`  
📦 **Space Complexity:** `O(V + E)`

---

### 📝 Notes
- Initial brute-force solution was useful for understanding the problem
- Optimization focused on reducing repeated state exploration
