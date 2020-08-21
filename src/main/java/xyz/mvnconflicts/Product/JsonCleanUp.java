package xyz.mvnconflicts.Product;

import java.util.Map;

public abstract class JsonCleanUp {

    public void removeKey(Map.Entry<?, ?> e) {
        if(e.getValue() instanceof Map) {
            cleaner((Map<?, ?>) e.getValue());
        } else {
            if(e.getKey().equals("Level")) {
                e.setValue(null);
            }
        }
    }
    public void cleaner(Map<?,?> map){
        map.entrySet().forEach(this::removeKey);
    }
}
