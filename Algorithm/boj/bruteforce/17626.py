# ref -> https://www.acmicpc.net/problem/17626
def sol(n):
    if int(n ** 0.5) == n ** 0.5:
        return 1
    for i in range(1, int(n ** 0.5) + 1):
        if int((n - i ** 2) ** 0.5) == (n - i ** 2) ** 0.5:
            return 2
    for i in range(1, int(n ** 0.5) + 1):
        for j in range(1, int((n - i ** 2) ** 0.5) + 1):
            if int((n - i ** 2 - j ** 2) ** 0.5) == (n - i ** 2 - j ** 2) ** 0.5:
                return 3
    return 4


print(sol(int(input())))
