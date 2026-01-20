# LeetCode 194 - Transpose File
# Topic: Shell, AWK

awk '
{
    for (i = 1; i <= NF; i++) {
        a[i, NR] = $i
        if (max < i) max = i
    }
}
END {
    for (i = 1; i <= max; i++) {
        for (j = 1; j <= NR; j++) {
            if (a[i, j] != "") {
                printf "%s", a[i, j]
                if (j < NR) printf " "
            }
        }
        printf "\n"
    }
}' file.txt
