from collections import deque
import sys
input=sys.stdin.readline

n=int(input())

queue=deque()
for _ in range(n):
    x=input().rstrip()
    if x=='pop_front':
        if queue:
            print(queue.popleft())
        else:
            print(-1)
    elif x=='pop_back':
        if queue:
            print(queue.pop())
        else:
            print(-1)
    elif x=='size':
        print(len(queue))
    elif x=='empty':
        if queue:
            print(0)
        else:
            print(1)
    elif x=='front':
        if queue:
            print(queue[0])
        else:
            print(-1)
    elif x=='back':
        if queue:
            print(queue[-1])
        else:
            print(-1)
    else:
        a,b=x.split()
        b=int(b)
        if a=='push_front':
            queue.appendleft(b)
        else:
            queue.append(b)
