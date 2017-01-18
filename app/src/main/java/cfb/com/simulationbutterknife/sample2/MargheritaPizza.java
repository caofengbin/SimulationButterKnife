package cfb.com.simulationbutterknife.sample2;

/**
 * Created by Administrator on 2017/1/10.
 */

@Factory(
        id = "Margherita",
        type = Meal.class
)
public class MargheritaPizza implements Meal {

    @Override
    public float getPrice() {
        return 6.0f;
    }
}
