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
package fr.ensisa.boueya.hethsron.lectorem.ui.middleware;
/**
 *      @file            NewsFragment.java
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
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import fr.ensisa.boueya.hethsron.lectorem.R;
import fr.ensisa.boueya.hethsron.lectorem.databinding.FragmentNewsBinding;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.item.Item;
import fr.ensisa.boueya.hethsron.lectorem.ui.provider.FeedProvider;

public class NewsFragment extends Fragment {

    private Toolbar toolbar;                                    // A standard Toolbar for use within application content
    private RecyclerView recyclerView;                          // A flexible view for providing a limited window to a large data set
    private NewsAdapter adapter;                                // A new adapter for providing child view on demand
    private FragmentNewsBinding binding;                        // A News Fragment binding

    private List<Item> items = new ArrayList<>();               // A dynamic list of items in a RSS feed

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("feedKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                try {
                    for (Item item : new PopulateItemsAsyncTask().execute(result.getString("urlKey")).get()) {
                        items.add(item);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        // Set Window flags
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create view DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);

        // Return View
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view);
    }

    private void onViewCreated(@NonNull View view) {
        // Configure Toolbar
        toolbar = binding.newsFtb;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_action_name);

        // Configure Adapter
        adapter = new NewsAdapter(view.getContext());
        adapter.setItems(items);

        // Configure RecyclerView
        recyclerView = binding.nrv;
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // Implement OnItemClickListener method
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                // Pass results between fragments
                Bundle result = new Bundle();
                result.putString("linkKey", item.getLink());
                getParentFragmentManager().setFragmentResult("itemKey", result);

                // Go to ItemFragment View
                NavHostFragment.findNavController(NewsFragment.this)
                        .navigate(R.id.action_newsFragment_to_itemFragment);
            }
        });
    }

    public class PopulateItemsAsyncTask extends AsyncTask<String, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(String... strings) {
            return FeedProvider.parse(strings[0]);
        }
    }

}