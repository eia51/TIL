from collections import deque

n, m = map(int, input().split())
graph = [list(map(int, input().split())) for _ in range(n)]

visited = [[False] * m for _ in range(n)]
dx = [0, 0, -1, 1]
dy = [-1, 1, 0, 0]

def Bfs(cur_x, cur_y, graph, visited, count):
    queue = deque()
    queue.append((cur_x, cur_y))
    visited[cur_x][cur_y] = True

    while queue:
        pop_x, pop_y = queue.popleft()
        count += 1
        for i in range(4):
            next_x = pop_x + dx[i]
            next_y = pop_y + dy[i]

            if next_x <= -1 or next_x >= n or next_y <= -1 or next_y >= m:
                continue
            if not visited[next_x][next_y] and graph[next_x][next_y]:
                visited[next_x][next_y] = True
                queue.append((next_x, next_y))
    return count

answer_list = []
for i in range(n):
    for j in range(m):
        if graph[i][j] == 1 and not visited[i][j]:
            answer_list.append(Bfs(i, j, graph, visited, 0))

print(len(answer_list))
if not answer_list:
    print(0)
else:
    print(max(answer_list))
