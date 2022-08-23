package tr.com.obss.jip.second.food;

import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;

@Food(price = 12.5)
public class Kebab implements FoodInterface {
    @Time(takes = 12)
    public void prepare() {}

    @Time(takes = 20)
    public void cook() {}

    @Time(takes = 10)
    public void send() {}
}
