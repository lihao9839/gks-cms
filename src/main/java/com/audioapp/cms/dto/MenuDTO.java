package com.audioapp.cms.dto;

import java.util.List;

import lombok.Data;

@Data
public class MenuDTO {
    private Long id;

    private String menuCode;

    private String menuName;

    private String level;

    private String parentCode;

    private String icon;

    private String menuUrl;
    
    private List<MenuDTO> childMenuList;
    
    public List<MenuDTO> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<MenuDTO> childMenuList) {
		this.childMenuList = childMenuList;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }
}