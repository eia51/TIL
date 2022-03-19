# ref -> https://www.acmicpc.net/problem/1541

def append(r, m, t):
    num = int(t)
    if m: r.append(num*-1)
    else: r.append(num)
        
input = input()
m = False
t = ''
r = []

for ch in input:
    if ch.isdigit():
        t+=ch
    else:
        append(r, m, t)
        
        if ch=='-':
            m=True
        t=''
if t!='':
    append(r,m, t)
print(sum(r))
