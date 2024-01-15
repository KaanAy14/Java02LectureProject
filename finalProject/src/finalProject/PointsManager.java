package finalProject;

public class PointsManager {
    private static PointsManager instance;
    private int totalPoints;
    private int diamondPoints;

    private PointsManager() {
        totalPoints = 0;
        diamondPoints = 0;
    }

    public static PointsManager getInstance() {
        if (instance == null) {
            instance = new PointsManager();
        }
        return instance;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void addPoints(int points) {
        totalPoints += points;
    }

    public void resetPoints() {
        totalPoints = 0;
    }
    
    public int getDiamondPoints() {
        return diamondPoints;
    }

    public void addDiamondPoints(int points) {
        diamondPoints += points;
    }

    public void resetDiamondPoints() {
        diamondPoints = 0;
    }
}
