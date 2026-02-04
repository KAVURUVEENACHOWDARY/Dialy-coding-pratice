class Solution:
    def maxSumTrionic(self, nums: List[int]) -> int:
        n = len(nums)

        # Prefix sum for fast range sum
        prefix = [0] * (n + 1)
        for i in range(n):
            prefix[i + 1] = prefix[i] + nums[i]

        # Minimum possible valid answer (safe for LeetCode long)
        ans = -10**18

        # q is the bottom of the valley
        for q in range(1, n - 1):
            # must strictly decrease then increase
            if not (nums[q - 1] > nums[q] < nums[q + 1]):
                continue

            # expand left: strictly increasing
            l = q - 1
            while l - 1 >= 0 and nums[l - 1] < nums[l]:
                l -= 1

            # expand right: strictly increasing
            r = q + 1
            while r + 1 < n and nums[r] < nums[r + 1]:
                r += 1

            # sum of contiguous subarray [l..r]
            total = prefix[r + 1] - prefix[l]
            ans = max(ans, total)

        return ans
