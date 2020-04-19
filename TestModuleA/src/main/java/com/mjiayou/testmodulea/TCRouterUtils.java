package com.mjiayou.testmodulea;

import com.mjiayou.trerouter.IRouter;
import com.mjiayou.trerouter.TCRouter;

public class TCRouterUtils implements IRouter {

    @Override
    public void putActivity() {
        TCRouter.get().addActivity("TestModuleAActivity", TestModuleAActivity.class);
    }
}
