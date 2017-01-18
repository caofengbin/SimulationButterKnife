package cfb.com.simulationbutterknife.sample2;

/**
 * Created by Administrator on 2017/1/10.
 */

@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal {
    @Override
    public float getPrice() {
        return 8.5f;
    }
}
