# ref -> https://www.acmicpc.net/problem/1931

n = int(input())
arr = []
for i in range(n):
    x,y=map(int, input().split())
    arr.append([x,y])
        
arr = sorted(arr, key=lambda a:a[0])
arr = sorted(arr, key=lambda a:a[1])
    
last = 0
count = 0
for x,y in arr:
    if x>=last:
        count += 1
        last = y    
print(count)
