package Settings;

//Singleton!
public class Settings {
    private static Settings globalThis;
    private Settings() {

    }
    public static Settings getSettings(){
        if(globalThis == null){
            globalThis = new Settings();
        }
        return globalThis;
    }

}
