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
package fr.ensisa.boueya.hethsron.lectorem.ui.home;
/**
 *      @file            HomeFragment.java
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
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import fr.ensisa.boueya.hethsron.lectorem.R;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.Feed;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.FeedViewModel;

public class HomeFragment extends Fragment {

    private FeedViewModel model;                                // A Feed ViewModel instance
    private Toolbar toolbar;                                    // A standard Toolbar for use within application content
    private RecyclerView recyclerView;                          // A flexible view for providing a limited window to a large data set
    private FloatingActionButton add;                           // A add floating action button
    private Adapter adapter;                                    // A new adapter for providing child view on demand

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        // Set Window flags
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @NonNull
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view);
    }

    @NonNull
    private void onViewCreated(@NonNull View view) {
        // Configure Toolbar
        toolbar = view.findViewById(R.id.htb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_action_name);

        // Configure Adapter
        adapter = new Adapter(view.getContext());

        // Configure RecyclerView
        recyclerView = view.findViewById(R.id.hrv);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Configure an add FloatingActionButton
        add = view.findViewById(R.id.add_fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to AddFeedFragment View
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_homeFragment_to_addFeedFragment);
            }
        });

        // Configure ViewModel
        model = new ViewModelProvider(getActivity()).get(FeedViewModel.class);
        model.getAll().observe(getActivity(), new Observer<List<Feed>>() {
            @Override
            public void onChanged(List<Feed> feeds) {
                // Update our RecyclerView
                adapter.setFeeds(feeds);
            }
        });

        // Configure an ItemTouchHelper for swiped deletion
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (position > 6) {
                    model.delete(adapter.getFeedAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getContext(), view.getResources().getString(R.string.delete_msg), Toast.LENGTH_LONG).show();
                    Log.i("Deletion Authorized", "A RSS feed deleted");
                }
                else {
                    Toast.makeText(getContext(), view.getResources().getString(R.string.unauthorized), Toast.LENGTH_LONG).show();
                    Log.i("Deletion Unauthorized", "A RSS feed not deleted");
                }
            }
        }).attachToRecyclerView(recyclerView);

        // Implement OnItemClickListener method
        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Feed feed) {
                // Pass results between fragments
                Bundle result = new Bundle();
                result.putString("urlKey", feed.getUrl());
                getParentFragmentManager().setFragmentResult("feedKey", result);

                // Go to NewsFragment View
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_homeFragment_to_newsFragment);
            }
        });
    }
}