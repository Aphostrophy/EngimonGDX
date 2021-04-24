package com.ungabunga.model.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.ungabunga.model.entities.Engimon;
import com.ungabunga.model.entities.LivingEngimon;
import com.ungabunga.model.entities.Skill;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.enums.DIRECTIONS;
import com.ungabunga.model.enums.IElements;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResourceProvider {

    AssetManager assetManager;
    CopyOnWriteArrayList<? extends Engimon> engimon;
    CopyOnWriteArrayList<Skill> skills;

    HashMap<IElements, CellType> mapElementToBiome;

    public ResourceProvider(){
        this.assetManager = new AssetManager();
        assetManager = new AssetManager();
        assetManager.load("pic/packed/avatarTextures.atlas", TextureAtlas.class);
        assetManager.load("pic/packed/uipack.atlas", TextureAtlas.class);
        assetManager.load("pic/font/small_letters_font.fnt", BitmapFont.class);

        assetManager.load("pic/engimon_packed/arcanine.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/blastoise.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/cubone.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/diglett.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/glaceon.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/glalie.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/magmar.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/mareep.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/ninetales.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/psyduck.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/raichu.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/shark.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/tongkol.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/voltorb.atlas", TextureAtlas.class);
        assetManager.load("pic/engimon_packed/wartotle.atlas", TextureAtlas.class);

        assetManager.finishLoading();

        this.skills = fileUtil.readSkillCSV();
        this.engimon = fileUtil.readEngimonCSV(this);

        HashMap<IElements, CellType> elementBiomeMap = new HashMap<>();
        elementBiomeMap.put(IElements.FIRE,CellType.MOUNTAIN);
        elementBiomeMap.put(IElements.WATER,CellType.SEA);
        elementBiomeMap.put(IElements.GROUND,CellType.GRASSLAND);
        elementBiomeMap.put(IElements.ELECTRIC,CellType.GRASSLAND);
        elementBiomeMap.put(IElements.ICE,CellType.TUNDRA);
        this.mapElementToBiome = elementBiomeMap;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Skill getSkill(String name){
        for(Skill skill : this.skills){
            if(skill.getSkillName().equals(name)){
                return skill;
            }
        }
        return null;
    }

    public Engimon randomizeEngimon(CellType biomes){
        ArrayList<Engimon> candidates = new ArrayList<>();
        for(Engimon engimon: this.engimon){
            for(IElements engimonElement: engimon.getElements()){
                if(mapElementToBiome.get(engimonElement).equals(biomes)){
                    candidates.add(engimon);
                }
            }
        }
        if(candidates.size()==0){
            return null;
        }
        int x = ThreadLocalRandom.current().nextInt(0,candidates.size());
        return candidates.get(x);
    }

//    public TextureRegion getSprite(LivingEngimon engimon, AVATAR_STATE state, DIRECTIONS directions){
//
//    }
}
