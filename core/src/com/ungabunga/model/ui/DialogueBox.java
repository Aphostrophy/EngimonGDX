package com.ungabunga.model.ui;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
public class DialogueBox extends Table{
    private  String text = "";
    private float animTimer = 0f;
    private float animationTime = 0f;
    private float TIME_PER_CHAR = 0.05f;
    private STATE state = STATE.IDLE;

    private Label textLabel;
    private enum STATE {
        ANIMATING,
        IDLE
    }

    public DialogueBox(Skin skin) {
        super(skin);
        this.setBackground("dialoguebox");
        textLabel = new Label("\n", skin);
        this.add(textLabel).expand().align(Align.left).pad(5f);
    }

    public void animateText(String s) {
        this.text = s;
        animationTime = text.length()*TIME_PER_CHAR;
        state = STATE.ANIMATING;
        animTimer = 0f;
    }

    public boolean isFinished() {
        if(state == STATE.IDLE) {
            return true;
        }
        else {
            return false;
        }
    }

    private void setText(String s) {
        if(!s.contains("\n")) {
            s += "\n";
        }
        this.textLabel.setText(s);
    }

    public void act(float d) {
        super.act(d);
        if(this.state == STATE.ANIMATING) {
            this.animTimer += d;
            if(animTimer > animationTime) {
                this.state = STATE.IDLE;
                this.animTimer = animationTime;
            }
            String displayText = "";
            int displayChar = (int)((animTimer/animationTime) * text.length());
            for(int i =0 ;i<displayChar;i++) {
                displayText+= text.charAt(i);
            }
            if(!displayText.equals(textLabel.getText().toString())) {
                setText(displayText);
            }
        }
    }
    public float getPrefWidth(){
        return 200f;
    }
}
