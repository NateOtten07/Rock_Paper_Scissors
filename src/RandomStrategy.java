import java.util.Random;
public class RandomStrategy implements Strategy
{
    @Override
    public String getMove(String playerMove)
    {
        RandomStrategy rand = new RandomStrategy();
        Random rnd = new Random();
        String computerMove = "";
        int randomNum = rnd.nextInt(3);
        switch (randomNum)
        {
            case 0:
                computerMove = "R";
                break;
            case 1:
                computerMove = "P";
                break;
            case 2:
                computerMove = "S";
                break;
            default:
                computerMove = "X";
                break;
        }
        return computerMove;
    }
}
