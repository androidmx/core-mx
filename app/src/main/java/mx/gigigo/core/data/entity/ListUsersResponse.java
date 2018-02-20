package mx.gigigo.core.data.entity;


import java.util.List;

import mx.gigigo.core.data.entity.base.ResponseBase;

/**
 * @author JG - December 13, 2017
 * @version 0.0.1
 * @since 0.0.1
 */
public class ListUsersResponse
        extends ResponseBase<List<UserEntity>> {

    @Override
    public boolean hasData() {
        return null != getData() && !getData().isEmpty();
    }
}
