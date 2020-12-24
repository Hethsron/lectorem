/**
 * Copyright © 2020  Hethsron Jedaël BOUEYA
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package fr.ensisa.boueya.hethsron.lectorem.ui.home;
/**
 *      @file Adapter.java
 *      @details
 *
 *      @author Hethsron Jedaël BOUEYA (hethsron-jedael.boueya@uha.fr)
 *
 *      @version 0.0.1
 *      @date November, 4th 2020
 *
 *      @Copyright GPLv3+ : GNU GPL version 3 or later
 *                       Licencied Material - Property of Me®
 *                       © 2020 ENSISA (UHA) - All rights reserved.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import fr.ensisa.boueya.hethsron.lectorem.R;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.Feed;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Feed> feeds;                                   // A dynamic list of RSS feeds
    private Context context;                                    // A interface to global information
    private LayoutInflater inflater;                            // A standard layout inflater
    private OnItemClickListener listener;                       // A click listener

    public Adapter(Context context) {
        this.context = context;
        this.feeds = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.show_items, parent, false);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(feeds.get(position).getImage());
        holder.title.setText(feeds.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    public Feed getFeedAt(int position) {
        return feeds.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.feed_image);
            this.title = itemView.findViewById(R.id.feed_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    // Check if listener is not null and position do not refer to NO_POSITION flag to avoid crash
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(feeds.get(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Feed feed);
    }

    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        this.listener = listener;
    }

}
