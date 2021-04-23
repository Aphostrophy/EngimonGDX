package com.ungabunga.model.dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import java.util.Map;
import java.util.HashMap;

public class Dialogue {
    private Map<Integer, DialogueNode> nodes = new HashMap<Integer, DialogueNode>();

    public DialogueNode getNode(int ID) {
        return this.nodes.get(ID);
    }

    public void addNode(DialogueNode DN) {
        this.nodes.put(DN.getId(),DN);
    }

}
