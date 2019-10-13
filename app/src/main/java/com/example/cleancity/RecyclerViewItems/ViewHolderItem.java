package com.example.cleancity.RecyclerViewItems;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleancity.R;
import com.example.cleancity.models.Signal;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderItem extends RecyclerView.ViewHolder{
    private ImageView photo;
    private TextView titre;
    private TextView description;
    private TextView date;
    private Signal signal;
    public ViewHolderItem(@NonNull View itemView) {
        super(itemView);
        photo = itemView.findViewById(R.id.photo);
        titre = itemView.findViewById(R.id.titre);
        date = itemView.findViewById(R.id.date);
        description = itemView.findViewById(R.id.description);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do somthing

            }
        });
    }

    public void bind(Signal signal){

        int id= 0;
        try {
            id = R.drawable.class.getField(signal.getPhoto()).getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        this.photo.setImageResource(R.drawable.bell);
        this.signal=signal;
        this.titre.setText(signal.getType());
        this.description.setText(signal.getDesc());
        this.date.setText(signal.getDate());

    }

}
