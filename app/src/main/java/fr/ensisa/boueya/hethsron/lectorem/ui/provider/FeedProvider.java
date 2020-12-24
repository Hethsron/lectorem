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
package fr.ensisa.boueya.hethsron.lectorem.ui.provider;
/**
 *      @file            FeedProvider.java
 *      @details
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
import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.item.Item;

public class FeedProvider {

    static final String CHANNEL = "channel";
    static final String DESCRIPTION = "description";
    static final String ITEM = "item";
    static final String LINK = "link";
    static final String PUB_DATE = "pubDate";
    static final String TITLE = "title";

    public static List<Item> parse(String url) {
        // Instantiate an Item list
        List<Item> items = new ArrayList<>();

        // Instantiate a new XML Parser
        XmlPullParser parser = Xml.newPullParser();

        // Define an InputStream
        InputStream stream = null;

        try {
            // Auto-detect the encoding from the stream
            stream = new URL(url).openConnection().getInputStream();

            // Set the input stream the parser is going to process
            parser.setInput(stream, null);

            // Get type of current event
            int eventType = parser.getEventType();

            // Define flag
            boolean done = false;

            // Define an item
            Item item = null;

            // Loop
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        // Get name of current element
                        name = parser.getName();
                        // Finding name match with item
                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("New item", "Create new item");
                            item = new Item();
                        }
                        else if (item != null) {
                            if (name.equalsIgnoreCase(LINK)) {
                                Log.i("Attribute", "setLink");
                                item.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                                Log.i("Attribute", "description");
                                item.setDescription(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                Log.i("Attribute", "date");
                                item.setPubDate(parser.nextText());
                            } else if (name.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", "title");
                                item.setTitle(parser.nextText().trim());
                            }
                        }
                        break;
                        case XmlPullParser.END_TAG:
                            // Get name of current element
                            name = parser.getName();
                            Log.i("End tag", name);
                            if (name.equalsIgnoreCase(ITEM) && item != null) {
                                Log.i("Added", item.toString());
                                items.add(item);
                            }
                            else if (name.equalsIgnoreCase(CHANNEL)) {
                                done = true;
                            }
                            break;
                }
                eventType = parser.next();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            // Check if stream is not null
            if (stream != null) {
                try {
                    // Close the InputStream
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return items;
    }

}
