package xyz.chaisong.newsexp.base.lifecycle;

public interface LifeCycleComponent {
    /**
     * The UI becomes visible from partially or totally invisible.
     * like {@link android.app.Activity#onResume}
     */
    void onBecomeVisible();

    /**
     * The UI becomes totally invisible.
     * like {@link android.app.Activity#onStop}
     */
    void onBecomeInvisible();

    /**
     * like {@link android.app.Activity#onDestroy}
     */
    void onDestroy();
}
