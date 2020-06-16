package com.example.crudtest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudtest.Mapel;
import com.example.crudtest.R;

import java.util.List;

public class MapelAdapter extends RecyclerView.Adapter<MapelAdapter.MapelViewHolder> {

    private Context mCtx;
    private List <Mapel> MapelList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MapelAdapter(Context mCtx, List<Mapel> mapelList) {
        this.mCtx = mCtx;
        MapelList = mapelList;
    }

    @NonNull
    @Override
    public MapelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_mapel_layout, parent, false);
        return new MapelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapelViewHolder holder, int position) {
        Mapel mapel = MapelList.get(position);

        holder.textViewMapel.setText(mapel.getNamamapel());
    }

    @Override
    public int getItemCount() {
        return MapelList.size();
    }

    class MapelViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMapel;

        public MapelViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMapel = itemView.findViewById(R.id.txtlistmapel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
