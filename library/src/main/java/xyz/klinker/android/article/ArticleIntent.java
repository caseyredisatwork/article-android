/*
 * Copyright (C) 2017 Jake Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.android.article;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;

import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;

/**
 * Class holding the {@link Intent} and start bundle for an Article Activity.
 *
 * <p>
 * <strong>Note:</strong> The constants below are public for the browser implementation's benefit.
 * You are strongly encouraged to use {@link ArticleIntent.Builder}.</p>
 */
public final class ArticleIntent {

    /**
     * Indicates that the user explicitly opted out of Custom Tabs in the calling application.
     * <p>
     * If an application provides a mechanism for users to opt out of Custom Tabs, this extra should
     * be provided with {@link Intent#FLAG_ACTIVITY_NEW_TASK} to ensure the browser does not attempt
     * to trigger any Custom Tab-like experiences as a result of the VIEW intent.
     * <p>
     * If this extra is present with {@link Intent#FLAG_ACTIVITY_NEW_TASK}, all Custom Tabs
     * customizations will be ignored.
     */
    private static final String EXTRA_USER_OPT_OUT_FROM_CUSTOM_TABS =
            "android.support.customtabs.extra.user_opt_out";

    /**
     * Extra used to match the session. This has to be included in the intent to open in
     * a custom tab. This is the same IBinder that gets passed to ICustomTabsService#newSession.
     * Null if there is no need to match any service side sessions with the intent.
     */
    public static final String EXTRA_SESSION = CustomTabsIntent.EXTRA_SESSION;

    /**
     * Extra that changes the background color for the toolbar. colorRes is an int that specifies a
     * {@link Color}, not a resource id.
     */
    public static final String EXTRA_TOOLBAR_COLOR = CustomTabsIntent.EXTRA_TOOLBAR_COLOR;

    /**
     * Extra that changes the accent color for the activity. colorRes is an int that specifies a
     * {@link Color}, not a resource id.
     */
    public static final String EXTRA_ACCENT_COLOR =
            "xyz.klinker.android.article.extra.EXTRA_ACCENT_COLOR";

    /**
     * Extra that changes the text size for the article. The default is 15 sp.
     */
    public static final String EXTRA_TEXT_SIZE =
            "xyz.klinker.android.article.extra.EXTRA_TEXT_SIZE";

    /**
     * String extra that is used for the API token when making requests to the server
     */
    public static final String EXTRA_API_TOKEN =
            "xyz.klinker.android.article.extra.EXTRA_API_TOKEN";

    /**
     * String extra that defines the service to run when favoriting an article. If not defined, no
     * favorite icon will be shown.
     */
    public static final String EXTRA_FAVORITE_SERVICE =
            "xyz.klinker.android.article.extra.EXTRA_FAVORITE_SERVICE";

    /**
     * Boolean extra that enables the url bar to hide as the user scrolls down the page
     */
    public static final String EXTRA_ENABLE_URLBAR_HIDING =
            CustomTabsIntent.EXTRA_ENABLE_URLBAR_HIDING;

    /**
     * Extra bitmap that specifies the icon of the back button on the toolbar. If the client chooses
     * not to customize it, a default close button will be used.
     */
    public static final String EXTRA_CLOSE_BUTTON_ICON = CustomTabsIntent.EXTRA_CLOSE_BUTTON_ICON;

    /**
     * Extra (int) that specifies state for showing the page title. Default is {@link #NO_TITLE}.
     */
    public static final String EXTRA_TITLE_VISIBILITY_STATE =
            CustomTabsIntent.EXTRA_TITLE_VISIBILITY_STATE;

    /**
     * Don't show any title. Shows only the domain.
     */
    public static final int NO_TITLE = CustomTabsIntent.NO_TITLE;

    /**
     * Shows the page title and the domain.
     */
    public static final int SHOW_PAGE_TITLE = CustomTabsIntent.SHOW_PAGE_TITLE;

    /**
     * Bundle used for adding a custom action button to the custom tab toolbar. The client should
     * provide a description, an icon {@link Bitmap} and a {@link PendingIntent} for the button.
     * All three keys must be present.
     */
    public static final String EXTRA_ACTION_BUTTON_BUNDLE =
            CustomTabsIntent.EXTRA_ACTION_BUTTON_BUNDLE;

    /**
     * List<Bundle> used for adding items to the top and bottom toolbars. The client should
     * provide an ID, a description, an icon {@link Bitmap} for each item. They may also provide a
     * {@link PendingIntent} if the item is a button.
     */
    public static final String EXTRA_TOOLBAR_ITEMS = CustomTabsIntent.EXTRA_TOOLBAR_ITEMS;

    /**
     * Extra that changes the background color for the secondary toolbar. The value should be an
     * int that specifies a {@link Color}, not a resource id.
     */
    public static final String EXTRA_SECONDARY_TOOLBAR_COLOR =
            CustomTabsIntent.EXTRA_SECONDARY_TOOLBAR_COLOR;

    /**
     * Key that specifies the {@link Bitmap} to be used as the image source for the action button.
     *  The icon should't be more than 24dp in height (No padding needed. The button itself will be
     *  48dp in height) and have a width/height ratio of less than 2.
     */
    public static final String KEY_ICON = CustomTabsIntent.KEY_ICON;

    /**
     * Key that specifies the content description for the custom action button.
     */
    public static final String KEY_DESCRIPTION = CustomTabsIntent.KEY_DESCRIPTION;

    /**
     * Key that specifies the PendingIntent to launch when the action button or menu item was
     * clicked. The custom tab will be calling {@link PendingIntent#send()} on clicks after adding
     * the url as data. The client app can call {@link Intent#getDataString()} to get the url.
     */
    public static final String KEY_PENDING_INTENT = CustomTabsIntent.KEY_PENDING_INTENT;

    /**
     * Extra boolean that specifies whether the custom action button should be tinted. Default is
     * false and the action button will not be tinted.
     */
    public static final String EXTRA_TINT_ACTION_BUTTON = CustomTabsIntent.EXTRA_TINT_ACTION_BUTTON;

    /**
     * Use an {@code ArrayList<Bundle>} for specifying menu related params. There should be a
     * separate {@link Bundle} for each custom menu item.
     */
    public static final String EXTRA_MENU_ITEMS = CustomTabsIntent.EXTRA_MENU_ITEMS;

    /**
     * Key for specifying the title of a menu item.
     */
    public static final String KEY_MENU_ITEM_TITLE = CustomTabsIntent.KEY_MENU_ITEM_TITLE;

    /**
     * Bundle constructed out of {@link ActivityOptionsCompat} that will be running when the
     * {@link Activity} that holds the custom tab gets finished. A similar ActivityOptions
     * for creation should be constructed and given to the startActivity() call that
     * launches the custom tab.
     */
    public static final String EXTRA_EXIT_ANIMATION_BUNDLE =
            CustomTabsIntent.EXTRA_EXIT_ANIMATION_BUNDLE;

    /**
     * Boolean extra that specifies whether a default share button will be shown in the menu.
     */
    public static final String EXTRA_DEFAULT_SHARE_MENU_ITEM =
            CustomTabsIntent.EXTRA_DEFAULT_SHARE_MENU_ITEM;

    /**
     * Extra that specifies the {@link RemoteViews} showing on the secondary toolbar. If this extra
     * is set, the other secondary toolbar configurations will be overriden. The height of the
     * {@link RemoteViews} should not exceed 56dp.
     * @see ArticleIntent.Builder#setSecondaryToolbarViews(RemoteViews, int[], PendingIntent).
     */
    public static final String EXTRA_REMOTEVIEWS = CustomTabsIntent.EXTRA_REMOTEVIEWS;

    /**
     * Extra that specifies an array of {@link View} ids. When these {@link View}s are clicked, a
     * {@link PendingIntent} will be sent, carrying the current url of the custom tab as data.
     * <p>
     * Note that Custom Tabs will override the default onClick behavior of the listed {@link View}s.
     * If you do not care about the current url, you can safely ignore this extra and use
     * {@link RemoteViews#setOnClickPendingIntent(int, PendingIntent)} instead.
     * @see ArticleIntent.Builder#setSecondaryToolbarViews(RemoteViews, int[], PendingIntent).
     */
    public static final String EXTRA_REMOTEVIEWS_VIEW_IDS =
            CustomTabsIntent.EXTRA_REMOTEVIEWS_VIEW_IDS;

    /**
     * Extra that specifies the {@link PendingIntent} to be sent when the user clicks on the
     * {@link View}s that is listed by {@link #EXTRA_REMOTEVIEWS_VIEW_IDS}.
     * <p>
     * Note when this {@link PendingIntent} is triggered, it will have the current url as data
     * field, also the id of the clicked {@link View}, specified by
     * {@link #EXTRA_REMOTEVIEWS_CLICKED_ID}.
     * @see ArticleIntent.Builder#setSecondaryToolbarViews(RemoteViews, int[], PendingIntent).
     */
    public static final String EXTRA_REMOTEVIEWS_PENDINGINTENT =
            CustomTabsIntent.EXTRA_REMOTEVIEWS_PENDINGINTENT;

    /**
     * Extra that specifies which {@link View} has been clicked. This extra will be put to the
     * {@link PendingIntent} sent from Custom Tabs when a view in the {@link RemoteViews} is clicked
     * @see ArticleIntent.Builder#setSecondaryToolbarViews(RemoteViews, int[], PendingIntent).
     */
    public static final String EXTRA_REMOTEVIEWS_CLICKED_ID =
            CustomTabsIntent.EXTRA_REMOTEVIEWS_CLICKED_ID;

    /**
     * Extra that specifies whether Instant Apps is enabled.
     */
    public static final String EXTRA_ENABLE_INSTANT_APPS =
            CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS;

    /**
     * Key that specifies the unique ID for an action button. To make a button to show on the
     * toolbar, use {@link #TOOLBAR_ACTION_BUTTON_ID} as its ID.
     */
    public static final String KEY_ID = CustomTabsIntent.KEY_ID;

    /**
     * The ID allocated to the custom action button that is shown on the toolbar.
     */
    public static final int TOOLBAR_ACTION_BUTTON_ID = CustomTabsIntent.TOOLBAR_ACTION_BUTTON_ID;

    /**
     * The maximum allowed number of toolbar items.
     */
    private static final int MAX_TOOLBAR_ITEMS = CustomTabsIntent.getMaxToolbarItems();

    /**
     * Integer for the theme you want to use, choose between {@link #THEME_LIGHT},
     * {@link #THEME_DARK} and {@link #THEME_AUTO}.
     */
    public static final String EXTRA_THEME = "xyz.klinker.android.article.extra.EXTRA_THEME";

    /**
     * Use the light theme.
     */
    public static final int THEME_LIGHT = 1;

    /**
     * Use the dark theme.
     */
    public static final int THEME_DARK = 2;

    /**
     * Use the black theme.
     */
    public static final int THEME_BLACK = 3;

    /**
     * Use an automatic theme, changing between light and dark based on time of day.
     */
    public static final int THEME_AUTO = 4;

    /**
     * Use an system default theme.
     */
    public static final int THEME_SYSTEM_DEFAULT= 5;

    /**
     * An {@link Intent} used to start the Custom Tabs Activity.
     */
    @NonNull
    public final Intent intent;

    /**
     * A {@link Bundle} containing the start animation for the Custom Tabs Activity.
     */
    @Nullable
    public final Bundle startAnimationBundle;

    /**
     * Convenience method to launch a Custom Tabs Activity.
     * @param context The source Context.
     * @param url The URL to load in the Custom Tab.
     */
    public void launchUrl(Context context, Uri url) {
        launchUrl(context, url.toString());
    }

    /**
     * Convenience method to launch a Custom Tabs Activity.
     * @param context The source Context.
     * @param url The URL to load in the Custom Tab.
     */
    public void launchUrl(Context context, String url) {
        intent.setData(Uri.parse(ArticleUtils.removeUrlParameters(url)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextCompat.startActivity(context, intent, startAnimationBundle);
    }

    private ArticleIntent(Intent intent, Bundle startAnimationBundle) {
        this.intent = intent;
        this.startAnimationBundle = startAnimationBundle;
    }

    /**
     * Builder class for {@link ArticleIntent} objects.
     */
    public static final class Builder {
        private final Context context;
        private final Intent mIntent;
        private ArrayList<Bundle> mMenuItems = null;
        private Bundle mStartAnimationBundle = null;
        private ArrayList<Bundle> mActionButtons = null;
        private boolean mInstantAppsEnabled = true;

        public Builder(Context context, String apiToken) {
            this.context = context;
            mIntent = new Intent(context, ArticleActivity.class);
            mIntent.putExtra(EXTRA_API_TOKEN, apiToken);
        }

        /**
         * Sets the toolbar color.
         *
         * @param color {@link Color}
         */
        public ArticleIntent.Builder setToolbarColor(@ColorInt int color) {
            mIntent.putExtra(EXTRA_TOOLBAR_COLOR, color);
            return this;
        }

        /**
         * Sets the accent color.
         *
         * @param color {@link Color}
         */
        public ArticleIntent.Builder setAccentColor(@ColorInt int color) {
            mIntent.putExtra(EXTRA_ACCENT_COLOR, color);
            return this;
        }

        public ArticleIntent.Builder setTextSize(int spSize) {
            mIntent.putExtra(EXTRA_TEXT_SIZE, spSize);
            return this;
        }

        /**
         * Enables the url bar to hide as the user scrolls down on the page.
         */
        public ArticleIntent.Builder enableUrlBarHiding() {
            mIntent.putExtra(EXTRA_ENABLE_URLBAR_HIDING, true);
            return this;
        }

        /**
         * Sets the Close button icon for the custom tab.
         *
         * @param icon The icon {@link Bitmap}
         */
        public ArticleIntent.Builder setCloseButtonIcon(@NonNull Bitmap icon) {
            mIntent.putExtra(EXTRA_CLOSE_BUTTON_ICON, icon);
            return this;
        }

        /**
         * Sets whether the title should be shown in the custom tab.
         *
         * @param showTitle Whether the title should be shown.
         */
        public ArticleIntent.Builder setShowTitle(boolean showTitle) {
            mIntent.putExtra(EXTRA_TITLE_VISIBILITY_STATE,
                    showTitle ? SHOW_PAGE_TITLE : NO_TITLE);
            return this;
        }

        /**
         * Sets the service to run when the favorite button is pressed.
         *
         * @param service The service to run.
         */
        public ArticleIntent.Builder setFavoriteService(Class<? extends Service> service) {
            mIntent.putExtra(EXTRA_FAVORITE_SERVICE, service.getName());
            return this;
        }

        /**
         * Adds a menu item.
         *
         * @param label Menu label.
         * @param pendingIntent Pending intent delivered when the menu item is clicked.
         */
        public ArticleIntent.Builder addMenuItem(@NonNull String label,
                                                 @NonNull PendingIntent pendingIntent) {
            if (mMenuItems == null) mMenuItems = new ArrayList<>();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_MENU_ITEM_TITLE, label);
            bundle.putParcelable(KEY_PENDING_INTENT, pendingIntent);
            mMenuItems.add(bundle);
            return this;
        }

        /**
         * Adds a default share item to the menu.
         */
        public ArticleIntent.Builder addDefaultShareMenuItem() {
            mIntent.putExtra(EXTRA_DEFAULT_SHARE_MENU_ITEM, true);
            return this;
        }

        /**
         * Sets the action button that is displayed in the Toolbar.
         * <p>
         * This is equivalent to calling
         * {@link ArticleIntent.Builder#addToolbarItem(int, Bitmap, String, PendingIntent)}
         * with {@link #TOOLBAR_ACTION_BUTTON_ID} as id.
         *
         * @param icon The icon.
         * @param description The description for the button. To be used for accessibility.
         * @param pendingIntent pending intent delivered when the button is clicked.
         * @param shouldTint Whether the action button should be tinted.
         *
         * @see ArticleIntent.Builder#addToolbarItem(int, Bitmap, String, PendingIntent)
         */
        public ArticleIntent.Builder setActionButton(@NonNull Bitmap icon,
                                                     @NonNull String description,
                                                     @NonNull PendingIntent pendingIntent,
                                                     boolean shouldTint) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_ID, TOOLBAR_ACTION_BUTTON_ID);
            bundle.putParcelable(KEY_ICON, icon);
            bundle.putString(KEY_DESCRIPTION, description);
            bundle.putParcelable(KEY_PENDING_INTENT, pendingIntent);
            mIntent.putExtra(EXTRA_ACTION_BUTTON_BUNDLE, bundle);
            mIntent.putExtra(EXTRA_TINT_ACTION_BUTTON, shouldTint);
            return this;
        }

        /**
         * Adds an action button to the custom tab. Multiple buttons can be added via this method.
         * If the given id equals {@link #TOOLBAR_ACTION_BUTTON_ID}, the button will be placed on
         * the toolbar; if the bitmap is too wide, it will be put to the bottom bar instead. If
         * the id is not {@link #TOOLBAR_ACTION_BUTTON_ID}, it will be directly put on secondary
         * toolbar. The maximum number of allowed toolbar items in a single intent is
         * {@link ArticleIntent#getMaxToolbarItems()}. Throws an
         * {@link IllegalStateException} when that number is exceeded per intent.
         *
         * @param id The unique id of the action button. This should be non-negative.
         * @param icon The icon.
         * @param description The description for the button. To be used for accessibility.
         * @param pendingIntent The pending intent delivered when the button is clicked.
         *
         * @see ArticleIntent#getMaxToolbarItems()
         * @deprecated Use
         * ArticleIntent.Builder#setSecondaryToolbarViews(RemoteViews, int[], PendingIntent).
         */
        @Deprecated
        public ArticleIntent.Builder addToolbarItem(int id,
                                                    @NonNull Bitmap icon,
                                                    @NonNull String description,
                                                    PendingIntent pendingIntent)
                throws IllegalStateException {
            if (mActionButtons == null) {
                mActionButtons = new ArrayList<>();
            }
            if (mActionButtons.size() >= MAX_TOOLBAR_ITEMS) {
                throw new IllegalStateException(
                        "Exceeded maximum toolbar item count of " + MAX_TOOLBAR_ITEMS);
            }
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_ID, id);
            bundle.putParcelable(KEY_ICON, icon);
            bundle.putString(KEY_DESCRIPTION, description);
            bundle.putParcelable(KEY_PENDING_INTENT, pendingIntent);
            mActionButtons.add(bundle);
            return this;
        }

        /**
         * Sets the action button that is displayed in the Toolbar with default tinting behavior.
         *
         * @see {@link ArticleIntent.Builder#setActionButton(
         * Bitmap, String, PendingIntent, boolean)}
         */
        public ArticleIntent.Builder setActionButton(@NonNull Bitmap icon,
                                                     @NonNull String description,
                                                     @NonNull PendingIntent pendingIntent) {
            return setActionButton(icon, description, pendingIntent, false);
        }

        /**
         * Sets the color of the secondary toolbar.
         * @param color The color for the secondary toolbar.
         */
        public ArticleIntent.Builder setSecondaryToolbarColor(@ColorInt int color) {
            mIntent.putExtra(EXTRA_SECONDARY_TOOLBAR_COLOR, color);
            return this;
        }

        /**
         * Sets the remote views displayed in the secondary toolbar in a custom tab.
         *
         * @param remoteViews   The {@link RemoteViews} that will be shown on the secondary toolbar.
         * @param clickableIDs  The IDs of clickable views. The onClick event of these views will be
         *                      handled by custom tabs.
         * @param pendingIntent The {@link PendingIntent} that will be sent when the user clicks on
         *                      one of the {@link View}s in clickableIDs. When the
         *                      {@link PendingIntent} is sent, it will have the current URL as its
         *                      intent data.
         * @see ArticleIntent#EXTRA_REMOTEVIEWS
         * @see ArticleIntent#EXTRA_REMOTEVIEWS_VIEW_IDS
         * @see ArticleIntent#EXTRA_REMOTEVIEWS_PENDINGINTENT
         * @see ArticleIntent#EXTRA_REMOTEVIEWS_CLICKED_ID
         */
        public ArticleIntent.Builder setSecondaryToolbarViews(@NonNull RemoteViews remoteViews,
                                                              @Nullable int[] clickableIDs,
                                                              @Nullable PendingIntent pendingIntent) {
            mIntent.putExtra(EXTRA_REMOTEVIEWS, remoteViews);
            mIntent.putExtra(EXTRA_REMOTEVIEWS_VIEW_IDS, clickableIDs);
            mIntent.putExtra(EXTRA_REMOTEVIEWS_PENDINGINTENT, pendingIntent);
            return this;
        }

        /**
         * Sets whether Instant Apps is enabled for this Custom Tab.

         * @param enabled Whether Instant Apps should be enabled.
         */
        public ArticleIntent.Builder setInstantAppsEnabled(boolean enabled) {
            mInstantAppsEnabled = enabled;
            return this;
        }

        /**
         * Sets the start animations.
         *
         * @param context Application context.
         * @param enterResId Resource ID of the "enter" animation for the browser.
         * @param exitResId Resource ID of the "exit" animation for the application.
         */
        public ArticleIntent.Builder setStartAnimations(
                @NonNull Context context, @AnimRes int enterResId, @AnimRes int exitResId) {
            mStartAnimationBundle = ActivityOptionsCompat.makeCustomAnimation(
                    context, enterResId, exitResId).toBundle();
            return this;
        }

        /**
         * Sets the exit animations.
         *
         * @param context Application context.
         * @param enterResId Resource ID of the "enter" animation for the application.
         * @param exitResId Resource ID of the "exit" animation for the browser.
         */
        public ArticleIntent.Builder setExitAnimations(
                @NonNull Context context, @AnimRes int enterResId, @AnimRes int exitResId) {
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(
                    context, enterResId, exitResId).toBundle();
            mIntent.putExtra(EXTRA_EXIT_ANIMATION_BUNDLE, bundle);
            return this;
        }

        /**
         * Sets the theme to use.
         *
         * @param theme One of {@link #THEME_LIGHT}, {@link #THEME_DARK}, {@link #THEME_BLACK} or
         *              {@link #THEME_AUTO}.
         */
        public ArticleIntent.Builder setTheme(int theme) {
            mIntent.putExtra(EXTRA_THEME, theme);
            return this;
        }

        /**
         * Combines all the options that have been set and returns a new {@link ArticleIntent}
         * object.
         */
        public ArticleIntent build() {
            if (mMenuItems != null) {
                mIntent.putParcelableArrayListExtra(ArticleIntent.EXTRA_MENU_ITEMS, mMenuItems);
            }
            if (mActionButtons != null) {
                mIntent.putParcelableArrayListExtra(EXTRA_TOOLBAR_ITEMS, mActionButtons);
            }
            mIntent.putExtra(EXTRA_ENABLE_INSTANT_APPS, mInstantAppsEnabled);

            new DragDismissIntentBuilder(context)
                    .setShowToolbar(true)
                    .setShouldScrollToolbar(true)
                    .setTheme(convertIntToTheme(mIntent.getIntExtra(EXTRA_THEME, THEME_AUTO)))
                    .setPrimaryColorValue(mIntent.getIntExtra(EXTRA_TOOLBAR_COLOR,
                            context.getResources().getColor(R.color.article_toolbarBackground)))
                    .build(mIntent);

            return new ArticleIntent(mIntent, mStartAnimationBundle);
        }
    }

    /**
     * @return The maximum number of allowed toolbar items for
     * {@link ArticleIntent.Builder#addToolbarItem(int, Bitmap, String, PendingIntent)} and
     * {@link ArticleIntent#EXTRA_TOOLBAR_ITEMS}.
     */
    public static int getMaxToolbarItems() {
        return MAX_TOOLBAR_ITEMS;
    }

    /**
     * Adds the necessary flags and extras to signal any browser supporting custom tabs to use the
     * browser UI at all times and avoid showing custom tab like UI. Calling this with an intent
     * will override any custom tabs related customizations.
     * @param intent The intent to modify for always showing browser UI.
     * @return The same intent with the necessary flags and extras added.
     */
    public static Intent setAlwaysUseBrowserUI(Intent intent) {
        if (intent == null) intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_USER_OPT_OUT_FROM_CUSTOM_TABS, true);
        return intent;
    }

    /**
     * Whether a browser receiving the given intent should always use browser UI and avoid using any
     * custom tabs UI.
     *
     * @param intent The intent to check for the required flags and extras.
     * @return Whether the browser UI should be used exclusively.
     */
    public static boolean shouldAlwaysUseBrowserUI(Intent intent) {
        return intent.getBooleanExtra(EXTRA_USER_OPT_OUT_FROM_CUSTOM_TABS, false)
                && (intent.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK) != 0;
    }

    /**
     * Converts the ArticleIntent integer theme to a DragDismiss theme
     *
     * @param themeInt One of {@link #THEME_LIGHT}, {@link #THEME_DARK}, {@link #THEME_BLACK},
     *                  {@link #THEME_AUTO}, or {@link #THEME_SYSTEM_DEFAULT}.
     * @return the corresponding DragDismiss theme
     */
    static DragDismissIntentBuilder.Theme convertIntToTheme(int themeInt) {
        switch (themeInt) {
            case THEME_LIGHT:
                return DragDismissIntentBuilder.Theme.LIGHT;
            case THEME_DARK:
                return DragDismissIntentBuilder.Theme.DARK;
            case THEME_BLACK:
                return DragDismissIntentBuilder.Theme.BLACK;
            case THEME_SYSTEM_DEFAULT:
                return DragDismissIntentBuilder.Theme.SYSTEM_DEFAULT;
            default:
                return DragDismissIntentBuilder.Theme.DAY_NIGHT;
        }
    }
}
