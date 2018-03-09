package com.mjiayou.trecore.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mjiayou.trecore.R;
import com.mjiayou.trecorelib.base.TCActivity;
import com.mjiayou.trecorelib.base.TCApp;
import com.mjiayou.trecorelib.base.TCMenuActivity;
import com.mjiayou.trecorelib.bean.TCResponse;
import com.mjiayou.trecorelib.bean.TCSinaStatusesResponse;
import com.mjiayou.trecorelib.bean.entity.TCMenu;
import com.mjiayou.trecorelib.dialog.DialogHelper;
import com.mjiayou.trecorelib.dialog.TCAlertDialog;
import com.mjiayou.trecorelib.helper.GsonHelper;
import com.mjiayou.trecorelib.net.RequestAdapter;
import com.mjiayou.trecorelib.util.AppUtils;
import com.mjiayou.trecorelib.util.CustomToastUtils;
import com.mjiayou.trecorelib.util.HandlerUtil;
import com.mjiayou.trecorelib.util.HelloUtils;
import com.mjiayou.trecorelib.util.LogUtils;
import com.mjiayou.trecorelib.util.MenuUtil;
import com.mjiayou.trecorelib.util.PageUtil;
import com.mjiayou.trecorelib.util.SharedUtil;
import com.mjiayou.trecorelib.util.ToastUtils;
import com.mjiayou.trecorelib.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by treason on 2017/9/25.
 */

public class TestActivity extends TCActivity {

    @InjectView(R.id.layout_menu_container)
    LinearLayout mLayoutMenuContainer;
    @InjectView(R.id.tv_info)
    TextView mTvInfo;

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tc_activity_menu;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        // mTitleBar
        getTitleBar().setTitle(TAG);
        getTitleBar().addLeftImageView(R.mipmap.tc_launcher, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("addLeftImageView");
            }
        });
        getTitleBar().addRightTextView("Menu", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("addRightTextView");
            }
        });

        // mLayoutMenuContainer
        MenuUtil.setMenus(mContext, mLayoutMenuContainer, getMenus());

        // mTvInfo
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append(HelloUtils.getHI());
        builder.append("\n");
        builder.append(AppUtils.getAppInfoDetail(mContext));
        builder.append("\n");
        MenuUtil.setInfo(mTvInfo, builder.toString());

        // MaterialDrawer
        initMaterialDrawer();
    }

    /**
     * getMenus
     */
    public List<TCMenu> getMenus() {
        List<TCMenu> tcMenus = new ArrayList<>();
        tcMenus.add(new TCMenu("LOG AND TOAST", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show("LOG AND TOAST TEST");
                LogUtils.i("LOG AND TOAST TEST");
            }
        }));
        tcMenus.add(new TCMenu("设置为第一次启动", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.createTCAlertDialog(mContext,
                        "提示", "确认设置为第一次启动？",
                        "确定", "取消", true,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                SharedUtil.get(mContext).setConfigIsFirst(true);
                                ToastUtils.show("设置第一次启动成功");
                            }

                            @Override
                            public void onCancelAction() {
                            }
                        }).show();
            }
        }));
        tcMenus.add(new TCMenu("模拟登录or退出登陆", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.createTCAlertDialog(mContext,
                        "提示", "请选择操作？",
                        "模拟登录", "退出登陆", true,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                UserUtil.doLogin("12345678901234567890123456789012");
                                ToastUtils.show("模拟登录成功");
                            }

                            @Override
                            public void onCancelAction() {
                                UserUtil.doLogout();
                                ToastUtils.show("退出登录成功");
                            }
                        }).show();
            }
        }));
        tcMenus.add(new TCMenu("NullPointExceptionTest", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.createTCAlertDialog(mContext,
                        "提示", "确认抛出NullPointerException？",
                        "确定", "取消", true,
                        new TCAlertDialog.OnTCActionListener() {
                            @Override
                            public void onOkAction() {
                                // 抛出异常
                                throw new NullPointerException("NullPointExceptionTest");
                            }

                            @Override
                            public void onCancelAction() {
                            }
                        }).show();
            }
        }));
        tcMenus.add(new TCMenu("WebViewActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, WebViewActivity.class));
            }
        }));
        tcMenus.add(new TCMenu("CameraActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CameraActivity.class));
            }
        }));
        tcMenus.add(new TCMenu("TCMenuActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TCMenuActivity.open(mContext, "TCMenuActivity", "TCMenuActivity Info Test", MenuUtil.getTCMenus(mContext));
            }
        }));
        tcMenus.add(new TCMenu("Dialog测试", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.showDialogDemo(mContext);
            }
        }));
        tcMenus.add(new TCMenu("StatusViewManager - onLoading", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStatusViewManager().onLoading();
                HandlerUtil.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getStatusViewManager().onFailure("数据异常", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getStatusViewManager().onSuccess();
                            }
                        });
                    }
                }, 3000);
            }
        }));
        tcMenus.add(new TCMenu("Test API", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.traceStart("网络请求开始");
                RequestAdapter requestAdapter = new RequestAdapter(mContext, new RequestAdapter.DataResponse() {
                    @Override
                    public void callback(Message msg) {
                        switch (msg.what) {
                            case RequestAdapter.SINA_STATUSES: {
                                LogUtils.traceStop("网络响应");
                                TCSinaStatusesResponse response = (TCSinaStatusesResponse) msg.obj;
                                if (response != null) {
                                    ToastUtils.show(GsonHelper.get().toJson(response));
                                } else {
                                    ToastUtils.show(TCApp.get().getResources().getString(R.string.tc_error_response_null));
                                }
                                break;
                            }
                        }
                    }

                    @Override
                    public void refreshView(TCResponse response) {
                    }
                });
                requestAdapter.sinaStatuses(String.valueOf(PageUtil.pageReset()));
            }
        }));
        tcMenus.add(new TCMenu("CustomToast 显示5秒 by Toast", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToastUtils.showToast("显示5秒 by Toast", 5000);
            }
        }));
        tcMenus.add(new TCMenu("CustomToast 显示5秒 by WindowManager", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToastUtils.showWindowToast("显示5秒 by WindowManager", 5000);
            }
        }));
        tcMenus.add(new TCMenu("ServiceActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ServiceActivity.class));
            }
        }));
        tcMenus.add(new TCMenu("FloatTagActivity", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, FloatTagActivity.class));
            }
        }));
        return tcMenus;
    }

    /**
     * MaterialDrawer
     */
    private void initMaterialDrawer() {
        // accountHeader
        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.tc_shape_rect_gradient_theme)
                .addProfiles(
                        new ProfileDrawerItem().withName("NameA").withEmail("EmailA").withIdentifier(0).withIcon(R.mipmap.tc_launcher),
                        new ProfileDrawerItem().withName("NameB").withEmail("EmailB").withIdentifier(1).withIcon(R.mipmap.tc_launcher),
                        new ProfileDrawerItem().withName("NameC").withEmail("EmailC").withIdentifier(2).withIcon(R.mipmap.tc_launcher))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        ToastUtils.show("onProfileChanged | profile.getIdentifier() -> " + profile.getIdentifier() + " | profile.getName() -> " + profile.getName());
                        return true;
                    }
                })
                .build();
        accountHeader.setActiveProfile(0);

        // drawerBuilder
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withSliderBackgroundColor(getResources().getColor(R.color.tc_white_alpha_light))
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new SectionDrawerItem().withName("SectionDrawerA"),
                        new PrimaryDrawerItem().withName("PrimaryDrawer000").withIcon(R.mipmap.tc_launcher).withIdentifier(0).withDescription("description000"),
                        new SectionDrawerItem().withName("SectionDrawerB"),
                        new PrimaryDrawerItem().withName("PrimaryDrawer111").withIcon(R.mipmap.tc_launcher).withIdentifier(1),
                        new SectionDrawerItem().withName("SectionDrawerC"),
                        new PrimaryDrawerItem().withName("PrimaryDrawer222").withIcon(R.mipmap.tc_launcher),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("PrimaryDrawer333"),
                        new PrimaryDrawerItem().withName("PrimaryDrawer444")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        ToastUtils.show("onItemClick | drawerItem.getIdentifier() -> " + drawerItem.getIdentifier());
                        return true;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        ToastUtils.show("onItemLongClick | drawerItem.getIdentifier() -> " + drawerItem.getIdentifier());
                        return true;
                    }
                })
                .build();
        drawer.setSelection(0);
    }

    // ******************************** project ********************************
}