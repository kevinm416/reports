package com.kevinm416.report.user;

import java.util.UUID;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserSessionDBMapper.class)
public interface UserSessionDAO {

    @SqlUpdate(
            " INSERT INTO user_sessions (id, user_id) " +
            " VALUES (:id, :userId) "
    )
    void createSession(@Bind("id") UUID id, @Bind("userId") long userId);

    @SqlQuery(
            " SELECT id, user_id " +
            " FROM user_sessions " +
            " WHERE id = :id "
    )
    UserSession loadSession(@Bind("id") UUID id);

    @SqlQuery(
            " SELECT COUNT(*) " +
            " FROM user_sessions " +
            " WHERE id = :id "
    )
    boolean sessionExists(@Bind("id") UUID id);

}
