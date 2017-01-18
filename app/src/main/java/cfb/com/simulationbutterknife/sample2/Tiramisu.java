package cfb.com.simulationbutterknife.sample2;

/**
 * Created by Administrator on 2017/1/10.
 */

@Factory(
        id = "Tiramisu",
        type = Meal.class
)
public class Tiramisu implements Meal {
    @Override
    public float getPrice() {
        return 4.5f;
    }
}
