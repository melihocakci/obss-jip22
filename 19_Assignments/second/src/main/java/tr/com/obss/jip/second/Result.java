package tr.com.obss.jip.second;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private final List<Bean> listBean = new ArrayList<>();

    public void add(Bean item) {
        listBean.add(item);
    }
}
