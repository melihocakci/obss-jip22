package tr.com.obss.jip.second.menu;

import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;

@Food(price = 12.5)
public class Kebab implements MenuItem {
    @Time(takes = 12)
    public void prepare() {}

    @Time(takes = 20)
    public void cook() {}

    @Time(takes = 10)
    public void send() {}
}
