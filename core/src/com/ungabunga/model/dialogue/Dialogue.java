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

    public int size() {
        return nodes.size();
    }

    public static Dialogue generateDialogue(String... lines) {
        Dialogue dialogue = new Dialogue();
        for (int i = 0; i < lines.length; i++) {
            DialogueNode node = new DialogueNode(lines[i], i);
            dialogue.addNode(node);
            if (i != 0) {
                dialogue.getNode(i-1).makeLinear(i);
            }
        }
        return dialogue;
    }

}
