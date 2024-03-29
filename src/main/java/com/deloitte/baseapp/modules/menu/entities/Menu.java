package com.deloitte.baseapp.modules.menu.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Menu implements Serializable, GenericEntity<Menu> {

    @Id
    @CsvBindByName
    private Long id;

    @NotBlank
    @CsvBindByName
    private String name;

    @NotBlank
    @Column(unique = true)
    @CsvBindByName
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Transient
    @CsvBindByName(column = "parent_id")
    private Long parentId;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Menu> children;

    /**
     * What will happen when a menu is clicked
     */
    @Column(length = 50)
    @CsvBindByName(column = "click_event")
    @Enumerated(EnumType.STRING)
    private EMenuClickEvent clickEvent;

    //TODO: decide on want to send processed href or unprocessed pathname --> store either one dont store both
    // if href is processed backend --> (kinda unneccessary?) --> sending path name is more logical since we are already looping the menus.
    @CsvBindByName
    private String pathname;

    // TODO: TO BE REMOVED, THIS FIELD IS REDUNDANT, pathname alone is enough.
    private String href;

    @CsvBindByName
    private Integer priority;

    @CsvBindByName(column = "is_active")
    private Boolean isActive;

    @JsonIgnore
    public Set<Menu> getChildren() {
        return children;
    }

    @Override
    public void update(Menu source) {
        this.name = source.getName();
        this.code = source.getCode();

        // if "source" don't have a parent --> this parent is null
        // OR
        // if this id not null, meaning menu exists AND this id (this menu)
        // is equal to source parent id (parent menu)), meaning current object is a parent of itself which is also invalid.
        if (null == source.getParent()
                || (null != this.getId() && this.getId().equals(source.getParent().getId()))) {
            this.parent = null;
        } else {
            this.parent = source.getParent(); // update
        }
        this.children = source.getChildren();
        this.href = source.getHref();
        this.priority = source.getPriority();
        this.clickEvent = source.getClickEvent();
        this.isActive = source.getIsActive();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Menu createNewInstance() {
        Menu newInstance = new Menu();
        newInstance.update(this);

        return newInstance;
    }
}
