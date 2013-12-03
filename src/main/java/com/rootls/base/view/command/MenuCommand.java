package com.rootls.base.view.command;

import com.rootls.base.view.groups.MenuBatchDeleteGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: luowei
 * Date: 12-5-26
 * Time: 上午2:08
 */
public class MenuCommand extends BaseCommand{

    private Integer[] menuIds;

    @NotNull(groups = {MenuBatchDeleteGroup.class})
    @Size(min = 1, groups = {MenuBatchDeleteGroup.class})
    public Integer[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Integer[] menuIds) {
        this.menuIds = menuIds;
    }
}
