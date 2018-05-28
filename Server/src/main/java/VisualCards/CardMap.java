package VisualCards;

import java.util.HashMap;
import java.util.Map;

public class CardMap {
    public static class StaticMap {
        private static Map<Integer, VisualCard> map;

        public StaticMap() {
            map = new HashMap<>();
        }

        public void put(Integer key, VisualCard card) {
            map.put(key, card);
        }

        public VisualCard get(Integer i) {
            return map.get(i);
        }

        public boolean empty() {
            return map.isEmpty();
        }

        public int size() {
            return map.size();
        }
    }
}
