# ref -> https://www.acmicpc.net/problem/11047
n, k = map(int, input().split())
v = []
for i in range(n):
    v.append(int(input()))

res = 0
for i in range(n - 1, -1, -1):
    if k >= v[i]:
        res += int(k / v[i])
    k %= v[i]
    if k == 0:
        break
print(res)
