package com.kevinm416.report.user;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;

public class CreateUserTransaction {

    private final Handle h;

    public CreateUserTransaction(Handle h) {
        this.h = h;
    }

    public long loadOrCreateUser(CreateUser createUser) {
        Long ret = h.inTransaction(new TransactionCallback<Long>() {
            @Override
            public Long inTransaction(Handle conn, TransactionStatus status) throws Exception {
                return loadOrCreateUser(conn, createUser);
            }

        });
        return ret;
    }

    private static long loadOrCreateUser(Handle txn, CreateUser createUser) {
        UserDAO userDao = txn.attach(UserDAO.class);
        User user = userDao.loadUserByEmail(createUser.getEmail());
        if (user != null) {
            return user.getId();
        } else {
            return userDao.createUser(createUser);
        }
    }

}
