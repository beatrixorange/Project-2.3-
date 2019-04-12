package TicTacToe;

import Framework.AbstractGameController;
import Framework.Board;
import Framework.ClickHandler;
import Framework.Tile;
import Reversi.ReversiView;
import Framework.PlayController;
import Framework.HumanPlayer;
import Framework.LocalPlayer;
import Connection.Connection;
import Framework.AbstractPlayer;
import javafx.scene.layout.VBox;
import Framework.BotPlayer;

public class TicTacToeController extends AbstractGameController implements ClickHandler{

	public TicTacToeController(Connection connection, AbstractPlayer p1,
			AbstractPlayer p2, PlayController parent, boolean startTurn)
	{
		super(connection, p1, p2, parent, startTurn);

		this.board = new Board(3,3);
		
		boolean myTurn = this.getPlayerAtMove() instanceof HumanPlayer;
		this.view = new TicTacToeView(this, this.board, myTurn);
	}

	public void onBoardClick(int x, int y)
	{
		if (!this.turn && this.player1 instanceof LocalPlayer) {
			this.makePlayerMove(turn, x, y);
		} else if(this.turn && this.player2 instanceof LocalPlayer) {
			this.makePlayerMove(turn, x, y);
		}
	}
	
	/*@Override
	public void onBoardClick(int x, int y) {
		if(checkMove(x,y)) {
		this.board.putTile(x, y, (this.turn) ? Tile.TWO : Tile.ONE);
		this.tView.reDraw(this.board);
		if(checkWin()) {
			System.out.println("Winner");
		};
		
		this.switchTurn(!this.turn);
		}
		else
			return;
	}*/
	
	public void makePlayerMove(boolean turn, int x, int y)
	{
		if (!board.isEmpty(x, y)) {
			return;
		}
		
		int move = this.board.xyToMove(x, y);
     	System.out.println(x + " " + y);
  		System.out.println(move);
  		this.connection.makeMove(move);
  		
		this.board.putTile(x, y, Tile.byTurn(this.turn));
		this.switchTurn(!this.turn);
		this.getView().reDraw(this.board);	
	}
	
/*
	@Override
	public boolean checkMove(int x, int y) {
		if (this.board.getTile(x, y) != Tile.EMPTY) {
			return false;
		}
		return true;
	}
	*/
	public boolean checkWin() {
		boolean victory = false;
		
		Tile[] tiles = new Tile[] {Tile.ONE, Tile.TWO};
		
		
		for (int tile = 0; tile < tiles.length; tile++) {
			Tile player = tiles[tile];
			for(int a = 0; a<3 ; a ++) {
				if(this.board.getTile(a,0) == player && this.board.getTile(a,1) == player && this.board.getTile(a,2) == player)
				{
						System.out.println("yey");
						victory = true;
						return victory;
				}
				if(this.board.getTile(0,a) == player && this.board.getTile(1,a) == player && this.board.getTile(2,a) == player)
				{
					victory = true;
					return victory;
				}
			}
			if(this.board.getTile(0,0) == player && this.board.getTile(1,1) == player && this.board.getTile(2,2) == player)
			{
				victory = true;
				return victory;
				
			}
			if(this.board.getTile(0,2) == player && this.board.getTile(1,1) == player && this.board.getTile(2,0) == player)
			{
				victory = true;
				return victory;
			}
		}
		return victory;
	}
	
	@Override
	public boolean checkMove(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void makeServerMove(boolean turn, int x, int y) {
		System.out.println("MAKE SERVER MOVEEE@@@@@22222@@@");
		this.board.putTile(x, y, Tile.byTurn(this.turn));
		this.switchTurn(!turn);
		this.getView().reDraw(this.board);
	}

	public TicTacToeView getView()
	{
		return (TicTacToeView)this.view;
	}

	protected void makeBotMove(boolean turn, BotPlayer bot)
	{
		int[] move = bot.move(this.board, turn);

		this.connection.makeMove(this.board.xyToMove(move[0], move[1]));

		this.board.putTile(move[0], move[1], Tile.byTurn(turn));

		this.switchTurn(!turn);

		this.getView().reDraw(this.board);
	}
}
