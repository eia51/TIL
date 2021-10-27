# ref -> https://www.acmicpc.net/submit/10989/34797074
import sys

n = int(sys.stdin.readline())
max_val = 10000
arr = [0] * max_val

for _ in range(n):
    my_in = int(sys.stdin.readline())
    arr[my_in - 1] = arr[my_in - 1] + 1

for i in range(max_val):
    if arr[i] != 0:
        for j in range(arr[i]):
            print(i + 1)
