package jp.itnav.realmsample.util.manager;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by derushio on 2017/05/13.
 */

public class RealmManager {
    public static RealmManager instance;

    private Context context;

    private RealmManager(Context context) {
        Realm.init(context);
    }

    public static RealmManager getInstance(Context context) {
        if (instance == null) {
            instance = new RealmManager(context);
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
        }
    }

    /**
     * 非同期で実行して下さい
     * @param objClass
     */
    public synchronized RealmObject loadRObject(Class objClass) {
        RealmObject object;

        Realm realm = Realm.getDefaultInstance();
        try {
            // ここのfindAllを変更することによりクエリが可能
            object = (RealmObject) realm.where(objClass).findAll().get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            object = null;
        }

        return object;
    }

}
