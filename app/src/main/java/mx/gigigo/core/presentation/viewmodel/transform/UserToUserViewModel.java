package mx.gigigo.core.presentation.viewmodel.transform;


import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.presentation.viewmodel.UserViewModel;
import mx.gigigo.core.rxmvp.Transform;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserToUserViewModel
        extends Transform<User, UserViewModel> {
    @Override
    public UserViewModel transform(User value) {
        if(null ==  value) return null;

        UserViewModel model = new UserViewModel();
        model.setId(value.getId());
        model.setName(value.getName());
        model.setLastName(value.getLastName());
        model.setAvatar(value.getAvatar());
        model.setUpdateAt(value.getUpdateAt());
        return model;
    }

    @Override
    public User reverseTransform(UserViewModel value) {
        if(value == null) return null;
        User user = new User();
        user.setId(value.getId());
        user.setName(value.getName());
        user.setLastName(value.getLastName());
        user.setAvatar(value.getAvatar());
        return user;
    }
}
