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
 *      @file            Feed.java
 *      @details         Contains an useful class that represents a table of RSS feed within the database
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
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @class           Feed
 * @brief           Represents a table within the database
 */
@Entity(tableName = "feed_table")
public class Feed {

    @PrimaryKey(autoGenerate = true)
    private int id;                                                 // A private key of the feed

    @ColumnInfo(name = "feed_title")
    private String title;                                           // A RSS feed title

    @ColumnInfo(name = "feed_description")
    private String description;                                     // A RSS feed small description

    @ColumnInfo(name = "feed_priority")
    private int priority;                                           // A RSS feed priority

    @ColumnInfo(name = "feed_image")
    private Integer image;                                           // A RSS feed resource image

    @ColumnInfo(name = "feed_url")
    private String url;                                             // A RSS feed Uniform Resource Locator

    /**
     * @fn          Feed
     * @brief       Parameterized constructor of class used to initialize the entity
     *
     * @param       title               A RSS feed title
     * @param       description         A RSS feed small description
     * @param       priority            A RSS feed priority
     * @param       image               A RSS feed resource image
     * @param       url                 A RSS feed Uniform Resource Locator
     */
    public Feed(String title, String description, int priority, Integer image, String url) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.image = image;
        this.url = url;
    }

    /**
     * @fn          getId
     * @brief       Returns from the table the unique private key of the RSS feed
     *
     * @return      int
     */
    public int getId() {
        return id;
    }

    /**
     * @fn          setId
     * @brief       Set into the table the private key of the RSS feed
     *
     * @param       id          int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @fn          getTitle
     * @brief       Returns from the table the title of the RSS feed
     *
     * @return      String
     */
    public String getTitle() {
        return title;
    }

    /**
     * @fn          getDescription
     * @brief       Returns from the table the small description of the RSS feed
     *
     * @return      String
     */
    public String getDescription() {
        return description;
    }

    /**
     * @fn          getPriority
     * @brief       Returns from the table the priority of the RSS feed
     *
     * @return      int
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @fn          getImage
     * @brief       Returns from the table the image resource of the RSS feed
     *
     * @return      Integer
     */
    public Integer getImage() {
        return image;
    }

    /**
     * @fn          getUrl
     * @brief       Returns from the table the unique uniform resource locator of the RSS feed
     *
     * @return      URL
     */
    public String getUrl() {
        return url;
    }

}
