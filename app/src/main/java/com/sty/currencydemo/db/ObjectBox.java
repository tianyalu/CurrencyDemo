package com.sty.currencydemo.db;

import android.content.Context;
import android.util.Log;

import com.sty.currencydemo.model.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.android.BuildConfig;
import io.objectbox.exception.FileCorruptException;
import io.objectbox.model.ValidateOnOpenMode;

public class ObjectBox {
    private static final String TAG = ObjectBox.class.getSimpleName();

    private static BoxStore boxStore;

    public static void init(Context context) {
        BoxStoreBuilder storeBuilder = MyObjectBox.builder()
                .validateOnOpen(ValidateOnOpenMode.WithLeaves)  // Additional DB page validation
                .validateOnOpenPageLimit(20)
                .androidContext(context.getApplicationContext());
        try {
            boxStore = storeBuilder.build();
        } catch (FileCorruptException e) { // Demonstrate handling issues caused by devices with a broken file system
            Log.w(TAG, "File corrupt, trying previous data snapshot...", e);
            // Retrying requires ObjectBox 2.7.1+
            storeBuilder.usePreviousCommit();
            boxStore = storeBuilder.build();
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format("Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()));
            // Enable Data Browser on debug builds.
            // https://docs.objectbox.io/data-browser
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
        }
    }

    public static BoxStore get() {
        return boxStore;
    }
}
