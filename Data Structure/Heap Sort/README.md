# Heap Sort 
  최대 힙 트리나 최소 힙 트리를 구성해 정렬을 하는 방법
  To configure and sort a maximum or minimum heap tree
## init heapvalues
  정렬할 숫자들을 입력받는 과정을 수행한다. 주어진 정수 값들을 -1이 나올 때까지 읽
  어서 그 내용대로 heap의 1번 index부터 차례로 집어 넣는다.
  Perform the process of receiving input of the numbers to be sorted. Read the given integer values until -1.
  Put in the 1st index of the heap in order according to the instructions.
## sort
  사용자로부터 입력 받은 숫자를 오름차순으로 정렬한다.
  Sort the numbers entered by the user in ascending order.
  
## void Adjust(int root, int n);
  인덱스 root에서부터 시작하는 subtree를 max heap 상태를 만족하도록 변경한다.
  Change the subtree starting from the index root to satisfy the maxheap state.
 
 ## void Sort
  initialization을 수행한 후 max heap 상태의 배열을 이용하여 정렬을 수행한다. 
  After initialization, the alignment is performed using an array of maxheap states.
