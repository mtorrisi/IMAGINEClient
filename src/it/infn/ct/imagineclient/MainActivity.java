package it.infn.ct.imagineclient;

import it.infn.ct.imagineclient.ui.AbstractNavDrawerActivity;
import it.infn.ct.imagineclient.ui.NavDrawerActivityConfiguration;
import it.infn.ct.imagineclient.ui.NavDrawerAdapter;
import it.infn.ct.imagineclient.ui.NavDrawerItem;
import it.infn.ct.imagineclient.ui.NavMenuItem;
import it.infn.ct.imagineclient.ui.NavMenuSection;
import android.os.Bundle;

public class MainActivity extends AbstractNavDrawerActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new JobsStatusFragment())
					.commit();
		}
	}

	@Override
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {

		NavDrawerItem[] menu = new NavDrawerItem[] {
				NavMenuSection.create(100, "My WorkSpace"),
				NavMenuItem.create(101, "Jobs", "my_jobs", true, this),
				NavMenuItem.create(102, "Jobs Map", "job_map", true, this),
				NavMenuItem.create(103, "Data", "data", true, this),
				NavMenuItem.create(104, "Help", "help", true, this),
				NavMenuSection.create(200, "General"),
				NavMenuItem.create(202, "Rate this app", "navdrawer_rating",
						false, this),
				NavMenuItem.create(203, "Eula", "navdrawer_eula", false, this),
				NavMenuItem.create(204, "Quit", "quit", false, this) };

		NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
		navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
		navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
		navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
		navDrawerActivityConfiguration.setNavItems(menu);
		navDrawerActivityConfiguration
				.setDrawerShadow(R.drawable.drawer_shadow);
		navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
		navDrawerActivityConfiguration
				.setDrawerCloseDesc(R.string.drawer_close);
		navDrawerActivityConfiguration.setBaseAdapter(new NavDrawerAdapter(
				this, R.layout.navdrawer_item, menu));
		return navDrawerActivityConfiguration;
	}

	@Override
	protected void onNavItemSelected(int id) {
		switch ((int) id) {
		case 101:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new JobsStatusFragment())
					.commit();
			break;
		case 102:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new JobsMapFragment())
					.commit();
			break;
		}

	}
}
