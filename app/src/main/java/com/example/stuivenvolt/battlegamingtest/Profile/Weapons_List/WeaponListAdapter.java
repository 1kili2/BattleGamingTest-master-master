package com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class WeaponListAdapter extends RecyclerView.Adapter<WeaponListAdapter.myViewHolder> {
    String participant;
    List<Weapons> listItem;

    public WeaponListAdapter(List<Weapons> passedListItem) {
        this.listItem = passedListItem;
    }

    @Override
    public WeaponListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weapon_list_item, parent, false);

        return new myViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        int itemNumber = position + 1;
        final Weapons weapon = listItem.get(position);
        Log.e("textview Name","The ID is: "+holder.weaponName.getId());
        holder.weaponName.setText(weapon.getName());
        holder.weaponPrice.setText(weapon.getPrice()+"€");
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView weaponName;
        TextView weaponPrice;

        public myViewHolder(View view) {
            super(view);
            weaponName = view.findViewById(R.id.weapons);
            weaponPrice = view.findViewById(R.id.price);
        }
    }
}

