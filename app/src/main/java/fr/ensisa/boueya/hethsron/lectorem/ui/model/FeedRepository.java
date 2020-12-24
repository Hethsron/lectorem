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
 *      @file            FeedRepository.java
 *      @details         Contains an useful class for managing multiple data sources
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
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * @class           FeedRepository
 * @brief           A class that we create for managing multiple data sources.
 *                  In addition to a Room database, the Repository could manage remote data sources such as a web server.
 */
public class FeedRepository {

    private FeedDao feedDao;                                    // A Feed Dao interface for accessing the database
    private LiveData<List<Feed>> feeds;                         // A Live Data of feeds

    /**
     * @fn          FeedRepository
     * @brief       Parameterized constructor of class
     *
     * @param       application         Global application state
     */
    public FeedRepository(Application application) {
        this.feedDao = FeedDatabase.getInstance(application).feedDao();
        this.feeds = feedDao.getAll();
    }

    /**
     * @fn          insert
     * @brief       Inserts a new Feed in the table
     *
     * @param       feed        A new Feed to insert
     */
    public void insert(Feed feed) {
        new InsertFeedAsyncTask(feedDao).execute(feed);
    }

    /**
     * @fn          update
     * @brief       Update an existing Feed contents from the table
     *
     * @param       feed        An existing Feed to update
     */
    public void update(Feed feed) {
        new UpdateFeedAsyncTask(feedDao).execute(feed);
    }

    /**
     * @fn          delete
     * @brief       Delete an existing Feed from the table
     *
     * @param       feed        An existing Feed to delete
     */
    public void delete(Feed feed) {
        new DeleteFeedAsyncTask(feedDao).execute(feed);
    }

    /**
     * @fn          deleteAll
     * @brief       Delete all existing feeds from the table
     */
    public void deleteAll() {
        new DeleteAllFeedAsyncTask(feedDao).execute();
    }

    /**
     * @fn          getAll
     * @brief       Returns all existing feeds from the table
     *
     * @return      LiveData<List<Feed>>
     */
    public LiveData<List<Feed>> getAll() {
        return feeds;
    }

    /**
     * @class       InsertFeedAsyncTask
     * @brief       Contains methods to perform inserting operation on the database
     */
    private static class InsertFeedAsyncTask extends AsyncTask<Feed, Void, Void> {

        private FeedDao feedDao;            // A FeedDao interface

        /**
         * @fn      InsertFeedAsyncTask
         * @brief   Parameterized constructor of class
         *
         * @param   feedDao         FeedDao interface
         */
        private InsertFeedAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.insert(feeds[0]);
            return null;
        }

    }

    /**
     * @class       UpdateFeedAsyncTask
     * @brief       Contains methods to perform updating operation on the database
     */
    private static class UpdateFeedAsyncTask extends AsyncTask<Feed, Void, Void> {

        private FeedDao feedDao;            // A FeedDao interface

        /**
         * @fn      UpdateFeedAsyncTask
         * @brief   Parameterized constructor of class
         *
         * @param   feedDao         FeedDao interface
         */
        private UpdateFeedAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.update(feeds[0]);
            return null;
        }

    }

    /**
     * @class       DeleteFeedAsyncTask
     * @brief       Contains methods to perform deleting operation on the database
     */
    private static class DeleteFeedAsyncTask extends AsyncTask<Feed, Void, Void> {

        private FeedDao feedDao;            // A FeedDao interface

        /**
         * @fn      DeleteFeedAsyncTask
         * @brief   Parameterized constructor of class
         *
         * @param   feedDao         FeedDao interface
         */
        private DeleteFeedAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.delete(feeds[0]);
            return null;
        }

    }

    /**
     * @class       DeleteAllFeedAsyncTask
     * @brief       Contains methods to perform deleting all operation on the database
     */
    private static class DeleteAllFeedAsyncTask extends AsyncTask<Void, Void, Void> {

        private FeedDao feedDao;            // A FeedDao interface

        /**
         * @fn      DeleteAllFeedAsyncTask
         * @brief   Parameterized constructor of class
         *
         * @param   feedDao         FeedDao interface
         */
        private DeleteAllFeedAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            feedDao.deleteAll();
            return null;
        }

    }

}
