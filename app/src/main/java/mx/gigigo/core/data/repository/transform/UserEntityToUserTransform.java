package mx.gigigo.core.data.repository.transform;

import mx.gigigo.core.data.entity.UserEntity;
import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.rxextensions.Transform;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserEntityToUserTransform
        extends Transform<UserEntity, User> {

    @Override
    public User transform(UserEntity value) {
        if(null ==  value) return null;

        User model = new User();
        model.setId(value.getId());
        model.setName(value.getFirstName());
        model.setLastName(value.getLastName());
        model.setAvatar(value.getAvatar());
        model.setUpdateAt(value.getUpdateAt());
        return model;
    }

    @Override
    public UserEntity reverseTransform(User value) {
        if(value == null) return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(value.getId());
        userEntity.setFirstName(value.getName());
        userEntity.setLastName(value.getLastName());
        userEntity.setAvatar(value.getAvatar());
        userEntity.setEmail(value.getEmail());
        return userEntity;
    }
}
