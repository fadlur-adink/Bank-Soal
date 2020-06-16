package com.example.crudtest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudtest.Kelas;
import com.example.crudtest.R;

import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.KelasViewHolder> {

    private Context mCtx;
    private List<Kelas> KelasList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public KelasAdapter(Context mCtx, List<Kelas> kelasList) {
        this.mCtx = mCtx;
        KelasList = kelasList;
    }

    @NonNull
    @Override
    public KelasAdapter.KelasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_kelas_layout, parent, false);
        return new KelasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KelasAdapter.KelasViewHolder holder, int position) {

        Kelas kelas = KelasList.get(position);

        holder.textViewKelas.setText(Integer.toString(kelas.getKelas()));
    }

    @Override
    public int getItemCount() {
        return KelasList.size();
    }

    class KelasViewHolder extends RecyclerView.ViewHolder{

        TextView textViewKelas;

        public KelasViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewKelas = itemView.findViewById(R.id.txtlistkelas);

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
