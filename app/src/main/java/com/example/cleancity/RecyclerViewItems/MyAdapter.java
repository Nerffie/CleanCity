package com.example.cleancity.RecyclerViewItems;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleancity.R;
import com.example.cleancity.models.Signal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderItem> {
    List<Signal>signals;
    AppCompatActivity activity;

    public MyAdapter(AppCompatActivity activity){
        this.activity=activity;
        setSignals();
    }

    private void setSignals() {
        signals = new ArrayList<>();
        signals.add(new Signal(1,45.782568, 3.087628,"signal1","toto","Desc 1","12/10/2019"));
        signals.add(new Signal(2,45.779271, 3.090622,"signal2","toto","Desc 2","12/10/2019"));
        signals.add(new Signal(3,45.781420, 3.074322,"signal3","toto","Desc 3","12/10/2019"));
        signals.add(new Signal(4,45.777138, 3.075145,"signal4","toto","Desc 4","12/10/2019"));
        signals.add(new Signal(5,45.773999, 3.072511,"signal5","toto","Desc 5","12/10/2019"));
        signals.add(new Signal(6,45.767997, 3.088088,"signal6","toto","Desc 6","12/10/2019"));
        signals.add(new Signal(7,45.777046, 3.081302,"signal7","toto","Desc 7","12/10/2019"));
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
            Signal signal = signals.get(position);
            holder.bind(signal);
    }

    @Override
    public int getItemCount() {
        return signals.size();
    }


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
                public void onClick(final View view) {
                    //do somthing
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(
                            "Voulez vous ajouter le signal á la carte.")
                            .setCancelable(true)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    Intent data = new Intent();
                                    data.putExtra("identifiant",signal);
                                    activity.setResult(2000,data);
                                    activity.finish();
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    dialog.cancel();
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
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
            this.photo.setImageResource(id);
            this.signal=signal;
            this.titre.setText(signal.getType());
            this.description.setText(signal.getDesc());
            this.date.setText(signal.getDate());

        }

    }
}
