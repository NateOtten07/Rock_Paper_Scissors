import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * RockPaperScissorsFrame is a Swing-based GUI application for playing Rock-Paper-Scissors
 * against a computer opponent that uses multiple strategies.
 */
public class RockPaperScissorsFrame extends JFrame {

    String playerMove;
    JPanel mainPnl;
    JPanel controlPnl;
    JPanel statsPnl;
    JPanel textPnl;

    JScrollPane scroller;
    JTextArea resultsPane;

    ImageIcon rock = new ImageIcon(
            new ImageIcon("src/rock.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
    );
    ImageIcon paper = new ImageIcon(
            new ImageIcon("src/paper.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
    );
    ImageIcon scissors = new ImageIcon(
            new ImageIcon("src/scissors.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)
    );

    JButton rockBtn = new JButton(rock);
    JButton paperBtn = new JButton(paper);
    JButton scissorsBtn = new JButton(scissors);
    JButton quitBtn = new JButton("Quit");

    JLabel playWinLbl;
    JLabel tiesLbl;
    JLabel compWinLbl;

    JTextField playWinTf;
    JTextField tiesTf;
    JTextField compWinTf;

    int playRock = 0;
    int playPaper = 0;
    int playScissors = 0;

    int compWins = 0;
    int playWins = 0;
    int ties = 0;

    int compRock = 0;
    int compPaper = 0;
    int compScissors = 0;

    String lastCompMove = "";
    String lastPlayMove = "";
    String compStrategy = "";

    private Strategy strategy;

    Cheat cheat = new Cheat();
    RandomStrategy random = new RandomStrategy();
    leastUsedStrategy leastUsed = new leastUsedStrategy();
    mostUsedStrategy mostUsed = new mostUsedStrategy();
    lastUsedStrategy lastUsed = new lastUsedStrategy();

    /**
     * Constructs the RockPaperScissorsFrame and initializes the GUI.
     */
    public RockPaperScissorsFrame() {
        super("Rock Paper Scissors");
        setSize(400, 400);
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);
        createControlPanel();
        mainPnl.add(controlPnl, BorderLayout.NORTH);
        createTextPanel();
        mainPnl.add(textPnl, BorderLayout.CENTER);
        createStatsPanel();
        mainPnl.add(statsPnl, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Determines the computer's move using a randomized strategy selection.
     *
     * @param playerMove the player's move ("R", "P", or "S")
     * @return the computer's move ("R", "P", or "S")
     */
    public String getCompMove(String playerMove) {
        Random rand = new Random();
        int rndStrat = rand.nextInt(100) + 1;
        String compMove = "";
        if (rndStrat <= 10) {
            compStrategy = "Cheat";
            compMove = cheat.getMove(playerMove);
        } else if (rndStrat <= 30) {
            compStrategy = "Least Used";
            compMove = leastUsed.getMove(playerMove);
        } else if (rndStrat <= 50) {
            compStrategy = "Most Used";
            compMove = mostUsed.getMove(playerMove);
        } else if (rndStrat <= 70) {
            compStrategy = "Last Used";
            compMove = lastUsed.getMove(playerMove);
        } else {
            compStrategy = "Random";
            compMove = random.getMove(playerMove);
        }
        switch (compMove) {
            case "R":
                compRock++;
                lastCompMove = "R";
                break;
            case "P":
                compPaper++;
                lastCompMove = "P";
                break;
            case "S":
                compScissors++;
                lastCompMove = "S";
                break;
            default:
                compMove = "X";
                break;
        }
        return compMove;
    }

    /**
     * Resolves a round of the game, updates statistics, and appends results.
     *
     * @param playerMove the player's move ("R", "P", or "S")
     */
    public void resolveMove(String playerMove) {
        String compMove = getCompMove(playerMove);
        String resultsStr = "";
        resultsPane.append("Player Move: " + playerMove + " Computer Move: " + compMove + "\n");
        if (compMove == playerMove) {
            if (compMove == "R") {
                resultsStr = "Rock vs Rock! It's a tie!";
            } else if (compMove == "P") {
                resultsStr = "Paper vs Paper! It's a tie!";
            } else if (compMove == "S") {
                resultsStr = "Scissors vs Scissors! It's a tie!";
            }
            ties++;
            tiesTf.setText(ties + "");
        } else if (playerMove == "R") {
            if (compMove == "P") {
                resultsStr = "Paper Covers Rock! Computer Wins!";
                compWins++;
                compWinTf.setText(compWins + "");
            } else if (compMove == "S") {
                resultsStr = "Rock Crushes Scissors! Player Wins!";
                playWins++;
                playWinTf.setText(playWins + "");
            }
        } else if (playerMove == "S") {
            if (compMove == "P") {
                resultsStr = "Scissors Cut Paper! Player Wins!";
                playWins++;
                playWinTf.setText(playWins + "");
            } else if (compMove == "R") {
                resultsStr = "Rock Crushes Scissors! Computer Wins!";
                compWins++;
                compWinTf.setText(compWins + "");
            }
        }
        resultsPane.append(resultsStr + " Computer Strategy: " + compStrategy + "\n");
    }

    /**
     * Creates the text panel containing the results area.
     */
    private void createTextPanel() {
        textPnl = new JPanel();
        resultsPane = new JTextArea(20, 50);
        resultsPane.setEditable(false);
        scroller = new JScrollPane(resultsPane);
        textPnl.add(scroller);
    }

    /**
     * Creates the statistics panel showing wins and ties.
     */
    private void createStatsPanel() {
        statsPnl = new JPanel();
        statsPnl.setLayout(new GridLayout(1, 6));
        playWinLbl = new JLabel("Player Wins: ");
        compWinLbl = new JLabel("Computer Wins: ");
        tiesLbl = new JLabel("Ties: ");
        playWinTf = new JTextField(3);
        playWinTf.setText(0 + "");
        compWinTf = new JTextField(3);
        compWinTf.setText(0 + "");
        tiesTf = new JTextField(3);
        tiesTf.setText(0 + "");
        statsPnl.add(compWinLbl);
        statsPnl.add(compWinTf);
        statsPnl.add(tiesLbl);
        statsPnl.add(playWinTf);
        statsPnl.add(playWinLbl);
        statsPnl.add(tiesTf);
    }

    /**
     * Creates the control panel with buttons for moves and quit.
     */
    private void createControlPanel() {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1, 4));
        controlPnl.add(rockBtn);
        controlPnl.add(paperBtn);
        controlPnl.add(scissorsBtn);
        controlPnl.add(quitBtn);
        rockBtn.addActionListener(e -> {
            playRock++;
            lastPlayMove = "R";
            resolveMove("R");
        });
        paperBtn.addActionListener(e -> {
            playPaper++;
            lastPlayMove = "P";
            resolveMove("P");
        });
        scissorsBtn.addActionListener(e -> {
            playScissors++;
            lastPlayMove = "S";
            resolveMove("S");
        });
        quitBtn.addActionListener(e -> System.exit(0));
    }

    /**
     * Strategy that selects the least used player move and counters it.
     */
    class leastUsedStrategy implements Strategy {
        @Override
        public String getMove(String playerMove) {
            int min = Math.min(playRock, Math.min(playPaper, playScissors));
            if (playRock == min)
                return "P";
            else if (playPaper == min)
                return "S";
            else
                return "R";
        }
    }

    /**
     * Strategy that selects the most used player move and counters it.
     */
    class mostUsedStrategy implements Strategy {
        @Override
        public String getMove(String playerMove) {
            int max = Math.max(playRock, Math.max(playPaper, playScissors));
            if (playRock == max)
                return "P";
            else if (playPaper == max)
                return "S";
            else
                return "R";
        }
    }

    /**
     * Strategy that repeats the player's last move, or random if none exists.
     */
    class lastUsedStrategy implements Strategy {
        private String lastMove = "";
        @Override
        public String getMove(String playerMove) {
            if (lastPlayMove.equals("")) {
                return random.getMove(playerMove);
            } else
                return lastPlayMove;
        }
    }
}