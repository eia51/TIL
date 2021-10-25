# ref -> https://www.acmicpc.net/problem/11866
n, k = map(int, input().split())
arr = list(range(1, n + 1))
res = list()
p = 0

while len(arr) > 0:
    p += 1
    item = arr.pop(0)
    if k != p:
        arr.append(item)
    else:
        res.append(item)
        p = 0

print('<', end='')
print(', '.join(map(str, res)), end='')
print('>')
