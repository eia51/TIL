import heapq
import sys

N = int(input())
card_deck = []
for _ in range(N):
    heapq.heappush(card_deck, int(sys.stdin.readline()))


if len(card_deck) == 1:
    print(0)
else:
    answer = 0
    while len(card_deck) > 1: 
        temp_1 = heapq.heappop(card_deck)
        temp_2 = heapq.heappop(card_deck)
        answer += temp_1 + temp_2
        heapq.heappush(card_deck, temp_1 + temp_2)
    
    print(answer)
