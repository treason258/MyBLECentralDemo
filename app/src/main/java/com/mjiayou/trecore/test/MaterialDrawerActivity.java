package com.mjiayou.trecore.test;

import android.os.Bundle;
import android.view.View;

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
import com.mjiayou.trecorelib.util.ToastUtils;

/**
 * MaterialDrawerActivity
 */
public class MaterialDrawerActivity extends TCActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_material_drawer;
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {

        getTitleBar().setTitle(TAG);

        // MaterialDrawer
        initMaterialDrawer();
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
}
