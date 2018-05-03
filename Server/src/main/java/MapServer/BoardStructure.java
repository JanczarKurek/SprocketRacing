package MapServer;


import java.util.*;

public class BoardStructure {                       //Board is a directed acyclic graph.
    private TreeMap<Integer, BoardField> fields;    //id to field. Between structures, fields are identified by ID.
                                                    //each field has distinct const ID.
    public boolean checkPath(Path path){
        ArrayList<BoardField> internalPath = internalPath(path);
        for(int id : path){
            internalPath.add(fields.get(id));
        }
        for(int i = 0; i < internalPath.size(); ++i){
            if(!internalPath.get(i).getNextFields().contains(internalPath.get(i+1))){
                return false;
            }
        }
        return true;
    }
    private BoardField start;
    private BoardField end;

    private ArrayList<BoardField> internalPath(Path path){
        ArrayList<BoardField> internalPath = new ArrayList<>();
        for(int id : path){
            internalPath.add(fields.get(id));
        }
        return internalPath;
    }

    public BoardField getStart() {
        return start;
    }

    public BoardField getEnd() {
        return end;
    }

    public Collection<OnPassEffect> effectsOnPath(Path path){
        LinkedList<OnPassEffect> ret = new LinkedList<>();
        ArrayList<BoardField> internalPath = internalPath(path);
        for(BoardField field : internalPath){
            ret.addAll(field.getOnPassEffects());
        }
        return ret;
    }
}