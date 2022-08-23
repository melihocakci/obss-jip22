package tr.com.obss.jip.second.menu;

import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;

@Food(price = 22.5)
public class Pizza implements MenuItem {
    @Time(takes = 10)
    public void prepare() {}

    @Time(takes = 20)
    public void cook() {}

    @Time(takes = 15)
    public void send() {}
}
