package com.ungabunga.model.utilities;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class TexturePackerTool {
    public static void main (String[] args) throws Exception {
//        TexturePacker.process("pic/unpacked", "pic/packed", "avatarTextures");
        TexturePacker.process(
                "pic/unpacked",
                "pic/packed",
                "uipack");
    }
}
