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
package fr.ensisa.boueya.hethsron.lectorem.ui.splashscreen;
/**
 *      @file            SplashScreenFragment.java
 *      @details         Contains an useful class to manage splash screen window
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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import fr.ensisa.boueya.hethsron.lectorem.R;

/**
 * @class           SplashScreenFragment
 * @brief           Manage splash screen window and perform animations
 */
public class SplashScreenFragment extends Fragment {

    private Animation top;                          // Top abstraction of Animation that can be applied to view
    private Animation bottom;                       // Bottom abstraction  of Animation that can be applied to view
    private ImageView image;                        // Image View resources
    private TextView text;                          // A user interface element that displays text to user


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, Bundle savedInstanceState) {
        // Set Window flags
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @NonNull
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onViewCreated(view);
    }

    @NonNull
    public void onViewCreated(@NonNull View view) {
        // Load animations objects from resources
        top = AnimationUtils.loadAnimation(getActivity(), R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_animation);

        // Find and Hook views that were identified by the android:id
        image = view.findViewById(R.id.smv);
        text = view.findViewById(R.id.stv);

        // Set the next animations to play for these views
        image.setAnimation(top);
        text.setAnimation(bottom);

        // Set time out
        final int TIME_OUT = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Go to HomeFragment View
                NavHostFragment.findNavController(SplashScreenFragment.this)
                        .navigate(R.id.action_splashScreenFragment_to_homeFragment);
            }
        }, TIME_OUT);
    }
}