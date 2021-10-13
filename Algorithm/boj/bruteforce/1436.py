# ref -> https://www.acmicpc.net/problem/1436

i = int(input())


cnt = 0
siz_n = 666
while True:
	if '666' in str(siz_n):
		cnt+=1

	if i == cnt:
		print(siz_n)
		break

	siz_n+=1
