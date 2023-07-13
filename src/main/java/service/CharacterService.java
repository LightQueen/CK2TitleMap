package service;

import bo.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterService {

    /**
     187002 K_moravia

     -187003 d_nyitra c_nitra b_nyitra
     --273005 c_trencin
     --2601781 b_stbenedek

     -187004 d_moravia c_gemer

     */

    public void refreshTitles(Character character){
        List<String> titles = new ArrayList<>();
        if (character.getD_d() == null) {
            titles = (List<String>) JDBCUtil.query("select name from title where holder=" + character.getCode());
        } else {
            titles = (List<String>) JDBCUtil.query("select title.name from title join oh on oh.title = title.name where oh.code=" + character.getCode());
        }
        character.setTitles(titles);
    }

}
