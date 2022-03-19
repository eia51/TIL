# ref -> https://www.acmicpc.net/problem/10816
n = int(input())
n_arr = map(int, input().split())
m = int(input())
m_arr = map(int, input().split())

n_dic = dict()
for i in n_arr:
    if i in n_dic:
        n_dic[i] += 1
    else:
        n_dic[i] = 1

res = list()
for i in m_arr:
    if i in n_dic:
        res.append(n_dic[i])
    else:
        res.append(0)

print(' '.join(map(str, res)))
