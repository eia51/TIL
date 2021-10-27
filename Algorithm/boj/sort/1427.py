# ref -> https://www.acmicpc.net/problem/1427

def sort(arr, start, end):
    if start >= end:
        return arr

    mid = partition(arr, start, end)
    sort(arr, start, mid - 1)
    sort(arr, mid, end)
    return arr


def mid(a, b, c):
    if a <= b <= c or c <= b <= a:
        return b
    if b <= a <= c or c <= a <= b:
        return a
    return c


def partition(arr, start, end) -> int:
    piv = mid(arr[(start + end) // 2], arr[start], arr[end])

    while start <= end:

        while arr[start] > piv:
            start += 1

        while arr[end] < piv:
            end -= 1

        if start <= end:
            arr[start], arr[end] = arr[end], arr[start]
            start += 1
            end -= 1

    return start


def quick_sort(arr):
    return sort(arr, 0, len(arr) - 1)


from sys import stdin

n = stdin.readline().strip()
arr = list(map(int, n))
arr = quick_sort(arr)

print(''.join(map(str, arr)))
