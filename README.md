# SWExpertAcademy_MockTest_Java_4013

## SW Expert Academy 4013. [모의 SW 역량테스트] 활주로 건설

### 1. 문제설명

출처: https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWIeV9sKkcoDFAVH

input으로 첫줄에 자석을 회전시킬 횟수 `K`가 들어온다. 이후 네개의 자석에 대해 날의 자성값이 `S`극이 `1`, `N`극이 `0`으로 들어온다. 톱니의 화살표는 input으로오는 자성값의 첫번째 index에 위치한다. 자석을 회전시킬 때 회전하는 자석과 인접한 자석이 인접한 부분이 같은 극이 아니라면 회전하는 자석과 반대 방향으로 `1`칸 회전한다. `K`번 회전시킨 후 빨간화살표가 가리키는 값으로 점수를 매길때 점수를 출력하는 문제.

[제약사항]

    1. 시간제한 : 최대 50 개 테스트 케이스를 모두 통과하는 데 C / C++ / Java 모두 3 초
    2. 자석의 개수는 4 개이며, 각 자석은 8 개의 날을 가지고 있다.
    3. 자석을 회전시키는 횟수 K 는 1 이상 20 이하의 정수이다. ( 1 ≤ K ≤ 20 )
    4. 하나의 자석이 1 칸 회전될 때, 붙어 있는 자석은 서로 붙어 있는 날의 자성이 다를 경우에만 반대 방향으로 1 칸 회전된다.
    5. 자석을 회전시키는 방향은 시계방향이 1 로, 반시계 방향이 -1 로 주어진다.
    6. 날의 자성은 N 극이 0 으로, S 극이 1 로 주어진다.
    7. 각 자석의 날 자성정보는 빨간색 화살표 위치의 날부터 시계방향 순서대로 주어진다.
    예를 들어, [Fig. 1] 의 1 번 자석의 자성정보는 0 0 1 0 0 1 0 0 과 같이 주어진다.

[입력]
> 입력의 맨 첫 줄에는 총 테스트 케이스의 개수 `T` 가 주어지고,
> 그 다음 줄부터 `T` 개의 테스트 케이스가 주어진다.
> 각 테스트 케이스의 첫 번째 줄에는 자석을 회전시키는 횟수 `K` 가 주어진다.
> 다음 `4` 개의 줄에는 `1` 번 자석부터 `4` 번 자석까지 각각 `8` 개 날의 자성정보가 차례대로 주어진다.
> 그 다음 `K` 개의 줄에는 자석을 `1` 칸씩 회전시키는 회전 정보가 주어진다.
> 자석의 회전 정보는 회전시키려는 자석의 번호, 회전방향으로 구성되어 있다.
> 회전방향은 `1` 일 경우 시계방향이며, `-1` 일 경우 반시계방향이다.
 
[출력]
> 테스트 케이스 개수만큼 `T` 개의 줄에 각각의 테스트 케이스에 대한 답을 출력한다.
> 각 줄은 `#t` 로 시작하고 공백을 하나 둔 다음 정답을 출력한다. ( `t` 는 `1` 부터 시작하는 테스트 케이스의 번호이다. )
> 정답은 모든 자석의 회전이 끝난 후 획득한 점수의 총 합이다.

### 2. 풀이

자석을 회전시킬때 인접한 자석을 회전시키며 이미 회전한 자석은 연쇄적으로 회전되지 않아야 한다. `boolean[] visit`을 두고 BFS를 이용하여 큐에 회전시킬 자석을 넣어 처리하였다. 단 회전을 할것인가의 여부는 회전이 시작하기전 극성을 비교하여서 정해야하기 때문에 BFS를 돌기전에 회전여부에 대하여 조사한 이후 진행하였다. BFS가 수행되면 점수를 계산하여 출력하여 해결하였다. 

```java

for (int test_case = 1; test_case <= T; test_case++) {
  StringTokenizer st;
  int K = Integer.parseInt(br.readLine());

  int[][] magSign = new int[4][8];
  for (int i = 0; i < magSign.length; i++) {
    st = new StringTokenizer(br.readLine());
    for (int j = 0; j < magSign[0].length; j++) {
      magSign[i][j] = Integer.parseInt(st.nextToken());
    }
  }

  int[] magPos = { 0, 0, 0, 0 };
  boolean[] magTurnFlag = { false, false, false };

  for (int i = 0; i < K; i++) {
    st = new StringTokenizer(br.readLine());
    Magnetic magnetic = new Magnetic(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));

    for (int j = 0; j < 3; j++) {
      if (magSign[j][(magPos[j] + 10) % 8] == magSign[j+1][(magPos[j+1] + 6) % 8]) {
        magTurnFlag[j] = false;
      } else {
        magTurnFlag[j] = true;
      }
    }

    Queue<Magnetic> q = new LinkedList<>();
    boolean[] visited = new boolean[4];
    visited[magnetic.idx] = true;
    q.add(magnetic);
    while (!q.isEmpty()) {
      Magnetic m = q.poll();
      int idx = m.idx;
      int dir = m.dir;

      if (idx != 0) {
        if (!visited[idx - 1] && magTurnFlag[idx - 1]) {
          visited[idx - 1] = true;
          q.add(new Magnetic(idx - 1, dir*(-1)));
        }
      }

      if (idx != 3) {
        if (!visited[idx + 1] && magTurnFlag[idx]) {
          visited[idx + 1] = true;
          q.add(new Magnetic(idx + 1, dir*(-1)));
        }
      }

      magPos[idx] = (magPos[idx] - dir + 8) % 8;
    }

  }
  int score = 0;
  for (int i = 0; i < magPos.length; i++) {
    if (magSign[i][magPos[i]] == 1) {
      score += Math.pow(2, i);
    }
  }

  System.out.println("#"+test_case+" "+score);
}

```
