import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
	
	static class Magnetic {
		int idx;
		int dir;

		public Magnetic(int idx, int dir) {
			this.idx = idx;
			this.dir = dir;
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

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
	}
}