n = int(input())
count = 9
dis = 2
minicount = 0
result = []
result1 = []

def solve():
    global count
    global dis
    global minicount
    if n >= 1023:
        print(-1)
        return
    if 0 <= n <= 9:
        print(n)
        return
    if count == n:
        return
    if len(result) == dis and result[0] == 9:
        for i in range(dis-1):
            if result[i]-1 != result[i+1]:
                break
            else:
                minicount += 1
        if minicount == dis-1:
            count += 1
            if count == n:
                print("".join(map(str, result)))
            dis += 1
            del result[:]
            minicount = 0
        else:
            minicount = 0

    if len(result) == dis:
        count += 1
        if count == n:
            print("".join(map(str, result)))
        return
    for i in range(0,10):
        if len(result) > 0 and i >= result[-1]:
            continue
        result.append(i)
        solve()
        if len(result) > 0:
            result.pop()
        else:
            pass

solve()
