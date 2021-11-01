# ref -> https://www.acmicpc.net/problem/19539
import sys
read = sys.stdin.readline

N = int(read())
apple = list(map(int, read().split()))
apple_sum = sum(apple)
turn = apple_sum // 3

if apple_sum % 3 != 0:
    print('NO')
else:
    for a in apple:
        turn -= (a//2)
    if turn > 0:
        print('NO')
    else:
        print('YES')
