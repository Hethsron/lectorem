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
 *      @file            AddFeedFragment.java
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import fr.ensisa.boueya.hethsron.lectorem.R;
import fr.ensisa.boueya.hethsron.lectorem.databinding.FragmentAddFeedBinding;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.Feed;
import fr.ensisa.boueya.hethsron.lectorem.ui.model.FeedViewModel;

public class AddFeedFragment extends Fragment {

    private Toolbar toolbar;                                    // A standard Toolbar for use within application content
    private Animation from_bottom;                              // From bottom abstraction of Animation that can be applied to FloatingActionButton
    private Animation to_bottom;                                // To bottom abstraction of Animation that can be applied to FloatingActionButton
    private Animation rotate_close;                             // Rotate Close bottom abstraction of Animation that can be applied to FloatingActionButton
    private Animation rotate_open;                              // Rotate Open bottom abstraction of Animation that can be applied to FloatingActionButton
    private FloatingActionButton cancel;                        // A cancel FloatingActionButton to leave fragment view
    private FloatingActionButton save;                          // A save FloatingActionButton to save given feed in database
    private FloatingActionButton tool;                          // A tool FloatingActionButton to display provided functions
    private EditText title;                                     // A feed title TextView
    private EditText description;                               // A feed description TextView
    private EditText link;                                      // A feed link TextView
    private NumberPicker priority;                              // A feed priority NumberPicker
    private FragmentAddFeedBinding binding;                     // A feed fragment binding

    private boolean clicked = false;                            // Default clicked status of tool FloatingActionButton

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        // Set Window flags
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create view DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_feed, container, false);

        // Return View
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view);
    }

    @NonNull
    private void onViewCreated(@NonNull View view) {
        // Configure Toolbar
        toolbar = binding.addFtb;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_action_name);

        // Configure TextView
        title = binding.userTitle;
        description = binding.userDescription;
        link = binding.userLink;

        // Configure NumberPicker
        priority = binding.userPriority;
        priority.setMinValue(1);
        priority.setMaxValue(10);

        // Load animations objects from resources
        from_bottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom);
        to_bottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom);
        rotate_close = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close);
        rotate_open = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open);

        // Configure a tool FloatingActionButton
        tool = binding.toolFab;
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolButtonClicked(v);
            }
        });

        // Configure a cancel FloatingActionButton
        cancel = binding.cancelFab;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to HomeFragment View
                NavHostFragment.findNavController(AddFeedFragment.this)
                        .navigate(R.id.action_addFeedFragment_to_homeFragment);
            }
        });

        // Configure a save FloatingActionButton
        save = binding.saveFab;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked(v);
            }
        });
    }

    private void onSaveButtonClicked(@NonNull View v) {
        // Get title CharSequence from View and check if it is empty
        final String title_str = title.getText().toString();
        if (TextUtils.isEmpty(title_str)) {
            title.setError(v.getResources().getString(R.string.title_error));
            return;
        }

        // Get description CharSequence from View and check if it is empty
        final String description_str = description.getText().toString();
        if (TextUtils.isEmpty(description_str)) {
            description.setError(v.getResources().getString(R.string.description_error));
            return;
        }

        // Get link CharSequence from View and check if it is empty
        final String link_str = link.getText().toString();
        if (TextUtils.isEmpty(link_str)) {
            link.setError(v.getResources().getString(R.string.link_error));
            return;
        }

        // Check if given link CharSequence is a valid URL
        if (!URLUtil.isValidUrl(link_str)) {
            link.setError(v.getResources().getString(R.string.url_error));
            return;
        }

        // Get priority value from View
        final int priority_val = priority.getValue();

        onHomeViewUpdated(new Feed(title_str, description_str, priority_val, R.drawable.ic_rss, link_str));
    }

    private void onHomeViewUpdated(Feed feed) {
        FeedViewModel model = new ViewModelProvider(requireActivity()).get(FeedViewModel.class);
        model.getAll().observe(getViewLifecycleOwner(), item ->{
            // Update the list of UI
            item.add(feed);
            binding.setFeed(feed);
        });

        // Go to HomeFragment View
        NavHostFragment.findNavController(AddFeedFragment.this)
                .navigate(R.id.action_addFeedFragment_to_homeFragment);
    }

    private void onToolButtonClicked(@NonNull View v) {
        // Configure visibility of others buttons
        setVisibility(v, clicked);

        // Configure animation
        setAnimation(v, clicked);

        // Update clicked status
        clicked = !clicked;
    }

    private void setVisibility(@NonNull View v, boolean status) {
        if (!status) {
            // Set visibility of save FloatingActionButton to VISIBLE
            save.setVisibility(v.VISIBLE);

            // Set visibility of cancel FloatingActionButton to VISIBLE
            cancel.setVisibility(v.VISIBLE);
        }
        else {
            // Set visibility of save FloatingActionButton to INVISIBLE
            save.setVisibility(v.INVISIBLE);

            // Set visibility of cancel FloatingActionButton to INVISIBLE
            cancel.setVisibility(v.INVISIBLE);
        }
    }

    private void setAnimation(@NonNull View v, boolean status) {
        if (!status) {
            // Set animation of save FloatingActionButton
            save.setAnimation(from_bottom);

            // Set animation of cancel FloatingActionButton
            cancel.setAnimation(from_bottom);

            // Set animation of tool FloatingActionButton
            tool.setAnimation(rotate_open);
        }
        else {
            // Set animation of save FloatingActionButton
            save.setAnimation(to_bottom);

            // Set animation of cancel FloatingActionButton
            cancel.setAnimation(to_bottom);

            // Set animation of tool FloatingActionButton
            tool.setAnimation(rotate_close);
        }
    }

}