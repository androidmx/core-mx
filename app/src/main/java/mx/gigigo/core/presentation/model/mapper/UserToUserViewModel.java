package mx.gigigo.core.presentation.model.mapper;


import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.presentation.model.UserViewModel;
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
        model.setAvatar(value.getAvatar());

        return model;
    }

    @Override
    public User reverseTransform(UserViewModel value) {
        throw new UnsupportedOperationException();
    }
}
