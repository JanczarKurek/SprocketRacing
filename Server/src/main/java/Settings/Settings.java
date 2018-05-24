package Settings;

import java.util.TreeMap;

//Singleton!
public class Settings {
    private static Settings globalThis;
    TreeMap<String, String> records = new TreeMap<>();
    void set(String key, String value){
        records.put(key, value);
    }

    String getValue(String key){
        return records.get(key);
    }

    Integer getIntegerValue(String key){
        return Integer.valueOf(records.get(key));
    }

    Double getDoubleValue(String key){
        return Double.valueOf(records.get(key));
    }

    private Settings() {

    }
    public static Settings getSettings(){
        if(globalThis == null){
            globalThis = new Settings();
        }
        return globalThis;
    }


}
