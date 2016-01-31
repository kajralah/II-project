import java.util.ArrayList;

public class AlfaBeta {

	private Board board;

	public AlfaBeta(Board board) {
		this.board = board;
	}

	public void printResults(long start) {
		System.out.println("Number of expanded nodes: "
				+ board.getNumberOfExpandedNodes());
		System.out.println("Number of visited nodes: "
				+ (board.getNumberOfVisitedNodes() - 1));

		final long totalTime = System.currentTimeMillis() - start;
		System.out.println("Computing time");
		System.out.println("In milliseconds: " + totalTime + " ms.");
		System.out.println("Rounded to seconds: " + (totalTime / 1000) + "s.");
		System.out.println("");
	}

	//decision function
	public Node minmaxDecision(Node currentState) {

		Node result;
		board.setNumberOfExpandedNodes(0);
		board.setNumberOfVisitedNodes(0);
		final long start = System.currentTimeMillis();

			int alpha = Integer.MIN_VALUE;
			int beta = Integer.MAX_VALUE;
			maxValue(currentState, alpha, beta);
			printResults(start);
			result = getBest(currentState);
		return result;
	}

	public int maxValue(Node currentState, int alpha, int beta) {

		board.setNumberOfVisitedNodes(board.getNumberOfVisitedNodes() + 1);

		if (currentState.checkWinner(board.getPlayLeft()) != -2) {
			return currentState.checkWinner(board.getPlayLeft());
		}

		int v = alpha;
		currentState.expandNode(false);

		board.setNumberOfExpandedNodes(board.getNumberOfExpandedNodes()
				+ currentState.getChildrenList().size());

		for (Node s : currentState.getChildrenList()) {
			v = Math.max(alpha, minValue(s, alpha, beta));
			if (v >= beta) {
				v = beta;
			}
			if (s.getParent().getParent() == null) {
				s.setValue(v);
				board.getTempList().add(s);
			}
			if (v >= beta) {
				return beta;
			}
		}
		return v;
	}

	public int minValue(Node currentState, int alpha, int beta) {

		board.setNumberOfVisitedNodes(board.getNumberOfVisitedNodes() + 1);

		if (currentState.checkWinner(board.getPlayLeft()) != -2) {
			return currentState.checkWinner(board.getPlayLeft());
		}
		
		int v = beta;
		currentState.expandNode(true);

		board.setNumberOfExpandedNodes(board.getNumberOfExpandedNodes()
				+ currentState.getChildrenList().size());

		for (Node s : currentState.getChildrenList()) {
			v = Math.min(beta, maxValue(s, alpha, beta));
			if (v <= alpha) {
				v = alpha;
			}
			if (s.getParent().getParent() == null) {
				s.setValue(v);
				board.getTempList().add(s);
			}
			if (v <= alpha) {
				return alpha;
			}
		}
		return v;
	}


	//get best option
	public Node getBest(Node currentstate) {
		Node computertest = new Node();
		if (board.getTempList().size() == 0)
			return null;

		computertest = board.getTempList().get(0);
		
		for (Node n : board.getTempList()) {
			if (n.checkWinner(board.getPlayLeft()) == 1) {
				return n;
			}

			if (n.getValue() > computertest.getValue()) {
				computertest = n;
			}

			if (board.getPlayLeft() == 8) {
				if (n.getGameMatrix()[0][0] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[0][2] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[2][0] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[2][2] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[0][1] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[1][0] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[1][2] == 0
						&& n.getGameMatrix()[1][1] == 1
						|| n.getGameMatrix()[2][1] == 0
						&& n.getGameMatrix()[1][1] == 1) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 9) {
				if (n.getGameMatrix()[1][1] == 1) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 7) {
				if (n.getGameMatrix()[0][0] == 0
						&& n.getGameMatrix()[2][2] == 1
						|| n.getGameMatrix()[0][2] == 0
						&& n.getGameMatrix()[2][0] == 1
						|| n.getGameMatrix()[2][0] == 0
						&& n.getGameMatrix()[0][2] == 1
						|| n.getGameMatrix()[2][2] == 0
						&& n.getGameMatrix()[0][0] == 1) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 0) {
				if (n.getGameMatrix()[0][0] == 1
						&& n.getGameMatrix()[2][2] == 0
						&& (n.getGameMatrix()[0][2] == 1 || n.getGameMatrix()[2][0] == 1)
						|| n.getGameMatrix()[2][0] == 1
						&& n.getGameMatrix()[0][2] == 0
						&& (n.getGameMatrix()[0][0] == 1 || n.getGameMatrix()[2][2] == 1)
						|| n.getGameMatrix()[0][2] == 1
						&& n.getGameMatrix()[2][0] == 0
						&& (n.getGameMatrix()[0][0] == 1 || n.getGameMatrix()[2][2] == 1)
						|| n.getGameMatrix()[2][2] == 1
						&& n.getGameMatrix()[0][0] == 0
						&& (n.getGameMatrix()[2][0] == 1 || n.getGameMatrix()[0][2] == 1)) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 1) {
				if (n.getGameMatrix()[0][0] == 0
						&& (n.getGameMatrix()[1][2] == 0 || n.getGameMatrix()[2][1] == 0)
						&& n.getGameMatrix()[2][2] == 1
						|| n.getGameMatrix()[0][2] == 0
						&& (n.getGameMatrix()[2][1] == 0 || n.getGameMatrix()[1][0] == 0)
						&& n.getGameMatrix()[2][0] == 1
						|| n.getGameMatrix()[2][0] == 0
						&& (n.getGameMatrix()[0][1] == 0 || n.getGameMatrix()[1][2] == 0)
						&& n.getGameMatrix()[0][2] == 1
						|| n.getGameMatrix()[2][2] == 0
						&& (n.getGameMatrix()[0][1] == 0 || n.getGameMatrix()[1][0] == 0)
						&& n.getGameMatrix()[0][0] == 1) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 1) {
				if (n.getGameMatrix()[0][2] == 0
						&& n.getGameMatrix()[2][0] == 0
						&& (n.getGameMatrix()[0][1] == 1
								|| n.getGameMatrix()[1][0] == 1
								|| n.getGameMatrix()[1][2] == 1 || n
								.getGameMatrix()[2][1] == 1)) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 1) {
				if (n.getGameMatrix()[2][1] == 0
						&& n.getGameMatrix()[1][2] == 0
						&& (n.getGameMatrix()[2][0] == 1 || n.getGameMatrix()[1][2] == 1)) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}

			if (board.getPlayLeft() == 5) {
				if (n.getGameMatrix()[0][1] == 0
						&& n.getGameMatrix()[1][1] == 1) {
					if (n.getGameMatrix()[0][0] == 1
							&& n.getGameMatrix()[1][0] == 1) {
						computertest = n;
						board.getTempList().clear();
						return computertest;
					}
				}
			}

			if (board.getPlayLeft() == 5 && n.getGameMatrix()[1][1] == 1) {
				if (n.getGameMatrix()[0][0] == 1
						&& n.getGameMatrix()[0][1] == 1
						&& n.getGameMatrix()[1][0] == 0
						|| n.getGameMatrix()[0][0] == 1
						&& n.getGameMatrix()[0][1] == 0
						&& n.getGameMatrix()[1][0] == 1
						|| n.getGameMatrix()[0][2] == 1
						&& n.getGameMatrix()[0][1] == 1
						&& n.getGameMatrix()[1][2] == 0
						|| n.getGameMatrix()[0][2] == 1
						&& n.getGameMatrix()[0][1] == 0
						&& n.getGameMatrix()[1][2] == 1
						|| n.getGameMatrix()[2][0] == 1
						&& n.getGameMatrix()[1][0] == 1
						&& n.getGameMatrix()[2][1] == 0
						|| n.getGameMatrix()[2][0] == 1
						&& n.getGameMatrix()[1][0] == 0
						&& n.getGameMatrix()[2][1] == 1
						|| n.getGameMatrix()[2][2] == 1
						&& n.getGameMatrix()[2][1] == 1
						&& n.getGameMatrix()[1][2] == 0
						|| n.getGameMatrix()[2][2] == 1
						&& n.getGameMatrix()[2][1] == 0
						&& n.getGameMatrix()[1][2] == 1) {
					computertest = n;
					board.getTempList().clear();
					return computertest;
				}
			}
		}

		computertest = board.getTempList().get(0);

		int[][] playertest = getPlayerBest(computertest);
		currentstate.expandNode(board.getIsPlayer());
		for (Node n : currentstate.getChildrenList()) {

			if (n.getValue() > computertest.getValue()) {
				computertest = n;
			}

			if (n.checkWinner(board.getPlayLeft()) == 1) {
				computertest = n;
				break;
			}

			if (isTheOne(n.getGameMatrix(), playertest)) {
				computertest = n;
				break;
			}

		}
		board.getTempList().clear();
		return computertest;
	}

	
	//best option to block opponent
	public boolean isTheOne(int[][] computer, int[][] player) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (computer[i][j] == 1 && player[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	
	//Get the best node
	public int[][] getPlayerBest(Node current) {
		Node test = new Node();

		current.expandNode(!board.getIsPlayer());
		ArrayList<Node> toCheckList = current.getChildrenList();
		for (Node n : toCheckList) {
			if (n.checkWinner(board.getPlayLeft()) == -1) {
				test = n;
			}
		}
		
		return test.getGameMatrix();
	}
}
