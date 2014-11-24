package com.kevinm416.report.user;

import java.util.UUID;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface UserSessionDAO {

    @SqlUpdate(
            " INSERT INTO user_sessions (id, user_id) " +
            " VALUES (:id, :userId) "
    )
    void createSession(@Bind("id") UUID id, @Bind("userId") long userId);

    @SqlQuery(
            " SELECT (id, user_id) " +
            " FROM user_sessions " +
            " WHERE id = :id "
    )
    public void loadSession(@BindBean UUID id);

}
