package ir.vasl.navigationcomponentimpl.view.episodes.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vasl.navigationcomponentimpl.R;
import ir.vasl.navigationcomponentimpl.databinding.EpisodeListItemBindingImpl;
import ir.vasl.navigationcomponentimpl.models.EpisodeModel;
import ir.vasl.navigationcomponentimpl.utils.BaseClasses.BaseViewHolder;
import ir.vasl.navigationcomponentimpl.view.callbacks.GlobalListCallback;

public class EpisodesListAdapter extends RecyclerView.Adapter<BaseViewHolder> implements GlobalListCallback {

    private List<? extends EpisodeModel.Result> mResults;

    private GlobalListCallback globalListCallback;

    public EpisodesListAdapter(GlobalListCallback globalListCallback) {
        this.globalListCallback = globalListCallback;
    }

    public void setCharacters(List<? extends EpisodeModel.Result> results) {
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
                    EpisodeModel.Result newResult = results.get(newItemPosition);
                    EpisodeModel.Result oldResult = mResults.get(oldItemPosition);
                    return newResult.getId() == oldResult.getId()
                            && TextUtils.equals(newResult.getName(), oldResult.getName())
                            && TextUtils.equals(newResult.getEpisode(), oldResult.getEpisode());
                }
            });

            mResults = results;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EpisodeListItemBindingImpl binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.episode_list_item, parent, false);
        return new EpisodesListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        ((EpisodesListAdapter.ViewHolder) holder).binding.setResult(mResults.get(position));
        ((EpisodesListAdapter.ViewHolder) holder).binding.setGlobalListCallback(this);
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

        private EpisodeListItemBindingImpl binding;

        public ViewHolder(@NonNull EpisodeListItemBindingImpl binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
