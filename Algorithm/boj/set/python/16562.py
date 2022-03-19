import sys

sys.setrecursionlimit(10**9)
input = sys.stdin.readline

def update(idx):
    global left

    for p in rel[idx]:
        if not check[p]:
            check[p] = 1
            left -= 1
            update(p)

N, M, k = map(int, input().split())
people = [[0, i] for i in range(N+1)]
rel = [[] for i in range(N+1)]
check = [0 for i in range(N+1)]
left = N
init_k = k

inp = list(map(int, input().split()))
for i in range(len(inp)):
    people[i+1][0] = inp[i]

for i in range(M):
    a, b = map(int, input().split())
    rel[a].append(b)
    rel[b].append(a)

people.sort()

for i in range(1, N+1):
    price, idx = people[i]
    if not left or k < price: break

    if not check[idx]:
        check[idx] = 1
        left -= 1
        k -= price
        update(idx)

if left: print('Oh no')
else: print(init_k - k)
