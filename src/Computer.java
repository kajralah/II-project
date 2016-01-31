public class Computer extends Thread implements Runnable {

	private boolean running;
	private Board board;
	private AlfaBeta alfabeta;

	public Computer(Board b, AlfaBeta alfabeta) {
		this.board = b;
		this.alfabeta = alfabeta;
		this.running = true;
	}

	public void run() {
		while (this.running) {
			while (board.getPlayLeft() > 0) {
				if (!board.getIsPlayer()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(board.getTurn() + " Turn - CPU");
					System.out.println(board.getPlayLeft() + " moves to end");
					Node newState = alfabeta.minmaxDecision(board
							.getCurrentState());
					if (newState != null) {
						newState.setParent(null);
						board.setCurrentState(newState);
					}
					board.setIsPlayer(true);
					board.setPlayLeft(board.getPlayLeft() - 1);
					board.setTurn(board.getTurn() + 1);
					board.refreshPaint();
					System.out.println(board.getTurn() + " Turn - Player");
					System.out.println(board.getPlayLeft()+ " moves to end");
				}
				if (board.getPlayLeft() != 0) {
					board.generalTestWinner();
				}

				if (board.getTerminate()) {
					this.running = false;
					break;
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (board.getPlayLeft() == 0) {
				board.generalTestWinner();
			}
			break;
		}
	}

}
