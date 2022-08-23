package tr.com.obss.jip.second.menu.food;

import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;
import tr.com.obss.jip.second.menu.MenuItem;

@Food(price = 30)
public class Sushi implements MenuItem {
    @Time(takes = 30)
    public void prepare() {}

    public void cook() {}

    @Time(takes = 20)
    public void send() {}
}
