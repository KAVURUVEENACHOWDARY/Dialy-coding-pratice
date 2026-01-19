#include <vector>
using namespace std;

class Solution {
public:
    int maxSideLength(vector<vector<int>>& mat, int threshold) {
        int m = mat.size();
        int n = mat[0].size();

        // Prefix sum array
        vector<vector<int>> pref(m + 1, vector<int>(n + 1, 0));

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                pref[i][j] = mat[i - 1][j - 1]
                           + pref[i - 1][j]
                           + pref[i][j - 1]
                           - pref[i - 1][j - 1];
            }
        }

        // Check if there exists a k x k square with sum <= threshold
        auto can = [&](int k) {
            for (int i = k; i <= m; i++) {
                for (int j = k; j <= n; j++) {
                    int sum = pref[i][j]
                            - pref[i - k][j]
                            - pref[i][j - k]
                            + pref[i - k][j - k];
                    if (sum <= threshold) return true;
                }
            }
            return false;
        };

        int left = 0, right = min(m, n), ans = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (can(mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }
};
