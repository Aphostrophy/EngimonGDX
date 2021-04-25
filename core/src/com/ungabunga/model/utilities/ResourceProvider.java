package com.ungabunga.model.utilities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ungabunga.model.entities.*;
import com.ungabunga.model.enums.AVATAR_STATE;
import com.ungabunga.model.enums.CellType;
import com.ungabunga.model.enums.DIRECTION;
import com.ungabunga.model.enums.IElements;
import com.ungabunga.model.exceptions.OutOfBoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.ungabunga.Settings.ANIM_TIMER;

public class ResourceProvider {

    AssetManager assetManager;
    CopyOnWriteArrayList<? extends Engimon> engimon;
    CopyOnWriteArrayList<Skill> skills;

    HashMap<IElements, CellType> mapElementToBiome;

    AnimationSet arcanineAnimationSet;
    AnimationSet blastoiseAnimationSet;
    AnimationSet charmanderAnimationSet;
    AnimationSet cubchooAnimationSet;
    AnimationSet cuboneAnimationSet;
    AnimationSet diglettAnimationSet;
    AnimationSet dugtrioAnimationSet;
    AnimationSet glaceonAnimationSet;
    AnimationSet glalieAnimationSet;
    AnimationSet growlitheAnimationSet;
    AnimationSet magmarAnimationSet;
    AnimationSet mareepAnimationSet;
    AnimationSet ninetalesAnimationSet;
    AnimationSet pikachuAnimationSet;
    AnimationSet poliwagAnimationSet;
    AnimationSet ponytaAnimationSet;
    AnimationSet psyduckAnimationSet;
    AnimationSet raichuAnimationSet;
    AnimationSet sandshrewAnimationSet;
    AnimationSet sharkAnimationSet;
    AnimationSet snoruntAnimationSet;
    AnimationSet squirtleAnimationSet;
    AnimationSet tongkolAnimationSet;
    AnimationSet voltorbAnimationSet;
    AnimationSet wartotleAnimationSet;
    AnimationSet vulpixAnimationSet;

    public ResourceProvider(){
        this.assetManager = new AssetManager();
        assetManager = new AssetManager();
        assetManager.load("pic/packed/avatarTextures.atlas", TextureAtlas.class);
        assetManager.load("pic/packed/uipack.atlas", TextureAtlas.class);
        assetManager.load("pic/font/small_letters_font.fnt", BitmapFont.class);

        assetManager.load("pic/engimon_packed/arcanine.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/blastoise.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/charmander.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/cubchoo.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/cubone.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/diglett.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/dugtrio.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/glaceon.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/glalie.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/growlithe.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/magmar.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/mareep.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/ninetales.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/pikachu.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/poliwag.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/ponyta.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/psyduck.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/raichu.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/sandshrew.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/shark.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/snorunt.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/squirtle.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/tongkol.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/voltorb.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/vulpix.atlas", TextureAtlas.class);
       assetManager.load("pic/engimon_packed/wartotle.atlas", TextureAtlas.class);
       assetManager.load("pic/packed/skillLogo.atlas", TextureAtlas.class);
        assetManager.load("pic/packed/skillItemLogo.atlas", TextureAtlas.class);

        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get("pic/engimon_packed/arcanine.atlas", TextureAtlas.class);
        arcanineAnimationSet = new AnimationSet(
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("arcanine_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("arcanine_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("arcanine_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("arcanine_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("arcanine_standing_south"),
                atlas.findRegion("arcanine_standing_north"),
                atlas.findRegion("arcanine_standing_west"),
                atlas.findRegion("arcanine_standing_east")
        );

       atlas = assetManager.get("pic/engimon_packed/blastoise.atlas", TextureAtlas.class);
       blastoiseAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("blastoise_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("blastoise_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("blastoise_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("blastoise_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("blastoise_standing_south"),
               atlas.findRegion("blastoise_standing_north"),
               atlas.findRegion("blastoise_standing_west"),
               atlas.findRegion("blastoise_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/charmander.atlas", TextureAtlas.class);
       charmanderAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("charmander_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("charmander_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("charmander_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("charmander_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("charmander_standing_south"),
               atlas.findRegion("charmander_standing_north"),
               atlas.findRegion("charmander_standing_west"),
               atlas.findRegion("charmander_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/cubchoo.atlas", TextureAtlas.class);
       cubchooAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubchoo_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubchoo_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubchoo_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubchoo_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("cubchoo_standing_south"),
               atlas.findRegion("cubchoo_standing_north"),
               atlas.findRegion("cubchoo_standing_west"),
               atlas.findRegion("cubchoo_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/cubone.atlas", TextureAtlas.class);
       cuboneAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubone_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubone_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubone_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("cubone_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("cubone_standing_south"),
               atlas.findRegion("cubone_standing_north"),
               atlas.findRegion("cubone_standing_west"),
               atlas.findRegion("cubone_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/diglett.atlas", TextureAtlas.class);
       diglettAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("diglett_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("diglett_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("diglett_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("diglett_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("diglett_standing_south"),
               atlas.findRegion("diglett_standing_north"),
               atlas.findRegion("diglett_standing_west"),
               atlas.findRegion("diglett_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/dugtrio.atlas", TextureAtlas.class);
       dugtrioAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("dugtrio_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("dugtrio_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("dugtrio_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("dugtrio_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("dugtrio_standing_south"),
               atlas.findRegion("dugtrio_standing_north"),
               atlas.findRegion("dugtrio_standing_west"),
               atlas.findRegion("dugtrio_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/glaceon.atlas", TextureAtlas.class);
       glaceonAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glaceon_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glaceon_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glaceon_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glaceon_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("glaceon_standing_south"),
               atlas.findRegion("glaceon_standing_north"),
               atlas.findRegion("glaceon_standing_west"),
               atlas.findRegion("glaceon_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/glalie.atlas", TextureAtlas.class);
       glalieAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glalie_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glalie_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glalie_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("glalie_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("glalie_standing_south"),
               atlas.findRegion("glalie_standing_north"),
               atlas.findRegion("glalie_standing_west"),
               atlas.findRegion("glalie_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/growlithe.atlas", TextureAtlas.class);
       growlitheAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("growlithe_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("growlithe_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("growlithe_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("growlithe_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("growlithe_standing_south"),
               atlas.findRegion("growlithe_standing_north"),
               atlas.findRegion("growlithe_standing_west"),
               atlas.findRegion("growlithe_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/magmar.atlas", TextureAtlas.class);
       magmarAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("magmar_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("magmar_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("magmar_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("magmar_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("magmar_standing_south"),
               atlas.findRegion("magmar_standing_north"),
               atlas.findRegion("magmar_standing_west"),
               atlas.findRegion("magmar_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/mareep.atlas", TextureAtlas.class);
       mareepAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("mareep_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("mareep_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("mareep_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("mareep_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("mareep_standing_south"),
               atlas.findRegion("mareep_standing_north"),
               atlas.findRegion("mareep_standing_west"),
               atlas.findRegion("mareep_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/ninetales.atlas", TextureAtlas.class);
       ninetalesAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ninetales_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ninetales_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ninetales_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ninetales_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("ninetales_standing_south"),
               atlas.findRegion("ninetales_standing_north"),
               atlas.findRegion("ninetales_standing_west"),
               atlas.findRegion("ninetales_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/pikachu.atlas", TextureAtlas.class);
       pikachuAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("pikachu_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("pikachu_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("pikachu_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("pikachu_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("pikachu_standing_south"),
               atlas.findRegion("pikachu_standing_north"),
               atlas.findRegion("pikachu_standing_west"),
               atlas.findRegion("pikachu_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/poliwag.atlas", TextureAtlas.class);
       poliwagAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("poliwag_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("poliwag_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("poliwag_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("poliwag_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("poliwag_standing_south"),
               atlas.findRegion("poliwag_standing_north"),
               atlas.findRegion("poliwag_standing_west"),
               atlas.findRegion("poliwag_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/ponyta.atlas", TextureAtlas.class);
       ponytaAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ponyta_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ponyta_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ponyta_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("ponyta_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("ponyta_standing_south"),
               atlas.findRegion("ponyta_standing_north"),
               atlas.findRegion("ponyta_standing_west"),
               atlas.findRegion("ponyta_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/psyduck.atlas", TextureAtlas.class);
       psyduckAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("psyduck_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("psyduck_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("psyduck_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("psyduck_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("psyduck_standing_south"),
               atlas.findRegion("psyduck_standing_north"),
               atlas.findRegion("psyduck_standing_west"),
               atlas.findRegion("psyduck_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/raichu.atlas", TextureAtlas.class);
       raichuAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("raichu_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("raichu_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("raichu_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("raichu_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("raichu_standing_south"),
               atlas.findRegion("raichu_standing_north"),
               atlas.findRegion("raichu_standing_west"),
               atlas.findRegion("raichu_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/sandshrew.atlas", TextureAtlas.class);
       sandshrewAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("sandshrew_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("sandshrew_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("sandshrew_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("sandshrew_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("sandshrew_standing_south"),
               atlas.findRegion("sandshrew_standing_north"),
               atlas.findRegion("sandshrew_standing_west"),
               atlas.findRegion("sandshrew_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/shark.atlas", TextureAtlas.class);
       sharkAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("shark_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("shark_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("shark_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("shark_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("shark_standing_south"),
               atlas.findRegion("shark_standing_north"),
               atlas.findRegion("shark_standing_west"),
               atlas.findRegion("shark_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/snorunt.atlas", TextureAtlas.class);
       snoruntAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("snorunt_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("snorunt_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("snorunt_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("snorunt_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("snorunt_standing_south"),
               atlas.findRegion("snorunt_standing_north"),
               atlas.findRegion("snorunt_standing_west"),
               atlas.findRegion("snorunt_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/squirtle.atlas", TextureAtlas.class);
       squirtleAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("squirtle_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("squirtle_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("squirtle_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("squirtle_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("squirtle_standing_south"),
               atlas.findRegion("squirtle_standing_north"),
               atlas.findRegion("squirtle_standing_west"),
               atlas.findRegion("squirtle_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/tongkol.atlas", TextureAtlas.class);
       tongkolAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("tongkol_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("tongkol_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("tongkol_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("tongkol_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("tongkol_standing_south"),
               atlas.findRegion("tongkol_standing_north"),
               atlas.findRegion("tongkol_standing_west"),
               atlas.findRegion("tongkol_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/voltorb.atlas", TextureAtlas.class);
       voltorbAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("voltorb_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("voltorb_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("voltorb_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("voltorb_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("voltorb_standing_south"),
               atlas.findRegion("voltorb_standing_north"),
               atlas.findRegion("voltorb_standing_west"),
               atlas.findRegion("voltorb_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/wartotle.atlas", TextureAtlas.class);
       wartotleAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("wartotle_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("wartotle_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("wartotle_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("wartotle_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("wartotle_standing_south"),
               atlas.findRegion("wartotle_standing_north"),
               atlas.findRegion("wartotle_standing_west"),
               atlas.findRegion("wartotle_standing_east")
       );

       atlas = assetManager.get("pic/engimon_packed/vulpix.atlas", TextureAtlas.class);
       vulpixAnimationSet = new AnimationSet(
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("vulpix_walking_south"), Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("vulpix_walking_north"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("vulpix_walking_west"),Animation.PlayMode.LOOP_PINGPONG),
               new Animation<TextureRegion>(ANIM_TIMER/3f, atlas.findRegions("vulpix_walking_east"),Animation.PlayMode.LOOP_PINGPONG),
               atlas.findRegion("vulpix_standing_south"),
               atlas.findRegion("vulpix_standing_north"),
               atlas.findRegion("vulpix_standing_west"),
               atlas.findRegion("vulpix_standing_east")
       );


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

    public Engimon randomizeEngimon(CellType biomes,int level){
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
        Engimon engimon = candidates.get(x);
        engimon.setLevel(level);
        return engimon;
    }

    public Engimon getEngimon(String species) throws OutOfBoundException {
        for(Engimon engimon: this.engimon){
            if(engimon.getSpecies().equals(species)){
                return engimon;
            }
        }
        throw new OutOfBoundException("Engimon not found!");
    }

    public TextureRegion getSprite(LivingEngimon engimon){
        if (engimon.getEngimonSpecies().equals("Arcanine")) {
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return arcanineAnimationSet.getStanding(engimon.getDirection());
            } else {
                return arcanineAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Blastoise")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return blastoiseAnimationSet.getStanding(engimon.getDirection());
            }else {
                return blastoiseAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Charmander")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return charmanderAnimationSet.getStanding(engimon.getDirection());
            }else {
                return charmanderAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Cubchoo")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return cubchooAnimationSet.getStanding(engimon.getDirection());
            }else {
                return cubchooAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Cubone")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return cuboneAnimationSet.getStanding(engimon.getDirection());
            }else {
                return cuboneAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Diglett")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return diglettAnimationSet.getStanding(engimon.getDirection());
            }else {
                return diglettAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Dugtrio")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return dugtrioAnimationSet.getStanding(engimon.getDirection());
            }else {
                return dugtrioAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Glaceon")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return glaceonAnimationSet.getStanding(engimon.getDirection());
            }else {
                return glaceonAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Glalie")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return glalieAnimationSet.getStanding(engimon.getDirection());
            }else {
                return glalieAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        }else if (engimon.getEngimonSpecies().equals("Growlithe")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return growlitheAnimationSet.getStanding(engimon.getDirection());
            }else {
                return growlitheAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Magmar")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return magmarAnimationSet.getStanding(engimon.getDirection());
            }else {
                return magmarAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Mareep")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return mareepAnimationSet.getStanding(engimon.getDirection());
            }else {
                return mareepAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Ninetales")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return ninetalesAnimationSet.getStanding(engimon.getDirection());
            }else {
                return ninetalesAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Pikachu")) {
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return pikachuAnimationSet.getStanding(engimon.getDirection());
            } else {
                return pikachuAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Poliwag")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return poliwagAnimationSet.getStanding(engimon.getDirection());
            }else {
                return poliwagAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Ponyta")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return ponytaAnimationSet.getStanding(engimon.getDirection());
            }else {
                return ponytaAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Psyduck")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return psyduckAnimationSet.getStanding(engimon.getDirection());
            }else {
                return psyduckAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Raichu")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return raichuAnimationSet.getStanding(engimon.getDirection());
            }else {
                return raichuAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Sandshrew")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return sandshrewAnimationSet.getStanding(engimon.getDirection());
            }else {
                return sandshrewAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Shark")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return sharkAnimationSet.getStanding(engimon.getDirection());
            }else {
                return sharkAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Snorunt")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return snoruntAnimationSet.getStanding(engimon.getDirection());
            }else {
                return snoruntAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Squirtle")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return squirtleAnimationSet.getStanding(engimon.getDirection());
            }else {
                return squirtleAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Tongkol")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return tongkolAnimationSet.getStanding(engimon.getDirection());
            }else {
                return tongkolAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Voltorb")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return voltorbAnimationSet.getStanding(engimon.getDirection());
            }else {
                return voltorbAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Wartotle")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return wartotleAnimationSet.getStanding(engimon.getDirection());
            }else {
                return wartotleAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        } else if (engimon.getEngimonSpecies().equals("Vulpix")){
            if (engimon.getState().equals(AVATAR_STATE.STANDING)) {
                return vulpixAnimationSet.getStanding(engimon.getDirection());
            }else {
                return vulpixAnimationSet.getWalking(engimon.getDirection()).getKeyFrame(engimon.getKeyFrame());
            }
        }
        return arcanineAnimationSet.getStanding(engimon.getDirection());
    }

    public TextureRegion getSprite(PlayerEngimon engimon){
        if (engimon.getSpecies().equals("Arcanine")) {
            return arcanineAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Blastoise")) {
            return blastoiseAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Charmander")) {
            return charmanderAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Cubchoo")) {
            return cubchooAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Cubone")) {
            return cuboneAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Diglett")) {
            return diglettAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Dugtrio")) {
            return dugtrioAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Glaceon")) {
            return glaceonAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Glalie")) {
            return glalieAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Growlithe")) {
            return growlitheAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Magmar")) {
            return magmarAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Mareep")) {
            return mareepAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Ninetales")) {
            return ninetalesAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Pikachu")) {
            return pikachuAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Poliwag")) {
            return poliwagAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Ponyta")) {
            return ponytaAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Psyduck")) {
            return psyduckAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Raichu")) {
            return raichuAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Sandshrew")) {
            return sandshrewAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Shark")) {
            return sharkAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Snorunt")) {
            return snoruntAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Squirtle")) {
            return squirtleAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Tongkol")) {
            return tongkolAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Voltorb")) {
            return voltorbAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Wartotle")) {
            return wartotleAnimationSet.getStanding(DIRECTION.DOWN);
        }
        if (engimon.getSpecies().equals("Vulpix")) {
            return vulpixAnimationSet.getStanding(DIRECTION.DOWN);
        }

        return arcanineAnimationSet.getStanding(DIRECTION.DOWN);
    }

    public TextureRegion getSprite(SkillItem skillitem){
        TextureAtlas atlas = assetManager.get("pic/packed/skillItemLogo.atlas", TextureAtlas.class);
        return atlas.findRegion(skillitem.getName());
    }

    public TextureRegion getSprite(Skill skill){
        TextureAtlas atlas = assetManager.get("pic/packed/skillLogo.atlas", TextureAtlas.class);
        return atlas.findRegion(skill.getSkillName());
    }

    public boolean isCompatible(List<IElements> elementsList, CellType biome){
        for(IElements element : elementsList){
            if(mapElementToBiome.get(element).equals(biome)){
                return true;
            }
        }
        return false;
    }
}
