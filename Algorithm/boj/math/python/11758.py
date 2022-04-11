dot = []
for i in range(3):
    dot.append(list(map(int, input().split())))

def ccw(p1, p2, p3):
    x1, y1 = p1
    x2, y2 = p2
    x3, y3 = p3
    return (x1 * y2 + x2 * y3 + x3 * y1) - (x2 * y1 + x3 * y2 + x1 * y3)

result = ccw(dot[0], dot[1], dot[2])
if result > 0:
    print(1)
elif result < 0:
    print(-1)
else:
    print(0)
