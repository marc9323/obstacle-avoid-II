package com.staticvoid.avoid.config;

public enum DifficultyLevel { // by default extends java Enum class
    // call enum constructor, invoked here where constants are declared
    EASY(GameConfig.EASY_OBSTACLE_SPEED),
    MEDIUM(GameConfig.MEDIUM_OBSTACLE_SPEED),
    HARD(GameConfig.HARD_OBSTACLE_SPEED);

    private final float obstacleSpeed;

    DifficultyLevel(float obstacleSpeed) {
        this.obstacleSpeed = obstacleSpeed;
    }

    public float getObstacleSpeed() {
        return obstacleSpeed;
    }

    public boolean isEasy() {
        return this == EASY;
    }

    public boolean isMedium() {
        return this == MEDIUM;
    }

    public boolean isHard() {
        return this == HARD;
    }
}


/*
Enum specifies a list of constant values
all are automatically public static final

may contain constructors methods and variables as well
enum constructors my not be directly invoked in our code

constructor is always called automatically when enum is initialized
initialization == first use

used in switch case and may be compared with == operator
 */