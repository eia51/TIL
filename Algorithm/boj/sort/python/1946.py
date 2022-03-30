import sys

t = int(input())

for i in range(0, t):
    cnt = 1
    people = []
    
    n = int(input())
    for i in range(n):
        paper, interview = map(int, sys.stdin.readline().split())
        people.append([paper, interview])

    people.sort()
    max = people[0][1]
    
    for i in range(1, n):
        if max > people[i][1]:
            cnt += 1
            max = people[i][1]

    print(cnt)
