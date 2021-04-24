package com.ungabunga.model.dialogue;
import java.util.List;
import com.ungabunga.model.dialogue.Dialogue;
import com.ungabunga.model.dialogue.DialogueNode;
import com.ungabunga.model.dialogue.DialogueNode.NODE_TYPE;

public class DialogueTraverser {
    private Dialogue dialogue;
    private DialogueNode currNode;

    public DialogueTraverser(Dialogue dialogue) {
        this.dialogue = dialogue;
        this.currNode = dialogue.getNode(0);
    }
    public DialogueNode getNext(int ptrIdx) {
        DialogueNode next = dialogue.getNode(currNode.getPointers().get(ptrIdx));
        currNode = next;
        return next;
    }
    public List<String> getOptions() {
        return currNode.getLabels();
    }
    public String getText() {
        return currNode.getText();
    }

    public NODE_TYPE getType() {
        return currNode.getType();
    }
}
