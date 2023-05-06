package com.chat.demo;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class ChatGptDemoApplication {

    /**
     * // 获取：当前账号所拥有的权限集合
     * StpUtil.getPermissionList();
     * <p>
     * // 判断：当前账号是否含有指定权限, 返回 true 或 false
     * StpUtil.hasPermission("user.add");
     * <p>
     * // 校验：当前账号是否含有指定权限, 如果验证未通过，则抛出异常: NotPermissionException
     * StpUtil.checkPermission("user.add");
     * <p>
     * // 校验：当前账号是否含有指定权限 [指定多个，必须全部验证通过]
     * StpUtil.checkPermissionAnd("user.add", "user.delete", "user.get");
     * <p>
     * // 校验：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]
     * StpUtil.checkPermissionOr("user.add", "user.delete", "user.get");
     * <p>
     * // 获取：当前账号所拥有的角色集合
     * StpUtil.getRoleList();
     * <p>
     * // 判断：当前账号是否拥有指定角色, 返回 true 或 false
     * StpUtil.hasRole("super-admin");
     * <p>
     * // 校验：当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException
     * StpUtil.checkRole("super-admin");
     * <p>
     * // 校验：当前账号是否含有指定角色标识 [指定多个，必须全部验证通过]
     * StpUtil.checkRoleAnd("super-admin", "shop-admin");
     * <p>
     * // 校验：当前账号是否含有指定角色标识 [指定多个，只要其一验证通过即可]
     * StpUtil.checkRoleOr("super-admin", "shop-admin");
     * // 当拥有 art.* 权限时
     * StpUtil.hasPermission("art.add");        // true
     * StpUtil.hasPermission("art.update");     // true
     * StpUtil.hasPermission("goods.add");      // false
     * <p>
     * // 当拥有 *.delete 权限时
     * StpUtil.hasPermission("art.delete");      // true
     * StpUtil.hasPermission("user.delete");     // true
     * StpUtil.hasPermission("user.update");     // false
     * <p>
     * // 当拥有 *.js 权限时
     * StpUtil.hasPermission("index.js");        // true
     * StpUtil.hasPermission("index.css");       // false
     * StpUtil.hasPermission("index.html");      // false
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ChatGptDemoApplication.class, args);
        System.out.println("\n启动成功：Sa-Token配置如下：" + SaManager.getConfig());

    }

    @RequestMapping("/")
    public String index() {
        return "redirect:index.html";
    }
}
