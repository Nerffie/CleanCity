package com.example.cleancity.RecyclerViewItems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cleancity.R;
import com.example.cleancity.models.Signal;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<ViewHolderItem> {
    List<Signal>signals;

    public MyAdapter(){
        setSignals();
    }

    private void setSignals() {
        signals = new ArrayList<>();
        signals.add(new Signal(1,"","","bell.png","toto","ici c'est description","12/10/2019"));
        signals.add(new Signal(2,"","","bell.png","toto","ici c'est description","12/10/2019"));
        signals.add(new Signal(3,"","","bell.png","toto","ici c'est description","12/10/2019"));
        signals.add(new Signal(4,"","","bell.png","toto","ici c'est description","12/10/2019"));
        signals.add(new Signal(5,"","","bell.png","toto","ici c'est description","12/10/2019"));
        signals.add(new Signal(6,"","","bell.png","toto","ici c'est description","12/10/2019"));
        signals.add(new Signal(7,"","","bell.png","toto","ici c'est description","12/10/2019"));
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
}
