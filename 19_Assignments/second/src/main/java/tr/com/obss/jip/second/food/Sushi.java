package tr.com.obss.jip.second.food;

import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;

@Food(price = 30)
public class Sushi implements FoodInterface {
    @Time(takes = 30)
    public void prepare() {}

    public void cook() {}

    @Time(takes = 20)
    public void send() {}
}
