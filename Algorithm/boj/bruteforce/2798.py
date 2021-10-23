# ref -> https://www.acmicpc.net/problem/2798
n, base = map(int, input().split())
a = list(map(int, input().split()))
b = len(a)

result = 0
for i in range(0, b-2):
	for j in range(i+1, b-1):
		for k in range(j+1, b):
			tmp = a[i] + a[j] + a[k]
			if tmp > base:
				continue
			else:
				result = max(tmp, result)

print(result)
