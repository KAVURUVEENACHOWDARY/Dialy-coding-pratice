import (
	"container/heap"
	"math"
	"sort"
)

type State struct {
	cost int
	i, j int
	t    int
}

type MinHeap []State

func (h MinHeap) Len() int           { return len(h) }
func (h MinHeap) Less(a, b int) bool { return h[a].cost < h[b].cost }
func (h MinHeap) Swap(a, b int)      { h[a], h[b] = h[b], h[a] }

func (h *MinHeap) Push(x interface{}) {
	*h = append(*h, x.(State))
}

func (h *MinHeap) Pop() interface{} {
	old := *h
	n := len(old)
	x := old[n-1]
	*h = old[:n-1]
	return x
}

func minCost(grid [][]int, k int) int {
	m, n := len(grid), len(grid[0])
	inf := math.MaxInt32

	dist := make([][][]int, k+1)
	for t := 0; t <= k; t++ {
		dist[t] = make([][]int, m)
		for i := 0; i < m; i++ {
			dist[t][i] = make([]int, n)
			for j := 0; j < n; j++ {
				dist[t][i][j] = inf
			}
		}
	}

	type Cell struct {
		val, i, j int
	}

	cells := []Cell{}
	for i := 0; i < m; i++ {
		for j := 0; j < n; j++ {
			cells = append(cells, Cell{grid[i][j], i, j})
		}
	}

	sort.Slice(cells, func(a, b int) bool {
		return cells[a].val < cells[b].val
	})

	ptr := make([]int, k+1)

	dist[0][0][0] = 0
	h := &MinHeap{}
	heap.Init(h)
	heap.Push(h, State{0, 0, 0, 0})

	dirs := [][]int{{0, 1}, {1, 0}}

	for h.Len() > 0 {
		cur := heap.Pop(h).(State)
		c, i, j, t := cur.cost, cur.i, cur.j, cur.t

		if c > dist[t][i][j] {
			continue
		}

		// Normal moves
		for _, d := range dirs {
			ni, nj := i+d[0], j+d[1]
			if ni < m && nj < n {
				nc := c + grid[ni][nj]
				if nc < dist[t][ni][nj] {
					dist[t][ni][nj] = nc
					heap.Push(h, State{nc, ni, nj, t})
				}
			}
		}

		// Teleport (optimized)
		if t < k {
			for ptr[t] < len(cells) && cells[ptr[t]].val <= grid[i][j] {
				x, y := cells[ptr[t]].i, cells[ptr[t]].j
				if c < dist[t+1][x][y] {
					dist[t+1][x][y] = c
					heap.Push(h, State{c, x, y, t + 1})
				}
				ptr[t]++
			}
		}
	}

	ans := inf
	for t := 0; t <= k; t++ {
		ans = min(ans, dist[t][m-1][n-1])
	}
	return ans
}

func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}
