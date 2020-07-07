package ir.vasl.navigationcomponentimpl.view.characters.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.databinding.CharactersListItemBinding;
import ir.vasl.navigationcomponentimpl.models.CharacterModel;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseViewHolder;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;

public class CharacterListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements GlobalListCallback {

    private List<? extends CharacterModel.Result> mResults;

    private GlobalListCallback globalListCallback;

    public CharacterListAdapter(GlobalListCallback globalListCallback) {
        this.globalListCallback = globalListCallback;
    }

    public void setCharacters(List<? extends CharacterModel.Result> results) {
        if (mResults == null) {
            this.mResults = results;
            notifyItemRangeInserted(0, results.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mResults.size();
                }

                @Override
                public int getNewListSize() {
                    return results.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mResults.get(oldItemPosition).getId() == results.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CharacterModel.Result newResult = results.get(newItemPosition);
                    CharacterModel.Result oldResult = mResults.get(oldItemPosition);
                    return newResult.getId() == oldResult.getId()
                            && TextUtils.equals(newResult.getName(), oldResult.getName())
                            && TextUtils.equals(newResult.getType(), oldResult.getType());
                }
            });

            mResults = results;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CharactersListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.characters_list_item, parent, false);
        return new CharacterListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        ((CharacterListAdapter.ViewHolder) holder).binding.setResult(mResults.get(position));
        ((CharacterListAdapter.ViewHolder) holder).binding.setGlobalListCallback(this);
    }

    @Override
    public int getItemCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public void onItemClicked(Object object) {
        if (globalListCallback != null)
            globalListCallback.onItemClicked(object);
    }

    static class ViewHolder extends BaseViewHolder {

        private CharactersListItemBinding binding;

        public ViewHolder(@NonNull CharactersListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
