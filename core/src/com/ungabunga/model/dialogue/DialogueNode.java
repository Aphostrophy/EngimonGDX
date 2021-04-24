package com.ungabunga.model.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DialogueNode {
    private ArrayList<Integer> pointers = new ArrayList<Integer>();
    private ArrayList<String> labels = new ArrayList<String>();

    private String text;
    private int id;
    private NODE_TYPE type;

    public enum NODE_TYPE {
        MULT,
        LINEAR,
        END
    }
    public  DialogueNode(String text, int id) {
        this.text = text;
        this.id = id;
        this.type = NODE_TYPE.END;
    }
    public void addChoice(String opt, int NodeID) {
        if(this.type == NODE_TYPE.LINEAR) {
            this.pointers.clear();
        }
        this.labels.add(opt);
        this.pointers.add(NodeID);
        this.type = NODE_TYPE.MULT;
    }

    public void makeLinear(int NodeID) {
        this.pointers.clear();
        this.labels.clear();
        this.pointers.add(NodeID);
        this.type = NODE_TYPE.LINEAR;
    }

    public List<Integer> getPointers() {
        return this.pointers;
    }

    public List<String> getLabels() {
        return this.labels;
    }

    public NODE_TYPE getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return text;
    }
}
