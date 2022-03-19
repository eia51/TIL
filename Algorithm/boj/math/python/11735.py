import sys
input = sys.stdin.readline

n, q = map(int, input().split())

rcnt = 0
ccnt = 0
rdel = 0
cdel = 0
hap = n*(n+1)//2

vr = [0 for i in range(n+1)]
vc = [0 for i in range(n+1)]

for i in range(q):
    a, b = input().split()
    b = int(b)
    if a == 'R':
        if vr[b] == 1:
            print(0)
        else:
            vr[b] = 1
            print((n-ccnt)*b + hap - cdel)
            rcnt += 1
            rdel += b
    else:
        if vc[b] == 1:
            print(0)
        else:
            vc[b] = 1
            print((n-rcnt)*b + hap - rdel)
            ccnt += 1
            cdel += b
