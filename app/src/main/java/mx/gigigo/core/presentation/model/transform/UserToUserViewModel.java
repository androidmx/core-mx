package mx.gigigo.core.presentation.model.transform;


import mx.gigigo.core.domain.model.User;
import mx.gigigo.core.presentation.model.UserModel;
import mx.gigigo.core.rxmvp.Transform;

/**
 * @author JG - January 04, 2018
 * @version 0.0.1
 * @since 0.0.1
 */
public class UserToUserViewModel
        extends Transform<User, UserModel> {
    @Override
    public UserModel transform(User value) {
        if(null ==  value) return null;

        UserModel model = new UserModel();
        model.setId(value.getId());
        model.setName(value.getName());
        model.setAvatar(value.getAvatar());

        return model;
    }

    @Override
    public User reverseTransform(UserModel value) {
        throw new UnsupportedOperationException();
    }
}
