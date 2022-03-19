# ref -> https://www.acmicpc.net/problem/2231
n = int(input())

f = False
for i in range(n):
    str_i = str(i)
    sum_si = 0
    for si in str_i:
        sum_si += int(si)
        
    if i+sum_si == n:
        print(i)
        f = True
        break

if not f:
    print(0)
