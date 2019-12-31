package com.example.cv.lhs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitles= new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //CompletedParticipantsFragment completedParticipantsFragment= new CompletedParticipantsFragment();
        //InprogressParticipantsFragment inprogressParticipantsFragment= new InprogressParticipantsFragment();
       // position = position+1;
       // Bundle bundle=new Bundle();
       // bundle.putString("message", "Fragment :"+position);
      //  completedParticipantsFragment.setArguments(bundle);
        return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
      return   lstTitles.get(position);
   /* position = position+1;
    if(position == 1){
        return "Completed";
    }
else{
    return "Inprogress";
    }*/
    }

    public void AddFragment(Fragment fragment, String title ){
        lstFragment.add(fragment);
        lstTitles.add(title);
    }
}
