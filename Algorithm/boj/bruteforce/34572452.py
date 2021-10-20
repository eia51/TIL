# ref -> https://www.acmicpc.net/source/34572452
x = input()
N = int(x.split(" ")[0])
M = int(x.split(" ")[1])
sq = []
for i in range(N):
    sq.append(input())

table = [
        "WBWBWBWB",
        "BWBWBWBW",
        "WBWBWBWB",
        "BWBWBWBW",
        "WBWBWBWB",
        "BWBWBWBW",
        "WBWBWBWB",
        "BWBWBWBW"
        ]

Count1 = 0
Count2 = 0

small = 64
for a in range(N-7):
    for b in range(M-7):
        for i in range(8):
            for k in range(8):
                if sq[i+a][k+b] != table[i][k]:
                    Count1 += 1

        if Count1 > 64-Count1:
            Count1 = 64-Count1
        if Count1 < small:
            small = Count1

        Count1 = 0

print(small)
