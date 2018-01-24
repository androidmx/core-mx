package mx.gigigo.core.presentation.viewmodel;

import java.io.Serializable;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserViewModel implements Serializable{
    private Integer id;
    private String name;
    private String lastName;
    private String avatar;
    private String updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean hasAvatar() {
        return null != avatar && !avatar.isEmpty();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
