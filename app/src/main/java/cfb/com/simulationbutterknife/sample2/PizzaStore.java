package cfb.com.simulationbutterknife.sample2;

/**
 * Created by Administrator on 2017/1/10.
 */

public class PizzaStore {

    private MealFactory mFactory = new MealFactory();

    public Meal order(String mealName) {
        return mFactory.create(mealName);
    }
}
