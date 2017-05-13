package jp.itnav.realmsample.util.manager;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by derushio on 2017/05/13.
 */

public class RealmManager {
    public static RealmManager instance;

    private Context context;

    private RealmManager() {
        ;
    }

    public static RealmManager getInstance(Context context) {
        if (instance == null) {
            instance = new RealmManager();
        }

        instance.context = context;
        return instance;
    }

    /**
     * 非同期で実行して下さい
     * @param rObject
     */
    public synchronized void saveRObject(final RealmObject rObject) {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(rObject);
                }
            });
        } finally {
            realm.close();
        }
    }

    /**
     * 非同期で実行して下さい
     * @param objClass
     */
    public synchronized RealmObject loadRObject(Class objClass) {
        RealmResults rResults;

        Realm realm = Realm.getDefaultInstance();
        try {
            rResults = realm.where(objClass)
                .findAllSorted("createAt", Sort.ASCENDING);
        } finally {
            realm.close();
        }

        return (RealmObject) rResults.get(0);
    }

}
