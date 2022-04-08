import heapq
import sys
input = sys.stdin.readline
t = int(input())

while(t > 0):
    que = []
    n = int(input())
    temp = list(map(int, input().split()))
    for i in range(n):
        heapq.heappush(que, temp[i])
    answer = 1
    while len(que) > 1:
        no1 = heapq.heappop(que)
        no2 = heapq.heappop(que)
        answer  = answer * no1 * no2
        heapq.heappush(que, no1 * no2)
    print(answer % 1000000007)
    t -= 1
