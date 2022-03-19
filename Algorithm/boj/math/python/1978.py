# ref -> https://www.acmicpc.net/submit/1978/34624341
import math
def is_prime(num: int):
    if num == 1:
        return False
    
    for a in range(2, int(math.sqrt(num)) + 1):
        if num % a == 0:
            return False
    return True


n = int(input())
arr = list(map(int, input().split()))
cnt = 0
for i in arr:
    if is_prime(i):
        cnt += 1
print(cnt)
