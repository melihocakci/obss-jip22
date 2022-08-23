package tr.com.obss.jip.second;

import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;
import tr.com.obss.jip.second.menu.Kebab;
import tr.com.obss.jip.second.menu.Pizza;
import tr.com.obss.jip.second.menu.Sushi;

import java.lang.reflect.Method;

public class MainClass {
    public static void main(String[] args) {
        Class<?>[] items = new Class[] {Kebab.class, Pizza.class, Sushi.class};
        Result result = new Result();

        for (Class<?> item : items) {
            Bean bean = new Bean();

            bean.setName(item.getSimpleName());

            if (item.isAnnotationPresent(Food.class)) {
                Food food = item.getAnnotation(Food.class);
                bean.setPrice(food.price());
            }

            Method[] methods = item.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Time.class)) {
                    Time time = method.getAnnotation(Time.class);
                    bean.setTime(bean.getTime() + time.takes());
                }
            }

            result.add(bean);
        }

        System.out.println("done");
    }
}
