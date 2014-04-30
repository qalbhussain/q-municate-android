package com.quickblox.qmunicate.qb.commands;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.quickblox.module.users.model.QBUser;
import com.quickblox.qmunicate.core.command.ServiceCommand;
import com.quickblox.qmunicate.qb.helpers.QBAuthHelper;
import com.quickblox.qmunicate.qb.helpers.QBChatHelper;
import com.quickblox.qmunicate.service.QBService;
import com.quickblox.qmunicate.service.QBServiceConsts;

public class QBLoginCommand extends ServiceCommand {

    private static final String TAG = QBLoginCommand.class.getSimpleName();

    private final QBAuthHelper qbAuthHelper;
    private final QBChatHelper qbChatHelper;

    public QBLoginCommand(Context context, QBAuthHelper qbAuthHelper, QBChatHelper qbChatHelper,
            String successAction, String failAction) {
        super(context, successAction, failAction);
        this.qbAuthHelper = qbAuthHelper;
        this.qbChatHelper = qbChatHelper;
    }

    public static void start(Context context, QBUser user) {
        Intent intent = new Intent(QBServiceConsts.LOGIN_ACTION, null, context, QBService.class);
        intent.putExtra(QBServiceConsts.EXTRA_USER, user);
        context.startService(intent);
    }

    @Override
    public Bundle perform(Bundle extras) throws Exception {
        QBUser user = (QBUser) extras.getSerializable(QBServiceConsts.EXTRA_USER);

        user = qbAuthHelper.login(user);
//        qbChatHelper.init(context);
        Bundle result = new Bundle();
        result.putSerializable(QBServiceConsts.EXTRA_USER, user);

        return result;
    }
}
