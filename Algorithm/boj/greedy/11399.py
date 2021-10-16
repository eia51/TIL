# ref -> https://www.acmicpc.net/problem/11399

n=input()
arr=list(map(int,input().split()))

arr.sort()
tmp=0
sum=0
for i in arr:
    tmp += int(i)
    sum += tmp
print(sum)
