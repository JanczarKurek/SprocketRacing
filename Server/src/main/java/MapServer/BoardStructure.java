package MapServer;


import java.util.*;

public class BoardStructure {                       //Board is a directed acyclic graph.
    private TreeMap<Integer, BoardField> fields;    //id to field. Between structures, fields are identified by ID.
                                                    //each field has distinct const ID.
    public boolean checkPath(Path path){
        ArrayList<BoardField> internalPath = internalPath(path);
        for(int i = 0; i < (internalPath.size() - 1); ++i){
            if(!internalPath.get(i).getNextFields().contains(internalPath.get(i+1))){
                return false;
            }
        }
        return true;
    }
    private BoardField start;
    private BoardField end;

    public BoardStructure(){
        fields = new TreeMap<>();
    }

    private ArrayList<BoardField> internalPath(Path path){
        ArrayList<BoardField> internalPath = new ArrayList<>();
        for(int id : path){
            internalPath.add(fields.get(id));
        }
        return internalPath;
    }

    public int getStart() {
        return start.getId();
    }

    public int getEnd() {
        return end.getId();
    }

    public Collection<OnPassEffect> effectsOnPath(Path path){
        LinkedList<OnPassEffect> ret = new LinkedList<>();
        ArrayList<BoardField> internalPath = internalPath(path);
        for(BoardField field : internalPath){
            ret.addAll(field.getOnPassEffects());
        }
        return ret;
    }

    public void setStart(BoardField start) {
        this.start = start;
    }

    public void setEnd(BoardField end) {
        this.end = end;
    }

    public boolean setField(BoardField field){
        if(fields.containsKey(field.getId()))
            return false;
        else{
            fields.put(field.getId(), field);
            return true;
        }
    }

    public Collection<OnStayEffect> getOnStayEffectsFromField(Integer fieldId){
        return fields.get(fieldId).getOnStayEffects();
    }
}