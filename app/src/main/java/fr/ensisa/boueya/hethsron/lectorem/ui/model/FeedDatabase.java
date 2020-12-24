/**
 * Copyright © 2020  Hethsron Jedaël BOUEYA
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package fr.ensisa.boueya.hethsron.lectorem.ui.model;
/**
 *      @file            FeedDatabase.java
 *      @details         Contains an useful class that serves as main access point for the underlying
 *                       connection to our app's persisted, relational data
 *
 *      @author          Hethsron Jedaël BOUEYA (hethsron-jedael.boueya@uha.fr)
 *
 *      @version         0.0.1
 *      @date            November, 4th 2020
 *
 *      @Copyright       GPLv3+ : GNU GPL version 3 or later
 *                       Licencied Material - Property of Me®
 *                       © 2020 ENSISA (UHA) - All rights reserved.
 */
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import fr.ensisa.boueya.hethsron.lectorem.R;

/**
 * @class           FeedDatabase
 * @brief           Contains the database holder and serves as the main access point
 *                  for the underlying connection to our app's persisted, relational data.
 */
@Database(entities = {Feed.class}, version = 1, exportSchema = false)
public abstract class FeedDatabase extends RoomDatabase {

    private static FeedDatabase instance;                       // A Feed Database instance acquired at runtime

    private static RoomDatabase.Callback feedCallback = new RoomDatabase.Callback() {   // A Feed Database callback
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };

    /**
     * @fn          feedDao
     * @brief       Returns the class that is annotated with @Dao
     *
     * @return      FeedDao
     */
    public abstract FeedDao feedDao();

    /**
     * @fn          getInstance
     * @brief       Returns the instance of Database for the given context
     *
     * @param       context         Application context
     * @return      Synchronized FeedDatabase
     */
    public static synchronized FeedDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FeedDatabase.class, "rss_feeds_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(feedCallback)
                    .build();
        }
        return instance;
    }

    /**
     * @class       PopulateDatabaseAsyncTask
     * @brief       Contains methods to perform insert operation on the database
     */
    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private FeedDao feedDao;            // A FeedDao interface

        private PopulateDatabaseAsyncTask(FeedDatabase feedDatabase) {
            this.feedDao = feedDatabase.feedDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedDao.insert(new Feed("France 24", "france24.fr - L'information internationale en direct", 1, R.drawable.ic_france24, "https://www.france24.com/fr/rss"));
            feedDao.insert(new Feed("Le Figaro", "Le Figaro.fr - A la Une : Retrouvez toute l'actualité en France et à l'international", 2, R.drawable.ic_le_figaro, "https://www.lefigaro.fr/rss/figaro_actualites.xml"));
            feedDao.insert(new Feed("Le Monde", "Le Monde.fr - Actualités et Infos en France et dans le monde", 3, R.drawable.ic_le_monde, "https://www.lemonde.fr/international/rss_full.xml"));
            feedDao.insert(new Feed("Le Parisien", "Le Parisien.fr - Actualités en direct et info en continu", 4, R.drawable.ic_le_parisien, "https://feeds.leparisien.fr/leparisien/rss"));
            feedDao.insert(new Feed("L'Equipe", "L'Equipe.fr - Toute l'actualité du sport", 5, R.drawable.ic_l__equipe_21, "https://www6.lequipe.fr/rss/actu_rss.xml"));
            feedDao.insert(new Feed("RMC Sport", "Toute l'actualité sportive en France et à l'international", 6, R.drawable.ic_rmc_sport, "https://rmcsport.bfmtv.com/rss/info/flux-rss/flux-toutes-les-actualites/"));
            feedDao.insert(new Feed("BFM Business", "L'actualité économique et sociale en France et dans le monde", 7, R.drawable.ic_bfm_business, "https://www.bfmtv.com/rss/economie/"));
            return null;
        }
    }
}
