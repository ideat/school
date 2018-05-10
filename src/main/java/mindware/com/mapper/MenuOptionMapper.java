package mindware.com.mapper;

import mindware.com.model.MenuOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuOptionMapper {
    void insertMenuOption(MenuOption menuOption);
    void deleteMenuOption(@Param("rolId") Integer rolId);
    List<MenuOption> findMenuOptionByRolId(@Param("rolId") Integer rolId);
}
