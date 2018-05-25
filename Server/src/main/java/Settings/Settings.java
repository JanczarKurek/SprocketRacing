package Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
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

    public static void getFromEnv(){
        Map<String, String> env = System.getenv();
        String path = env.get("SPROCKETSETTINGS");
        File file = new File(path);
        FileInputStream stream;
        try {
            stream = new FileInputStream(file);
            Scanner fileReader = new Scanner(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getResourcesPath(){
        Map<String, String> env = System.getenv();
        return env.get("SPROCKETFILEPATH");
    }

    public static Settings getSettings(){
        if(globalThis == null){
            globalThis = new Settings();
        }
        return globalThis;
    }


}
