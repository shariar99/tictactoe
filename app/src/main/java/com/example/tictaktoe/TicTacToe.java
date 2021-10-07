package com.example.tictaktoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TicTacToe extends View {
    private final  int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private boolean winningLine = false;
    private final Paint paint = new Paint();
    private  int cellSize = getWidth()/3;
    private  final  GameLogic game ;
    public TicTacToe(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new GameLogic();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs ,
                R.styleable.TicTacToe , 0,0);
        try {
      boardColor = a.getInteger(R.styleable.TicTacToe_bordColor,0);
      XColor = a.getInteger(R.styleable.TicTacToe_XColor, 0);
      OColor = a.getInteger(R.styleable.TicTacToe_OColor , 0);
      winningLineColor =a.getInteger(R.styleable.TicTacToe_winningLineColor , 0);
        }finally {
            a.recycle();

          }
    }
 @Override
    protected  void onMeasure (  int width , int height ){
        super.onMeasure(width ,height);
        int dimension = Math.min(getMeasuredHeight(),getMeasuredHeight());
        cellSize = dimension/3;
        setMeasuredDimension(dimension , dimension);
 }
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
    protected void onDraw(Canvas canves ){

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBord(canves);
        drawMarkers(canves);
        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canves);
        }
  }
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {

      float x = event.getX();
      float y = event.getY();
      int action = event.getAction();
      if (action == MotionEvent.ACTION_DOWN) {

          int row = (int) Math.ceil(y / cellSize);
          int col = (int) Math.ceil(x / cellSize);

          if(!winningLine ) {

              if (game.updateGameBoard(row, col)) {

                  invalidate();

                  if(game.winnerCheck()){
                      winningLine = true;
                      invalidate();
                  }
                  if (game.getPlayer() % 2 == 0) {

                      game.setPlayer(game.getPlayer() - 1);
                  } else {
                      game.setPlayer(game.getPlayer() + 1);
                  }
              }
          }

          invalidate();

          return  true;
      }
      return false;


   }
  private  void drawGameBord(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

    for (int c = 1 ;c <3 ;c++ ){
         canvas.drawLine(cellSize*c ,0,cellSize*c ,canvas.getWidth() , paint);
    }
    for (int r = 1 ; r < 3 ;r++){

        canvas.drawLine(0 ,cellSize*r ,canvas.getWidth(),cellSize*r , paint);
      }
  }
   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
   private void drawMarkers(Canvas canvas){
       for ( int r= 0; r< 3 ;r++ ){

           for ( int c = 0 ; c <3 ; c++){
               if (game.getGameBord()[r][c] != 0){
                   if(game.getGameBord()[r][c] ==1){
                       drawX(canvas, r, c );
                   }else {
                       drawO(canvas , r, c);
                   }

               }
           }
       }
      }
  private void drawX(Canvas canvas , int row ,int col  ){
        paint.setColor(XColor);

        canvas.drawLine((col+1)*cellSize,
                row*cellSize,
                col*cellSize,
                (row+1)*cellSize,
                paint);

      canvas.drawLine(col*cellSize,
              row*cellSize,
              (col+1)*cellSize,
              (row+1)*cellSize,
              paint);
  }
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void drawO(Canvas canvas , int row , int col){

        paint.setColor(OColor);
        canvas.drawOval(col*cellSize,row*cellSize,
                (col*cellSize+cellSize),
                (row*cellSize + cellSize),
                paint );
  }
   private void drawHorizontalLine(Canvas canvas ,int row ,int col ){

        canvas.drawLine(col,row*cellSize+(float)cellSize/2,cellSize*3,
                row*cellSize+(float)cellSize/2,paint);

   }
   public  void drawVerticalLine (Canvas canvas,int row ,int col ){

        canvas.drawLine(col*cellSize+(float)cellSize/2,row,col*cellSize+(float) cellSize/2,cellSize*3,
                paint);
   }
   private void  drawDiogonalLinePos(Canvas canvas){

        canvas.drawLine(0,cellSize*3,cellSize*3,0,paint);
   }

    private void  drawDiogonalLineneg(Canvas canvas){

        canvas.drawLine(0,0,cellSize*3,cellSize*3,paint);

    }

    private void drawWinningLine(Canvas canvas){

        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        switch (game.getWinType()[2]){

            case 1 :
                drawHorizontalLine(canvas,row,col);
                break;
            case 2 :
                drawVerticalLine(canvas,row,col);
                break;
            case 3 :
                drawDiogonalLineneg(canvas);
                break;
            case 4:
                drawDiogonalLinePos(canvas);
                break;

        }
    }
  public void setUpGame(Button playAgain , Button home , TextView playerDisplay , String[] name){

 game.setPlayAgainBTN(playAgain);
 game.setHomeBYN(home);
 game.setPlayerTern(playerDisplay);
 game.setPlayerName(name);


  }
  public void resetGame(){

        game.resetGame();
        winningLine = false;
  }
}

