package edu.ucsb.cs.cs185.nivek325.showmethemoney;

/**
 * Created by Kevin on 3/18/17.
 */

public class BudgetManager {
    private static float foodBudget = 500.0f;
    private static float entertainmentBudget = 100.0f;
    private static float livingBudget = 2000.0f;
    private static float otherBudget = 300.0f;


    public static void setFoodBudget(float foodBudget) {
        BudgetManager.foodBudget = foodBudget;
    }

    public static void setEntertainmentBudget(float entertainmentBudget) {
        BudgetManager.entertainmentBudget = entertainmentBudget;
    }

    public static void setLivingBudget(float livingBudget) {
        BudgetManager.livingBudget = livingBudget;
    }

    public static void setOtherBudget(float otherBudget) {
        BudgetManager.otherBudget = otherBudget;
    }

    public static float getFoodBudget() {
        return BudgetManager.foodBudget;
    }

    public static float getEntertainmentBudget() {
        return BudgetManager.entertainmentBudget;
    }

    public static float getLivingBudget() {
        return BudgetManager.livingBudget;
    }

    public static float getOtherBudget() {
        return BudgetManager.otherBudget;
    }
}
