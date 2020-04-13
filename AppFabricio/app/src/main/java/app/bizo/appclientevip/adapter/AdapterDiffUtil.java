package app.bizo.appclientevip.adapter;

import android.widget.Adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import app.bizo.appclientevip.model.Usuario;

public class AdapterDiffUtil extends DiffUtil.Callback {

    private List<Usuario> oldList;
    private List<Usuario> newList;

    public AdapterDiffUtil(List<Usuario> oldList, List<Usuario> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }


    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Usuario oldEmployee = oldList.get(oldItemPosition);
        final Usuario newEmployee = newList.get(newItemPosition);

        return oldEmployee.getNome().equals(newEmployee.getNome());
    }

//    @Nullable
//    @Override
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//        // Implement method if you're going to use ItemAnimator
//        return super.getChangePayload(oldItemPosition, newItemPosition);
//    }
}
