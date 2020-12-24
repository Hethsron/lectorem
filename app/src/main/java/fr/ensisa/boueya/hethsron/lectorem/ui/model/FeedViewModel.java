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
 *      @file            FeedViewModel.java
 *      @details         Contains an useful class designed to store and manage UI-related data in a
 *                       lifecycle conscious way
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
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

/**
 * @class           FeedViewModel
 * @brief           This class is designed to store and manage UI-related data in a lifecycle conscious way
 */
public class FeedViewModel extends AndroidViewModel {

    private FeedRepository repository;                          // A repository for managing multiple data sources
    private LiveData<List<Feed>> feeds;                         // A Live Data of feeds

    /**
     * @fn          FeedViewModel
     * @brief       Parameterized constructor of class
     *
     * @param       application         Global application state
     */
    public FeedViewModel(@NonNull Application application) {
        super(application);
        this.repository = new FeedRepository(application);
        this.feeds = repository.getAll();
    }

    /**
     * @fn          insert
     * @brief       Inserts a new Feed in the table
     *
     * @param       feed        A new Feed to insert
     */
    public void insert(Feed feed) {
        repository.insert(feed);
    }

    /**
     * @fn          update
     * @brief       Update an existing Feed contents from the table
     *
     * @param       feed        An existing Feed to update
     */
    public void update(Feed feed) {
        repository.update(feed);
    }

    /**
     * @fn          delete
     * @brief       Delete an existing Feed from the table
     *
     * @param       feed        An existing Feed to delete
     */
    public void delete(Feed feed) {
        repository.delete(feed);
    }

    /**
     * @fn          deleteAll
     * @brief       Delete all existing feeds from the table
     */
    public void deleteAll() {
        repository.deleteAll();
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

}
