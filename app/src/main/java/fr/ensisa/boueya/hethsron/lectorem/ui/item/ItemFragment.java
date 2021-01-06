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
package fr.ensisa.boueya.hethsron.lectorem.ui.item;
/**
 *      @file            ItemFragment.java
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
import androidx.fragment.app.FragmentResultListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import fr.ensisa.boueya.hethsron.lectorem.R;
import fr.ensisa.boueya.hethsron.lectorem.databinding.FragmentItemBinding;

public class ItemFragment extends Fragment {

    private Toolbar toolbar;                                    // A standard Toolbar for use within application content
    private WebView webView;                                    // An inner WebView
    private FragmentItemBinding binding;                        // A Fragment Item binding

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        // Set Window flags
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create view DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item, container, false);

        // Return View
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated(view);
    }

    private void onViewCreated(@NonNull View view) {
        // Configure Toolbar
        toolbar = binding.itemFtb;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_action_name);

        getParentFragmentManager().setFragmentResultListener("itemKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Configure WebView
                webView = binding.itemWeb;
                webView.loadUrl(result.getString("linkKey"));
            }
        });
    }

}