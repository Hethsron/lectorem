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
 *      @file            FeedDao.java
 *      @details         Contains an useful interface used for accessing the database of RSS feeds
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
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * @class           FeedDao
 * @brief           Contains the methods used for accessing the database.
 */
@Dao
public interface FeedDao {

    /**
     * @fn          insert
     * @brief       Inserts a new Feed in the table
     *
     * @param       feed        A new Feed to insert
     */
    @Insert
    void insert(Feed feed);

    /**
     * @fn          update
     * @brief       Update an existing Feed contents from the table
     *
     * @param       feed        An existing Feed to update
     */
    @Update
    void update(Feed feed);

    /**
     * @fn          delete
     * @brief       Delete an existing Feed from the table
     *
     * @param       feed        An existing Feed to delete
     */
    @Delete
    void delete(Feed feed);

    /**
     * @fn          deleteAll
     * @brief       Delete all existing feeds from the table
     */
    @Query("DELETE FROM feed_table")
    void deleteAll();

    /**
     * @fn          getAll
     * @brief       Returns all existing feeds from the table
     *
     * @return      LiveData<List<Feed>>
     */
    @Query("SELECT * FROM feed_table ORDER BY feed_priority ASC")
    LiveData<List<Feed>> getAll();

}
