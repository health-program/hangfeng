package com.paladin.hf.mapper.ordinary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.paladin.hf.model.ordinary.Prizepunish;


public interface PrizepunishMapper extends CustomerMapper<Prizepunish>{
    
    List<Map<String, Object>> selectPrizepunishAll(Prizepunish prizepunish);
    
    List<Map<String, Object>> selectPrizepunishAll2(Prizepunish prizepunish);
    
    List<Map<String, Object>> appSelectPrizepunishAll(Prizepunish prizepunish);
    
    Prizepunish prizepId(@Param("id") String id);
}