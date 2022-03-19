import sys

while True:
    try: x=int(sys.stdin.readline().strip())
    except: break
    x*=10000000
    n=int(sys.stdin.readline().strip())
    lego=[int(sys.stdin.readline().strip()) for _ in range(n)]
    lego.sort()
    start,end=0,len(lego)-1
    result=-1
    while start<end:
        temp=lego[start]+lego[end]
        if temp>x: end-=1
        elif temp<x: start+=1
        else:
            result=abs(lego[start]-lego[end])
            break
    if result==-1: print('danger')
    else: print('yes',lego[start],lego[end])
