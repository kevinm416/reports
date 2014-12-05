package com.kevinm416.report.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.kevinm416.report.auth.ReportApplicationRealm;
import com.kevinm416.report.auth.UserAuthInfo;
import com.kevinm416.report.auth.UserWithAuthInfo;

public class UserWithAuthInfoDBMapper implements ResultSetMapper<UserWithAuthInfo>{

    @Override
    public UserWithAuthInfo map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        User user = new UserDBMapper().map(index, r, ctx);
        return new UserWithAuthInfo(
                user,
                UserAuthInfo.create(
                        user,
                        ReportApplicationRealm.REALM_NAME,
                        r.getString("pw_hash"),
                        r.getString("salt")));
    }

}
