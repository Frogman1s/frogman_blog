package com.frogman.boot.utils;

import cn.hutool.core.convert.Convert;
import com.frogman.boot.domain.entity.Menu;
import com.frogman.boot.domain.vo.MenuVo;
import java.util.*;
import java.util.stream.Collectors;

public class RouterMenuTreeUtil {
    //不支持三级树形结构！
    public static List<MenuVo> routerMenuTreeHelper(List<Menu> menus){
        Map<Long, MenuVo> map = new HashMap<>();
        for (Menu menu : menus) {
            MenuVo menuVo = Convert.convert(MenuVo.class, menu);
            menuVo.setChildren(new ArrayList<MenuVo>());
            map.put(menu.getId(),menuVo);
        }
        for (Menu menu : menus) {
            Long parentId = menu.getParentId();
            if(parentId!=0L){
                map.get(parentId).getChildren().add(map.get(menu.getId()));
                map.remove(menu.getId());
            }
        }
        List<MenuVo> menuVos = map.values().stream().collect(Collectors.toList());
        return menuVos;

    }
}
