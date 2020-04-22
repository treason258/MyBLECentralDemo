package com.mjiayou.testmodulea;

import com.mjiayou.treannotation.IRouter;
import com.mjiayou.treannotation.TCRouter;

public class TCRouterUtils implements IRouter {

    @Override
    public void putActivity() {
        TCRouter.get().addActivity("TestModuleAActivity", TestModuleAActivity.class);
    }
}
