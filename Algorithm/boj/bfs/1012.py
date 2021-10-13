# ref -> https://www.acmicpc.net/problem/1012

import sys
sys.setrecursionlimit(10**6)
g = [] 

def dfs(x,y):
    if x<0 or y<0 or x>=n or y>=m:
        return False
    
    if g[x][y]==1:
        g[x][y]=0
        dfs(x+1, y)
        dfs(x-1, y)
        dfs(x, y+1)
        dfs(x, y-1)
        return True
    else:
        return False
     
t = int(input())
for tt in range(t):
    n,m,k=map(int, input().split())
    
    g = [[0]*m for nn in range(n)]
    
    for kk in range(k):
        x,y = map(int, input().split())
        g[x][y] = 1
        
        
    result = 0
    for i in range(n):
        for j in range(m):
            if g[i][j] == 1 and dfs(i, j):
                result += 1
                
    print(result)            
                
