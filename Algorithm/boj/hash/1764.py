# ref -> https://www.acmicpc.net/problem/1764
n, m = map(int, input().split())

cant_hear = dict()
for i in range(n):
    cant_hear[input()] = True

cant_hear_see = list()
for i in range(m):
    cant_see = input()
    if cant_see in cant_hear:
        cant_hear_see.append(cant_see)

cant_hear_see.sort()
print(len(cant_hear_see))
for name in cant_hear_see:
    print(name)
