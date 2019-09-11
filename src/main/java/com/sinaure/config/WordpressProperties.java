package com.sinaure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sinaure.config.model.Menu;
import com.sinaure.config.model.Server;
import com.sinaure.config.model.Theme;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("wordpress")
public class WordpressProperties {

    private List<Menu> menus = new ArrayList<>();
    private Theme themes;
    private List<Server> servers = new ArrayList<>();

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Theme getThemes() {
        return themes;
    }

    public void setThemes(Theme themes) {
        this.themes = themes;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "WordpressProperties{" +
                "menus=" + menus +
                ", themes=" + themes +
                ", servers=" + servers +
                '}';
    }
}
