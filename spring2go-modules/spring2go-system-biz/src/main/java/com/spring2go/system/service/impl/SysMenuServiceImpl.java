package com.spring2go.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring2go.common.core.constant.CommonConstants;
import com.spring2go.common.core.domain.TreeNode;
import com.spring2go.common.core.util.TreeUtils;
import com.spring2go.common.security.util.SecurityUtils;
import com.spring2go.system.mapper.SysMenuMapper;
import com.spring2go.system.vo.MenuTree;
import com.spring2go.system.entity.SysMenu;
import com.spring2go.system.service.SysMenuService;
import com.spring2go.system.vo.MetaVo;
import com.spring2go.system.vo.RouterVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @author: xiaobin
 * @date: 2021-04-08 11:01
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    /**
     * 查询菜单数据-下拉树结构
     *
     * @param parentId 父节点
     * @return 下拉树结构列表
     */
    @Override
    public List<MenuTree> selectMenuTree(Integer parentId) {
        Integer parent = parentId == null ? 0 : parentId;
        List<SysMenu> list = baseMapper.selectList(
                Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parent).orderByAsc(SysMenu::getOrderNum));

        List<MenuTree> treeList = list.stream().filter(item -> !item.getMenuId().equals(item.getParentId()))
                .sorted(Comparator.comparingInt(SysMenu::getOrderNum)).map(item -> {
                    MenuTree node = new MenuTree();
                    BeanUtils.copyProperties(item, node);
                    return node;
                }).collect(Collectors.toList());
        return TreeUtils.build(treeList);
    }

    @Override
    public List<MenuTree> selectMenuTreeByRoleNames(Set<String> roleNames) {

        List<SysMenu> all = new ArrayList<>();
        roleNames.stream().forEach(roleName -> {
            if (SecurityUtils.isAdmin(roleName)) {
                LambdaQueryWrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(SysMenu::getDelFlag, "0");
                all.addAll(sysMenuMapper.selectList(queryWrapper));
            } else {
                all.addAll(sysMenuMapper.listMenusByRoleName(roleName));
            }
        });

        List<MenuTree> treeList = all.stream().filter(item -> !item.getMenuId().equals(item.getParentId()))
                .sorted(Comparator.comparingInt(SysMenu::getOrderNum)).map(item -> {
                    MenuTree node = new MenuTree();
                    BeanUtils.copyProperties(item, node);
                    node.setId(item.getMenuId());
                    return node;
                }).collect(Collectors.toList());

        return TreeUtils.build(treeList);
    }

    @Override
    public List<RouterVo> buildMenus(List<MenuTree> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (MenuTree menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));

            List<TreeNode> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && CommonConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");

                List<MenuTree> menutrees = new ArrayList<MenuTree>();
                for (TreeNode item : cMenus) {
                    MenuTree iMenu = (MenuTree) item;
                    menutrees.add(iMenu);
                }
                router.setChildren(buildMenus(menutrees));

            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(CommonConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(MenuTree menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(MenuTree menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && CommonConstants.TYPE_DIR.equals(menu.getMenuType())
                && CommonConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(MenuTree menu) {
        String component = CommonConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = CommonConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = CommonConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{CommonConstants.HTTP, CommonConstants.HTTPS},
                new String[]{"", ""});
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(MenuTree menu) {
        return menu.getParentId().intValue() == 0 && CommonConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(CommonConstants.NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(MenuTree menu) {
        return menu.getIsFrame().equals(CommonConstants.NO_FRAME) && ishttp(menu.getPath());
    }

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        if (StringUtils.isEmpty(link)) {
            return false;
        }

        if (link.startsWith(CommonConstants.HTTP) || link.startsWith(CommonConstants.HTTPS)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(MenuTree menu) {
        return menu.getParentId().intValue() != 0 && CommonConstants.TYPE_DIR.equals(menu.getMenuType());
    }
}
