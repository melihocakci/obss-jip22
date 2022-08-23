package tr.com.obss.jip.second;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import tr.com.obss.jip.second.annotation.Food;
import tr.com.obss.jip.second.annotation.Time;

import java.lang.reflect.Method;
import java.util.Set;

public class MainClass {
    public static void main(String[] args) {
        Reflections reflections =
                new Reflections("tr.com.obss.jip.second.menu.food", new SubTypesScanner(false));

        Set<Class<?>> items = reflections.getSubTypesOf(Object.class);

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
