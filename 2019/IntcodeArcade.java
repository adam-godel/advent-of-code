public class IntcodeArcade extends Intcode {
    private char[][] screen;
    public IntcodeArcade(String input, char[][] screen) {
        super(input);
        this.screen = screen;
    }
    private long movePaddle() {
        int ballX = 0, paddleX = 0;
        for (int i = 0; i < screen.length; i++)
            for (int j = 0; j < screen[i].length; j++)
                if (screen[i][j] == '◯')
                    ballX = j;
                else if (screen[i][j] == '▔')
                    paddleX = j;
        if (ballX > paddleX)
            return 1;
        else if (ballX < paddleX)
            return -1;
        return 0;
    }
    public long getInput() {
        return movePaddle();
    }
}
