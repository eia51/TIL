N = int(input())
star_list = [[" " for _ in range(N)] for _ in range(N)]

def star(size,x,y):
  if size == 1:
    star_list[y][x] = '*'
  else:
    size2 = size // 3

    star(size2, x+0*size2, y+0*size2)
    star(size2, x+0*size2, y+1*size2)
    star(size2, x+0*size2, y+2*size2)

    star(size2, x+1*size2, y+0*size2)
    star(size2, x+1*size2, y+2*size2)

    star(size2, x+2*size2, y+0*size2)
    star(size2, x+2*size2, y+1*size2)
    star(size2, x+2*size2, y+2*size2)

    
star(N,0,0)

for k in star_list:
  print(''.join(k))
