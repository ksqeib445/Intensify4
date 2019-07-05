package ksqeib.Intensify.store;

import java.util.ArrayList;
import java.util.List;

public class SuitEffect {

    public List<String> potionEffectList = new ArrayList<>();
    public int suitHp = 0;

    public SuitEffect(List<String> list1, int i) {
        potionEffectList = list1;
        suitHp = i;
    }
}
