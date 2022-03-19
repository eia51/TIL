# ref -> https://www.acmicpc.net/problem/13305
n = int(input())
load = list(map(int, input().split()))
node = list(map(int, input().split()))

m = node[0]
res = m * load[0]

for i in range(1, n-1):
    if node[i] < m:
        m = node[i]
    res += (m*load[i])
print(res)
