import sys
import itertools

expression = list(map(str, sys.stdin.readline().strip()))
stack, pos, answer = [], [], set()

for i, value in enumerate(expression):
    if value == '(':
        stack.append(i)
    if value == ')':
        pos.append((stack.pop(), i))

for i in range(1, len(pos)+1):
    comb = itertools.combinations(pos, i)
    for j in comb:
        temp = expression[:]
        for s, e in j:
            temp[s] = ''
            temp[e] = ''
        answer.add(''.join(map(str, temp)))
for i in sorted(list(answer)):
    print(i)
