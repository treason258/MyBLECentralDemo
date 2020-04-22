package com.mjiayou.testmodulea;

import com.mjiayou.treannotation.ITCRouter;
import com.mjiayou.treannotation.TCRouter;

public class TCRouterUtils implements ITCRouter {

    @Override
    public void putActivity() {
        TCRouter.get().addActivity("TestModuleAActivity", TestModuleAActivity.class);
    }
}
