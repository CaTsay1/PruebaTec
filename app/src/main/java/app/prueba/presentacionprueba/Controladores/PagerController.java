package app.prueba.presentacionprueba.Controladores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import app.prueba.presentacionprueba.Activitys.FragmentNav.FavoritosFragment;
import app.prueba.presentacionprueba.Activitys.FragmentNav.InicioFragment;

public class PagerController extends FragmentPagerAdapter {

    int numoftabs;

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numoftabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new InicioFragment();
            case 1:
                return new FavoritosFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
